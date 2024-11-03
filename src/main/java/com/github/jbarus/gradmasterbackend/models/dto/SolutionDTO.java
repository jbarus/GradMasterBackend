package com.github.jbarus.gradmasterbackend.models.dto;

import com.github.jbarus.gradmasterbackend.models.Student;
import com.github.jbarus.gradmasterbackend.models.problem.Committee;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolutionDTO {
    private UUID id;
    private List<Committee> committees;
    private List<Student> unassignedStudents;
}
