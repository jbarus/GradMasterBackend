package com.github.jbarus.gradmasterbackend.mappers;

import com.github.jbarus.gradmasterbackend.models.dto.SolutionDTO;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;
import com.github.jbarus.gradmasterbackend.models.problem.Solution;
import org.springframework.beans.factory.parsing.Problem;

import java.util.UUID;

public class SolutionMapper {

    public static SolutionDTO convertSolutionToSolutionDTOFromProblemContext(ProblemContext problemContext) {
        if (problemContext.getSolution() == null) {
            return null;
        }
        SolutionDTO solutionDTO = new SolutionDTO();
        solutionDTO.setId(problemContext.getUuid());
        solutionDTO.setSolution(problemContext.getSolution());
        return solutionDTO;
    }

    public static Solution convertSolutionDTOToSolution(SolutionDTO solutionDTO) {
        if (solutionDTO == null) {
            return null;
        }
        Solution solution = new Solution();
        solution.setCommittees(solutionDTO.getSolution().getCommittees());
        solution.setUnassignedStudents(solutionDTO.getSolution().getUnassignedStudents());
        solution.setUnassignedUniversityEmployees(solutionDTO.getSolution().getUnassignedUniversityEmployees());
        return solution;
    }

    public static SolutionDTO convertSolutionToSolutionDTO(UUID id, Solution solution) {
        if (solution == null) {
            return null;
        }
        SolutionDTO solutionDTO = new SolutionDTO();
        solutionDTO.setId(id);
        solutionDTO.setSolution(solution);
        return solutionDTO;
    }
}
