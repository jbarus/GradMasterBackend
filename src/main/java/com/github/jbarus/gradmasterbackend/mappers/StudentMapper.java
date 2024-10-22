package com.github.jbarus.gradmasterbackend.mappers;

import com.github.jbarus.gradmasterbackend.models.Student;
import com.github.jbarus.gradmasterbackend.models.dto.StudentDTO;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;

import java.util.List;

public class StudentMapper {
    public static StudentDTO convertStudentListToStudentDTO(ProblemContext problemContext){
        return StudentDTO.builder()
                .id(problemContext.getUuid())
                .students(problemContext.getStudents())
                .build();
    }

    public static List<Student> converStudentDTOtoStudentList(StudentDTO studentDTO){
        return studentDTO.getStudents();
    }
}
