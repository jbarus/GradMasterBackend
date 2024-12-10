package com.github.jbarus.gradmasterbackend.utils;

import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContextUtils {

    public static List<UniversityEmployee> getPositiveRelationListByProblemContext(ProblemContext problemContext) {
        List<UniversityEmployee> universityEmployees = new ArrayList<>();
        for(UUID uuid : problemContext.getPositiveCorrelationMapping()){
            for (UniversityEmployee universityEmployee : problemContext.getUniversityEmployees()) {
                if(universityEmployee.getId().equals(uuid)){
                    universityEmployees.add(universityEmployee);
                }
            }
        }
        return universityEmployees;
    }

    public static List<UniversityEmployee> getNegativeRelationListByProblemContext(ProblemContext problemContext) {
        List<UniversityEmployee> universityEmployees = new ArrayList<>();
        for(UUID uuid : problemContext.getNegativeCorrelationMapping()){
            for (UniversityEmployee universityEmployee : problemContext.getUniversityEmployees()) {
                if(universityEmployee.getId().equals(uuid)){
                    universityEmployees.add(universityEmployee);
                }
            }
        }
        return universityEmployees;
    }
}
