package com.github.jbarus.gradmasterbackend.models.dto;

import com.github.jbarus.gradmasterbackend.context.Context;
import com.github.jbarus.gradmasterbackend.context.ContextOptions;
import com.github.jbarus.gradmasterbackend.models.Student;
import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import com.github.jbarus.gradmasterbackend.structures.CorrelationMap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ContextDataDTO {
    private UUID id;
    private List<UniversityEmployee> universityEmployeeList;
    private List<Student> unassignedStudentList;
    private List<UUID> positiveCorrelation;
    private List<UUID> negativeCorrelation;
    private ContextOptions contextOptions;

    public ContextDataDTO(UUID id, List<UniversityEmployee> universityEmployeeList, List<Student> unassignedStudentList, List<UUID> positiveCorrelation, List<UUID> negativeCorrelation, ContextOptions contextOptions) {
        this.id = id;
        this.universityEmployeeList = universityEmployeeList;
        this.unassignedStudentList = unassignedStudentList;
        this.positiveCorrelation = positiveCorrelation;
        this.negativeCorrelation = negativeCorrelation;
        this.contextOptions = contextOptions;
    }
}
