package com.github.jbarus.gradmasterbackend.controllers;

import com.github.jbarus.gradmasterbackend.exceptions.CalculationInProgressException;
import com.github.jbarus.gradmasterbackend.exceptions.InvalidInputException;
import com.github.jbarus.gradmasterbackend.exceptions.NoSuchDataException;
import com.github.jbarus.gradmasterbackend.exceptions.calculationstart.NoSuchContextException;
import com.github.jbarus.gradmasterbackend.models.dto.SolutionDTO;
import com.github.jbarus.gradmasterbackend.models.problem.Solution;
import com.github.jbarus.gradmasterbackend.services.SolutionService;
import org.springframework.http.HttpStatus;
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
        } catch (NoSuchContextException | NoSuchDataException ex) {
            return ResponseEntity.notFound().build();
        } catch (CalculationInProgressException e){
            return ResponseEntity.badRequest().build();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{contextId}")
    public ResponseEntity<SolutionDTO> updateSolution(
            @PathVariable UUID contextId,
            @RequestBody Solution solution
    ) {
        try {
            SolutionDTO updatedSolution = solutionService.updateSolutionByContextId(contextId, solution);
            return ResponseEntity.ok(updatedSolution);
        } catch (NoSuchContextException | NoSuchDataException ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{contextId}")
    public ResponseEntity<SolutionDTO> setSolution(
            @PathVariable UUID contextId,
            @RequestBody Solution solution
    ) {
        try {
            SolutionDTO newSolution = solutionService.setSolutionByContextId(contextId, solution);
            return ResponseEntity.ok(newSolution);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{contextId}")
    public ResponseEntity<SolutionDTO> deleteSolution(@PathVariable UUID contextId) {
        try {
            SolutionDTO deletedSolution = solutionService.deleteSolutionByContextId(contextId);
            return ResponseEntity.ok(deletedSolution);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}