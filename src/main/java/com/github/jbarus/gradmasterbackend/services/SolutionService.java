package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.exceptions.CalculationInProgressException;
import com.github.jbarus.gradmasterbackend.exceptions.NoSuchDataException;
import com.github.jbarus.gradmasterbackend.exceptions.calculationstart.NoSuchContextException;
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
        if (problemContext == null) {
            throw new NoSuchContextException("ProblemContext or solution not found for the given contextId");
        }
        if(problemContext.isInProgress()){
            throw new CalculationInProgressException("Calculation in progress");
        }
        return SolutionMapper.convertSolutionToSolutionDTO(problemContext);
    }

    public SolutionDTO updateSolutionByContextId(UUID contextId, SolutionDTO solutionDTO) {
        ProblemContext problemContext = ProblemContext.getInstance(contextId);
        if (problemContext == null) {
            throw new NoSuchContextException("ProblemContext or solution not found for the given contextId");
        }
        if(problemContext.getSolution() == null){
            throw new NoSuchDataException("Solution not found for the given contextId");
        }
        if(problemContext.isInProgress()){
            throw new CalculationInProgressException("Calculation in progress");
        }
        problemContext.setSolution(SolutionMapper.convertSolutionDTOToSolution(solutionDTO));
        return SolutionMapper.convertSolutionToSolutionDTO(problemContext);
    }

    public SolutionDTO setSolutionByContextId(UUID contextId, SolutionDTO solutionDTO) {
        ProblemContext problemContext = ProblemContext.getInstance(contextId);
        if (problemContext == null) {
            throw new NoSuchContextException("ProblemContext not found for the given contextId");
        }
        if(problemContext.isInProgress()){
            throw new CalculationInProgressException("Calculation in progress");
        }
        problemContext.setSolution(SolutionMapper.convertSolutionDTOToSolution(solutionDTO));
        return SolutionMapper.convertSolutionToSolutionDTO(problemContext);
    }
}
