package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.exceptions.MalformedRequestException;
import com.github.jbarus.gradmasterbackend.exceptions.NoSuchDataException;
import com.github.jbarus.gradmasterbackend.exceptions.calculationstart.NoSuchContextException;
import com.github.jbarus.gradmasterbackend.mappers.ProblemParametersMapper;
import com.github.jbarus.gradmasterbackend.models.dto.ProblemParametersDTO;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemParameters;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class ProblemParametersService {

    public ProblemParametersDTO setProblemParameters(UUID contextId, ProblemParameters problemParameters) {
        validateProblemParameters(problemParameters);
        ProblemContext context = getValidContextWithEmployees(contextId);
        context.setProblemParameters(problemParameters);
        return ProblemParametersMapper.convertProblemParametersToProblemParametersDTO(contextId,context.getProblemParameters());
    }

    public ProblemParametersDTO getProblemParameters(UUID contextId) {
        ProblemContext context = getValidContextWithEmployees(contextId);
        if(context.getProblemParameters() == null) {
            throw new NoSuchDataException("Invalid problem parameters ID: " + contextId);
        }
        return ProblemParametersMapper.convertProblemParametersToProblemParametersDTO(contextId,context.getProblemParameters());
    }

    public ProblemParametersDTO updateProblemParameters(UUID contextId, ProblemParameters problemParameters) {
        validateProblemParameters(problemParameters);
        ProblemContext context = getValidContextWithEmployees(contextId);
        if(context.getProblemParameters() == null) {
            throw new NoSuchDataException("Invalid problem parameters ID: " + contextId);
        }
        context.setProblemParameters(problemParameters);
        return ProblemParametersMapper.convertProblemParametersToProblemParametersDTO(contextId,context.getProblemParameters());
    }

    public ProblemParametersDTO deleteProblemParameters(UUID contextId) {
        ProblemContext context = getValidContextWithEmployees(contextId);
        ProblemParameters problemParameters = context.getProblemParameters();
        if(context.getProblemParameters() == null) {
            throw new NoSuchDataException("Invalid problem parameters ID: " + contextId);
        }
        context.setProblemParameters(null);
        return ProblemParametersMapper.convertProblemParametersToProblemParametersDTO(contextId,problemParameters);
    }

    private ProblemContext getValidContextWithEmployees(UUID contextId) {
        ProblemContext context = ProblemContext.getInstance(contextId);
        if (context == null) {
            throw new NoSuchContextException("Invalid context ID: " + contextId);
        }
        return context;
    }

    private void validateProblemParameters(ProblemParameters problemParameters) {
        if (problemParameters == null) {
            throw new MalformedRequestException("ProblemParameters cannot be null.");
        }

        if (problemParameters.getCalculationTimeInSeconds() == 0) {
            throw new MalformedRequestException("Field 'someField' must be filled.");
        }

        if (problemParameters.getCommitteeSize() == 0) {
            throw new MalformedRequestException("Field 'anotherField' must be filled.");
        }

        if (problemParameters.getMaxNumberOfNonHabilitatedEmployees() == 0) {
            throw new MalformedRequestException("Field 'numberField' must be filled.");
        }
    }
}