package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.exceptions.MissingColumnsException;
import com.github.jbarus.gradmasterbackend.exceptions.MultipleDatesException;
import com.github.jbarus.gradmasterbackend.mappers.StudentMapper;
import com.github.jbarus.gradmasterbackend.models.Student;
import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import com.github.jbarus.gradmasterbackend.models.communication.Response;
import com.github.jbarus.gradmasterbackend.models.communication.UploadStatus;
import com.github.jbarus.gradmasterbackend.models.dto.StudentDTO;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;
import com.github.jbarus.gradmasterbackend.pipelines.StudentExtractionPipeline;
import com.github.jbarus.gradmasterbackend.pipelines.filters.studentfilters.StudentFormatFilter;
import com.github.jbarus.gradmasterbackend.pipelines.filters.studentfilters.StudentMajorFilter;
import com.github.jbarus.gradmasterbackend.utils.XLSXUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class StudentService {
    private final StudentExtractionPipeline studentExtractionPipeline;

    public StudentService(StudentExtractionPipeline studentExtractionPipeline) {
        this.studentExtractionPipeline = studentExtractionPipeline;
    }

    public ResponseEntity<Response<UploadStatus, StudentDTO>> handleStudentFile(MultipartFile file, UUID id) {
        XSSFWorkbook workbook;
        try{
            workbook = new XSSFWorkbook(file.getInputStream());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new Response<>(UploadStatus.INVALID_INPUT, null));
        }

        try{
            studentExtractionPipeline.addFilterAfter(StudentFormatFilter.class, new StudentMajorFilter());
            studentExtractionPipeline.doFilter(workbook);
        }catch (MissingColumnsException ex){
            return ResponseEntity.badRequest().body(new Response<>(UploadStatus.INVALID_CONTENT, null));
        }catch (MultipleDatesException ex){
            return ResponseEntity.badRequest().body(new Response<>(UploadStatus.MULTIPLE_DATES, null));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new Response<>(UploadStatus.PARSING_ERROR, null));
        }

        List<List<String>> workbookData = XLSXUtils.convertXSLXToList(workbook);

        if(workbookData.isEmpty()){
            return ResponseEntity.badRequest().body(new Response<>(UploadStatus.PARSING_ERROR));
        }

        HashMap<String, List<Student>> studentReviewerMap = XLSXUtils.getStudentReviewerMapping(workbookData);

        ProblemContext problemContext = ProblemContext.getInstance(id);
        if(problemContext == null){
            return ResponseEntity.badRequest().body(new Response<>(UploadStatus.UNINITIALIZED_CONTEXT, null));
        }

        List<UniversityEmployee> universityEmployees = problemContext.getUniversityEmployees();
        List<Student> students = new ArrayList<>();
        HashMap<UUID, List<UUID>> studentReviewerMapping = new HashMap<>();
        //TODO Do przerobienia bo nie łapie studentów bez profesora w komisji
        for (UniversityEmployee universityEmployee : universityEmployees) {
            List<Student> studentsForReviewer = studentReviewerMap.get(universityEmployee.getSecondName() + " " + universityEmployee.getFirstName());
            if (studentsForReviewer != null) {
                List<UUID> studentIds = new ArrayList<>();
                for (Student student : studentsForReviewer) {
                    studentIds.add(student.getId());
                }
                studentReviewerMapping.put(universityEmployee.getId(), studentIds);
                students.addAll(studentsForReviewer);
                studentReviewerMap.remove(universityEmployee.getSecondName() + " " + universityEmployee.getFirstName());
            }
        }
        for (List<Student> studentsWithoutReviewer : studentReviewerMap.values()) {
            students.addAll(studentsWithoutReviewer);
            System.out.println(studentsWithoutReviewer);
        }
        problemContext.setStudents(students);
        problemContext.setStudentReviewerMapping(studentReviewerMapping);

        return ResponseEntity.badRequest().body(new Response<>(UploadStatus.SUCCESS, StudentMapper.convertStudentListToStudentDTO(problemContext)));
    }

    public ResponseEntity<List<Student>> getStudentsByContext(UUID id) {
        ProblemContext problemContext = ProblemContext.getInstance(id);
        if(problemContext == null || problemContext.getUniversityEmployees() == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(problemContext.getStudents());
    }

    public ResponseEntity<List<Student>> updateStudentsByContext(UUID id, List<Student> students) {
        ProblemContext problemContext = ProblemContext.getInstance(id);
        if(problemContext == null || problemContext.getUniversityEmployees() == null){
            return ResponseEntity.badRequest().build();
        }
        problemContext.setStudents(students);
        return ResponseEntity.ok().body(problemContext.getStudents());
    }
}
