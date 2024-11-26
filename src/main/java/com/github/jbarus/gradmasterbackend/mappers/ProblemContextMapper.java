package com.github.jbarus.gradmasterbackend.mappers;

import com.github.jbarus.gradmasterbackend.models.dto.ContextDisplayInfoDTO;
import com.github.jbarus.gradmasterbackend.models.dto.ProblemDTO;
import com.github.jbarus.gradmasterbackend.models.dto.SolutionDTO;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;

public class ProblemContextMapper {
    public static void contextInfoDisplayToProblemContextConverter(ContextDisplayInfoDTO contextDisplayInfoDTO, ProblemContext problemContext) {
        problemContext.setName(contextDisplayInfoDTO.getName());
        problemContext.setDate(contextDisplayInfoDTO.getDate());
    }

    public static ContextDisplayInfoDTO problemContextToContextDisplayInfoDTOConverter(ProblemContext problemContext) {
        return ContextDisplayInfoDTO.builder()
                .id(problemContext.getUuid())
                .name(problemContext.getName())
                .date(problemContext.getDate())
                .build();
    }

    public static void ProblemDTOToProblemContextConverter(ProblemDTO problemDTO, ProblemContext problemContext) {
        problemContext.setUniversityEmployees(problemDTO.getUniversityEmployees());
        problemContext.setStudents(problemDTO.getStudents());
        problemContext.setStudentReviewerMapping(problemDTO.getStudentReviewerMapping());
        problemContext.setPositiveCorrelationMapping(problemDTO.getPositiveCorrelationMapping());
        problemContext.setNegativeCorrelationMapping(problemDTO.getNegativeCorrelationMapping());
        problemContext.setSplittedUniversityEmployees(problemDTO.getSplittedUniversityEmployees());
        problemContext.setProblemParameters(problemDTO.getProblemParameters());
    }

    public static ProblemDTO problemContextToProblemDTOConverter(ProblemContext problemContext) {
        return ProblemDTO.builder()
                .id(problemContext.getUuid())
                .universityEmployees(problemContext.getUniversityEmployees())
                .students(problemContext.getStudents())
                .studentReviewerMapping(problemContext.getStudentReviewerMapping())
                .positiveCorrelationMapping(problemContext.getPositiveCorrelationMapping())
                .negativeCorrelationMapping(problemContext.getNegativeCorrelationMapping())
                .splittedUniversityEmployees(problemContext.getSplittedUniversityEmployees())
                .problemParameters(problemContext.getProblemParameters())
                .build();
    }

    public static void SolutionDTOToProblemContextConverter(SolutionDTO solutionDTO, ProblemContext problemContext) {
        problemContext.setSolution(problemContext.getSolution());
    }

    public static SolutionDTO problemContextToSolutionDTOConverter(ProblemContext problemContext) {
        return SolutionDTO.builder()
                .id(problemContext.getUuid())
                .committees(problemContext.getSolution().getCommittees())
                .build();
    }
}
