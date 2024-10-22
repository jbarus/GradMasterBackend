package com.github.jbarus.gradmasterbackend.models.dto;

import com.github.jbarus.gradmasterbackend.models.Student;
import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemParameters;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemDTO {
    private UUID id;
    private List<UniversityEmployee> universityEmployees;
    private List<Student> students;
    private HashMap<UUID, UUID> studentReviewerMapping;
    private HashMap<UUID, UUID> positiveCorrelationMapping;
    private HashMap<UUID, UUID> negativeCorrelationMapping;
    private List<UUID> splittedUniversityEmployees;
    private ProblemParameters problemParameters;


}
