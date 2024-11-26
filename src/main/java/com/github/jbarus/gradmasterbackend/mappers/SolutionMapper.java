package com.github.jbarus.gradmasterbackend.mappers;

import com.github.jbarus.gradmasterbackend.models.dto.SolutionDTO;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;
import com.github.jbarus.gradmasterbackend.models.problem.Solution;
import org.springframework.beans.factory.parsing.Problem;

public class SolutionMapper {

    public static SolutionDTO convertSolutionToSolutionDTO(ProblemContext problemContext) {
        SolutionDTO solutionDTO = new SolutionDTO();
        solutionDTO.setCommittees(problemContext.getSolution().getCommittees());
        solutionDTO.setId(problemContext.getUuid());
        solutionDTO.setUnassignedStudents(problemContext.getSolution().getUnassignedStudents());
        return solutionDTO;
    }

    public static Solution convertSolutionDTOToSolution(SolutionDTO solutionDTO) {
        Solution solution = new Solution();
        solution.setCommittees(solutionDTO.getCommittees());
        solution.setUnassignedStudents(solutionDTO.getUnassignedStudents());
        return solution;
    }
}