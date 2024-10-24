package com.github.jbarus.gradmasterbackend.controllers;

import com.github.jbarus.gradmasterbackend.models.communication.CalculationStartStatus;
import com.github.jbarus.gradmasterbackend.models.communication.Response;
import com.github.jbarus.gradmasterbackend.models.dto.ProblemDTO;
import com.github.jbarus.gradmasterbackend.services.ProblemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/problems")
public class ProblemController {
    private final ProblemService problemService;

    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @PostMapping("/{contextId}")
    public ResponseEntity<Response<CalculationStartStatus, ProblemDTO>> publishProblemToQueue(@PathVariable UUID contextId){
        return problemService.publishProblem(contextId);
    }
}
