package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemParameters;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class ProblemParametersService {

    public ProblemParameters setProblemParameters(UUID contextId, ProblemParameters problemParameters) {
        validateProblemParameters(problemParameters);
        ProblemContext context = getValidContextWithEmployees(contextId);
        context.setProblemParameters(problemParameters);
        return context.getProblemParameters();
    }

    public ProblemParameters getProblemParameters(UUID contextId) {
        ProblemContext context = getValidContextWithEmployees(contextId);
        return context.getProblemParameters();
    }

    public ProblemParameters updateProblemParameters(UUID contextId, ProblemParameters problemParameters) {
        validateProblemParameters(problemParameters);
        ProblemContext context = getValidContextWithEmployees(contextId);
        context.setProblemParameters(problemParameters);
        return context.getProblemParameters();
    }

    private ProblemContext getValidContextWithEmployees(UUID contextId) {
        ProblemContext context = ProblemContext.getInstance(contextId);
        if (context == null) {
            throw new IllegalArgumentException("Invalid context ID: " + contextId);
        }
        return context;
    }

    private void validateProblemParameters(ProblemParameters problemParameters) {
        if (problemParameters == null) {
            throw new IllegalArgumentException("ProblemParameters cannot be null.");
        }

        if (problemParameters.getCalculationTimeInSeconds() == 0) {
            throw new IllegalArgumentException("Field 'someField' must be filled.");
        }

        if (problemParameters.getCommitteeSize() == 0) {
            throw new IllegalArgumentException("Field 'anotherField' must be filled.");
        }

        if (problemParameters.getMaxNumberOfNonHabilitatedEmployees() == 0) {
            throw new IllegalArgumentException("Field 'numberField' must be filled.");
        }
    }
}