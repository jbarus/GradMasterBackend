package com.github.jbarus.gradmasterbackend.controllers;

import com.github.jbarus.gradmasterbackend.exceptions.BusinessLogicException;
import com.github.jbarus.gradmasterbackend.exceptions.calculationstart.MissingDataException;
import com.github.jbarus.gradmasterbackend.exceptions.calculationstart.NoSuchContextException;
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
    public ResponseEntity<?> publishProblemToQueue(@PathVariable UUID contextId) {
        try {
            ProblemDTO problemDTO = problemService.publishProblem(contextId);
            return ResponseEntity.ok(new Response<>(CalculationStartStatus.SUCCESS, problemDTO));
        } catch (NoSuchContextException e) {
            return ResponseEntity.badRequest().body(new Response<>(CalculationStartStatus.NO_SUCH_CONTEXT));
        } catch (MissingDataException e) {
            return ResponseEntity.badRequest().body(new Response<>(e.getStatus()));
        } catch (BusinessLogicException e) {
            return ResponseEntity.badRequest().body(new Response<>(CalculationStartStatus.INTERNAL_ERROR));
        }
    }
}
