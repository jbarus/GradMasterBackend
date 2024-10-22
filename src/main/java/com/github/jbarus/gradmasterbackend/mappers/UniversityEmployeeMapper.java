package com.github.jbarus.gradmasterbackend.mappers;

import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import com.github.jbarus.gradmasterbackend.models.dto.UniversityEmployeeDTO;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;

import java.util.List;

public class UniversityEmployeeMapper {
    public static UniversityEmployeeDTO convertUniversityEmployeeListToUniversityEmployeeDTO(ProblemContext problemContext){
        return UniversityEmployeeDTO.builder()
                .id(problemContext.getUuid())
                .universityEmployees(problemContext.getUniversityEmployees())
                .build();
    }

    public static List<UniversityEmployee> converUniversityEmployeeDTOtoUniversityEmployeeList(UniversityEmployeeDTO employeeDTO){
        return employeeDTO.getUniversityEmployees();
    }
}
