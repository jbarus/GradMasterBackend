package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.context.Context;
import com.github.jbarus.gradmasterbackend.models.Student;
import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import com.github.jbarus.gradmasterbackend.pipelines.StudentExtractionPipeline;
import com.github.jbarus.gradmasterbackend.utils.XLSXUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StudentService {
    private final StudentExtractionPipeline studentExtractionPipeline;

    public StudentService(StudentExtractionPipeline studentExtractionPipeline) {
        this.studentExtractionPipeline = studentExtractionPipeline;
    }

    public HashMap<LocalDate, List<Student>> prepareStudents(MultipartFile file) throws Exception {
        XSSFWorkbook workbook;
        try{
            workbook = new XSSFWorkbook(file.getInputStream());
        }catch (Exception e){
            return null;
        }

        studentExtractionPipeline.doFilter(workbook);

        List<List<String>> workbookData = XLSXUtils.convertXSLXToList(workbook);
        if(workbookData.isEmpty()){
            return null;
        }

        HashMap<LocalDate, List<List<String>>> studentsAsListByDate = XLSXUtils.splitByDates(workbookData, 2);
        HashMap<LocalDate, List<Student>> studentsByDate = new HashMap<>();
        for(Map.Entry<LocalDate, List<List<String>>> entry : studentsAsListByDate.entrySet()){
            Context context = Context.getInstance(entry.getKey());
            HashMap<String, List<Student>> studentsByUniversityEmployee = XLSXUtils.convertRawDataToStudentHashMapByUniversityEmployee(entry.getValue());
            List<Student> studentsForDate = new ArrayList<>();
            for (UniversityEmployee universityEmployee : context.getUniversityEmployeeList()) {
                String employeeKey = universityEmployee.getSecondName() + " " + universityEmployee.getFirstName();

                List<Student> reviewedStudents = studentsByUniversityEmployee.get(employeeKey);

                if (reviewedStudents != null) {
                    universityEmployee.setReviewedStudents(reviewedStudents);
                    studentsForDate.addAll(reviewedStudents);
                }
            }
            studentsByDate.put(entry.getKey(), studentsForDate);
            context.getStudentList().addAll(studentsForDate);
        }

        return studentsByDate;
    }
}
