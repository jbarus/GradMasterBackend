package com.github.jbarus.gradmasterbackend.controllers;

import com.github.jbarus.gradmasterbackend.models.dto.ProblemParametersDTO;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemParameters;
import com.github.jbarus.gradmasterbackend.services.ProblemParametersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/problem-parameters")
public class ProblemParametersController {

    private final ProblemParametersService problemParametersService;

    public ProblemParametersController(ProblemParametersService problemParametersService) {
        this.problemParametersService = problemParametersService;
    }

    @PostMapping("/{contextId}")
    public ResponseEntity<ProblemParametersDTO> setProblemParameters(
            @PathVariable UUID contextId,
            @RequestBody ProblemParameters problemParameters) {
        try {
            ProblemParametersDTO result = problemParametersService.setProblemParameters(contextId, problemParameters);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{contextId}")
    public ResponseEntity<ProblemParametersDTO> getProblemParameters(@PathVariable UUID contextId) {
        try {
            ProblemParametersDTO result = problemParametersService.getProblemParameters(contextId);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{contextId}")
    public ResponseEntity<ProblemParametersDTO> updateProblemParameters(
            @PathVariable UUID contextId,
            @RequestBody ProblemParameters problemParameters) {
        try {
            ProblemParametersDTO result = problemParametersService.updateProblemParameters(contextId, problemParameters);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
