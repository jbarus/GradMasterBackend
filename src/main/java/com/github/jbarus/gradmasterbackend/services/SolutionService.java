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

    public ResponseEntity<SolutionDTO> getSolutionByContextId(UUID contextId) {
        ProblemContext problemContext = ProblemContext.getInstance(contextId);
        if(problemContext == null || problemContext.getSolution() == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(SolutionMapper.convertSolutionToSolutionDTO(problemContext));
    }

    public ResponseEntity<SolutionDTO> updateSolutionByContextId(UUID contextId, SolutionDTO solutionDTO) {
        ProblemContext problemContext = ProblemContext.getInstance(contextId);
        if(problemContext == null || problemContext.getSolution() == null){
            return ResponseEntity.badRequest().build();
        }

        problemContext.setSolution(SolutionMapper.convertSolutionDTOToSolution(solutionDTO));

        return ResponseEntity.ok().body(SolutionMapper.convertSolutionToSolutionDTO(problemContext));
    }

    public ResponseEntity<SolutionDTO> setSolutionByContextId(UUID contextId, SolutionDTO solutionDTO) {
        ProblemContext problemContext = ProblemContext.getInstance(contextId);
        if(problemContext == null){
            return ResponseEntity.badRequest().build();
        }

        problemContext.setSolution(SolutionMapper.convertSolutionDTOToSolution(solutionDTO));

        return ResponseEntity.ok().body(SolutionMapper.convertSolutionToSolutionDTO(problemContext));
    }

}
