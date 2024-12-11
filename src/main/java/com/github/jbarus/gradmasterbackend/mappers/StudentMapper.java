package com.github.jbarus.gradmasterbackend.mappers;

import com.github.jbarus.gradmasterbackend.models.Student;
import com.github.jbarus.gradmasterbackend.models.dto.StudentDTO;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;

import java.util.List;
import java.util.UUID;

public class StudentMapper {
    public static StudentDTO convertStudentListToStudentDTO(UUID contextId, List<Student> studentList){
        if (contextId == null || studentList == null) {
            return null;
        }
        return StudentDTO.builder()
                .id(contextId)
                .students(studentList)
                .build();
    }

    public static List<Student> converStudentDTOtoStudentList(StudentDTO studentDTO){
        if (studentDTO == null) {
            return null;
        }
        return studentDTO.getStudents();
    }
}
