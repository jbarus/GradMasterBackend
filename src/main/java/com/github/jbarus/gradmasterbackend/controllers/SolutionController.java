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
        try {
            SolutionDTO solutionDTO = solutionService.getSolutionByContextId(contextId);
            return ResponseEntity.ok(solutionDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{contextId}")
    public ResponseEntity<SolutionDTO> updateSolution(
            @PathVariable UUID contextId,
            @RequestBody SolutionDTO solutionDTO
    ) {
        try {
            SolutionDTO updatedSolution = solutionService.updateSolutionByContextId(contextId, solutionDTO);
            return ResponseEntity.ok(updatedSolution);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/{contextId}")
    public ResponseEntity<SolutionDTO> setSolution(
            @PathVariable UUID contextId,
            @RequestBody SolutionDTO solutionDTO
    ) {
        try {
            SolutionDTO newSolution = solutionService.setSolutionByContextId(contextId, solutionDTO);
            return ResponseEntity.ok(newSolution);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}