package com.github.jbarus.gradmasterbackend.controllers;

import com.github.jbarus.gradmasterbackend.models.dto.RelationDTO;
import com.github.jbarus.gradmasterbackend.services.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/relations")
public class RelationController {

    private final RelationService relationService;

    @Autowired
    public RelationController(RelationService relationService) {
        this.relationService = relationService;
    }

    @PostMapping("/positive/{contextId}")
    public ResponseEntity<?> addPositiveRelation(@PathVariable UUID contextId, @RequestBody List<UUID> relations) {
        return handleRelationOperation(() -> relationService.addPositiveRelation(contextId, relations));
    }

    @PostMapping("/negative/{contextId}")
    public ResponseEntity<?> addNegativeRelation(@PathVariable UUID contextId, @RequestBody List<UUID> relations) {
        return handleRelationOperation(() -> relationService.addNegativeRelation(contextId, relations));
    }

    @PutMapping("/positive/{contextId}")
    public ResponseEntity<?> updatePositiveRelation(@PathVariable UUID contextId, @RequestBody List<UUID> relations) {
        return handleRelationOperation(() -> relationService.updatePositiveRelation(contextId, relations));
    }

    @PutMapping("/negative/{contextId}")
    public ResponseEntity<?> updateNegativeRelation(@PathVariable UUID contextId, @RequestBody List<UUID> relations) {
        return handleRelationOperation(() -> relationService.updateNegativeRelation(contextId, relations));
    }

    @GetMapping("/positive/{contextId}")
    public ResponseEntity<?> getPositiveRelations(@PathVariable UUID contextId) {
        return handleRelationOperation(() -> relationService.getPositiveRelations(contextId));
    }

    @GetMapping("/negative/{contextId}")
    public ResponseEntity<?> getNegativeRelations(@PathVariable UUID contextId) {
        return handleRelationOperation(() -> relationService.getNegativeRelations(contextId));
    }

    private ResponseEntity<?> handleRelationOperation(ServiceOperation operation) {
        try {
            return ResponseEntity.ok(operation.execute());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }

    @FunctionalInterface
    private interface ServiceOperation {
        Object execute() throws Exception;
    }
}
