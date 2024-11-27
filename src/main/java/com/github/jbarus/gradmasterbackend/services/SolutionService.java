package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.mappers.SolutionMapper;
import com.github.jbarus.gradmasterbackend.models.dto.SolutionDTO;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;
import org.etsi.uri.x01903.v13.ResponderIDType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SolutionService {

    public SolutionDTO getSolutionByContextId(UUID contextId) {
        ProblemContext problemContext = ProblemContext.getInstance(contextId);
        if (problemContext == null || problemContext.getSolution() == null || problemContext.isInProgress()) {
            throw new IllegalArgumentException("ProblemContext or solution not found for the given contextId");
        }
        return SolutionMapper.convertSolutionToSolutionDTO(problemContext);
    }

    public SolutionDTO updateSolutionByContextId(UUID contextId, SolutionDTO solutionDTO) {
        ProblemContext problemContext = ProblemContext.getInstance(contextId);
        if (problemContext == null || problemContext.getSolution() == null || problemContext.isInProgress()) {
            throw new IllegalArgumentException("ProblemContext or solution not found for the given contextId");
        }
        problemContext.setSolution(SolutionMapper.convertSolutionDTOToSolution(solutionDTO));
        return SolutionMapper.convertSolutionToSolutionDTO(problemContext);
    }

    public SolutionDTO setSolutionByContextId(UUID contextId, SolutionDTO solutionDTO) {
        ProblemContext problemContext = ProblemContext.getInstance(contextId);
        if (problemContext == null || problemContext.isInProgress()) {
            throw new IllegalArgumentException("ProblemContext not found for the given contextId");
        }
        problemContext.setSolution(SolutionMapper.convertSolutionDTOToSolution(solutionDTO));
        return SolutionMapper.convertSolutionToSolutionDTO(problemContext);
    }
}
