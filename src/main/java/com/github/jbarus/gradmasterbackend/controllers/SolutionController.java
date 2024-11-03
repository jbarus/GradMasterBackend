package com.github.jbarus.gradmasterbackend.controllers;

import com.github.jbarus.gradmasterbackend.models.dto.SolutionDTO;
import com.github.jbarus.gradmasterbackend.services.SolutionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/solutions")
public class SolutionController {

    private final SolutionService solutionService;

    public SolutionController(SolutionService solutionService) {
        this.solutionService = solutionService;
    }

    @GetMapping("/{contextId}")
    public ResponseEntity<SolutionDTO> getSolution(@PathVariable UUID contextId) {
        return solutionService.getSolutionByContextId(contextId);
    }

    @PutMapping("/{contextId}")
    public ResponseEntity<SolutionDTO> updateSolution(@PathVariable UUID contextId, @RequestBody SolutionDTO solutionDTO) {
        return solutionService.updateSolutionByContextId(contextId, solutionDTO);
    }

    @PostMapping("/{contextId}")
    public ResponseEntity<SolutionDTO> setSolution(@PathVariable UUID contextId, @RequestBody SolutionDTO solutionDTO) {
        return solutionService.setSolutionByContextId(contextId, solutionDTO);
    }
}
