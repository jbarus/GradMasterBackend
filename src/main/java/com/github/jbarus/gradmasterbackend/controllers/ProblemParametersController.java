package com.github.jbarus.gradmasterbackend.controllers;

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
    public ResponseEntity<ProblemParameters> setProblemParameters(@PathVariable UUID contextId, @RequestBody ProblemParameters problemParameters) {
        return problemParametersService.setProblemParametersByContextId(contextId, problemParameters);
    }

    @GetMapping("/{contextId}")
    public ResponseEntity<ProblemParameters> getProblemParameters(@PathVariable UUID contextId) {
        return problemParametersService.getProblemParametersByContextId(contextId);
    }

    @PutMapping("/{contextId}")
    public ResponseEntity<ProblemParameters> updateProblemParameters(@PathVariable UUID contextId, @RequestBody ProblemParameters problemParameters) {
        return problemParametersService.updateProblemParametersByContextId(contextId, problemParameters);
    }

}
