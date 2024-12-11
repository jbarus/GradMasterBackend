package com.github.jbarus.gradmasterbackend.controllers;

import com.github.jbarus.gradmasterbackend.exceptions.MalformedRequestException;
import com.github.jbarus.gradmasterbackend.exceptions.NoSuchDataException;
import com.github.jbarus.gradmasterbackend.exceptions.calculationstart.NoSuchContextException;
import com.github.jbarus.gradmasterbackend.models.dto.ProblemParametersDTO;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemParameters;
import com.github.jbarus.gradmasterbackend.services.ProblemParametersService;
import org.springframework.http.HttpStatus;
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
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }catch (NoSuchContextException ex) {
            return ResponseEntity.notFound().build();
        }catch (MalformedRequestException e){
            return ResponseEntity.badRequest().build();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{contextId}")
    public ResponseEntity<ProblemParametersDTO> getProblemParameters(@PathVariable UUID contextId) {
        try {
            ProblemParametersDTO result = problemParametersService.getProblemParameters(contextId);
            return ResponseEntity.ok(result);
        }catch (NoSuchContextException | NoSuchDataException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping("/{contextId}")
    public ResponseEntity<ProblemParametersDTO> updateProblemParameters(
            @PathVariable UUID contextId,
            @RequestBody ProblemParameters problemParameters) {
        try {
            ProblemParametersDTO result = problemParametersService.updateProblemParameters(contextId, problemParameters);
            return ResponseEntity.ok(result);
        }catch (NoSuchContextException | NoSuchDataException ex) {
            return ResponseEntity.notFound().build();
        }catch (MalformedRequestException e){
            return ResponseEntity.badRequest().body(null);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @DeleteMapping("/{contextId}")
    public ResponseEntity<ProblemParametersDTO> deleteProblemParameters(@PathVariable UUID contextId) {
        try {
            ProblemParametersDTO result = problemParametersService.deleteProblemParameters(contextId);
            return ResponseEntity.ok(result);
        }catch (NoSuchContextException | NoSuchDataException ex) {
            return ResponseEntity.notFound().build();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
