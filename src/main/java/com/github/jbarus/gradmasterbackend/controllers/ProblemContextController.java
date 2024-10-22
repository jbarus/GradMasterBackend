package com.github.jbarus.gradmasterbackend.controllers;

import com.github.jbarus.gradmasterbackend.models.dto.ContextDisplayInfoDTO;
import com.github.jbarus.gradmasterbackend.services.ProblemContextService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/problem-contexts")
public class ProblemContextController {

    private final ProblemContextService problemContextService;

    public ProblemContextController(ProblemContextService problemContextService) {
        this.problemContextService = problemContextService;
    }

    @GetMapping
    public ResponseEntity<List<ContextDisplayInfoDTO>> getAllContexts() {
        List<ContextDisplayInfoDTO> contexts = problemContextService.getAllContexts();
        return ResponseEntity.ok(contexts);
    }

    @GetMapping("/{contextId}")
    public ResponseEntity<ContextDisplayInfoDTO> getContextById(@PathVariable UUID contextId) {
        return problemContextService.getContextById(contextId);
    }

    @PutMapping("/{contextId}")
    public ResponseEntity<ContextDisplayInfoDTO> modifyContextById(@PathVariable UUID contextId, @RequestBody ContextDisplayInfoDTO contextDisplayInfoDTO) {
        return problemContextService.modifyContextById(contextId, contextDisplayInfoDTO);
    }

    @DeleteMapping("/{contextId}")
    public ResponseEntity<Void> deleteContextById(@PathVariable UUID contextId) {
        return problemContextService.deleteContextById(contextId);
    }
}
