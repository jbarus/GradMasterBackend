package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.exceptions.*;
import com.github.jbarus.gradmasterbackend.exceptions.calculationstart.NoSuchContextException;
import com.github.jbarus.gradmasterbackend.mappers.StudentMapper;
import com.github.jbarus.gradmasterbackend.models.Student;
import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import com.github.jbarus.gradmasterbackend.models.communication.UploadStatus;
import com.github.jbarus.gradmasterbackend.models.dto.StudentDTO;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;
import com.github.jbarus.gradmasterbackend.pipelines.StudentExtractionPipeline;
import com.github.jbarus.gradmasterbackend.utils.XLSXUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

    public StudentDTO handleStudentFile(MultipartFile file, UUID id) {
        XSSFWorkbook workbook;

        try {
            workbook = new XSSFWorkbook(file.getInputStream());
        } catch (Exception e) {
            throw new InvalidInputException("Invalid input file");
        }

        try {
            studentExtractionPipeline.doFilter(workbook);
        } catch (MissingColumnsException e) {
            throw new BusinessLogicException(UploadStatus.INVALID_CONTENT);
        } catch (Exception e) {
            throw new BusinessLogicException(UploadStatus.PARSING_ERROR);
        }

        List<List<String>> workbookData = XLSXUtils.convertXSLXToList(workbook);

        if (workbookData.isEmpty()) {
            throw new BusinessLogicException(UploadStatus.PARSING_ERROR);
        }

        HashMap<String, List<Student>> studentReviewerMap = XLSXUtils.getStudentReviewerMapping(workbookData);
        ProblemContext problemContext = ProblemContext.getInstance(id);

        if (problemContext == null) {
            throw new NoSuchContextException("No such context");
        }

        if(problemContext.getUniversityEmployees() == null){
            throw new UninitializedContextException("Uninitialized context");
        }

        if(problemContext.getStudents() != null && problemContext.getStudentReviewerMapping() != null) {
            throw new BusinessLogicException(UploadStatus.INVALID_UPDATE_SEQUENCE);
        }

        List<UniversityEmployee> universityEmployees = problemContext.getUniversityEmployees();
        List<Student> students = new ArrayList<>();
        HashMap<UUID, List<UUID>> studentReviewerMapping = new HashMap<>();
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

        return StudentMapper.convertStudentListToStudentDTO(problemContext.getUuid(),problemContext.getStudents());
    }

    public StudentDTO getStudentsByContext(UUID id) {
        ProblemContext problemContext = ProblemContext.getInstance(id);

        if (problemContext == null) {
            throw new NoSuchContextException("No such context");
        }
        if(problemContext.getStudents() == null){
            throw new NoSuchDataException("No such data");
        }
        return StudentMapper.convertStudentListToStudentDTO(problemContext.getUuid(),problemContext.getStudents());
    }

    public StudentDTO updateStudentsByContext(UUID id, List<Student> students) {
        ProblemContext problemContext = ProblemContext.getInstance(id);

        if (problemContext == null || problemContext.getUniversityEmployees() == null) {
            throw new UninitializedContextException("No such context");
        }
        if(problemContext.getStudents() == null|| problemContext.getStudentReviewerMapping() == null){
            throw new NoSuchDataException("No such data");
        }

        for (Student student : students) {
            if(!problemContext.getStudents().stream().map(Student::getId).toList().contains(student.getId())) {
                throw new BusinessLogicException(UploadStatus.UNAUTHORIZED_MODIFICATION);
            }
        }

        problemContext.setStudents(students);
        return StudentMapper.convertStudentListToStudentDTO(problemContext.getUuid(),problemContext.getStudents());
    }
}
