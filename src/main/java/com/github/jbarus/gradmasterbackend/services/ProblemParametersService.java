package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemParameters;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProblemParametersService {

    public ResponseEntity<ProblemParameters> setProblemParametersByContextId(UUID contextId, ProblemParameters problemParameters) {
        ProblemContext problemContext = ProblemContext.getInstance(contextId);
        if(problemContext == null || problemContext.getUniversityEmployees() == null){
            return ResponseEntity.badRequest().build();
        }
        problemContext.setProblemParameters(problemParameters);
        return ResponseEntity.ok().body(problemContext.getProblemParameters());
    }

    public ResponseEntity<ProblemParameters> getProblemParametersByContextId(UUID contextId) {
        ProblemContext problemContext = ProblemContext.getInstance(contextId);
        if(problemContext == null || problemContext.getUniversityEmployees() == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(problemContext.getProblemParameters());
    }

    public ResponseEntity<ProblemParameters> updateProblemParametersByContextId(UUID contextId, ProblemParameters problemParameters) {
        ProblemContext problemContext = ProblemContext.getInstance(contextId);
        if(problemContext == null || problemContext.getUniversityEmployees() == null){
            return ResponseEntity.badRequest().build();
        }
        problemContext.setProblemParameters(problemParameters);
        return ResponseEntity.ok().body(problemContext.getProblemParameters());
    }
}
