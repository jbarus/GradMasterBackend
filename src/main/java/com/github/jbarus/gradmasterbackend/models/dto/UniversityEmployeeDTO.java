package com.github.jbarus.gradmasterbackend.models.dto;

import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UniversityEmployeeDTO {
    private UUID id;
    private List<UniversityEmployee> universityEmployees;
}
