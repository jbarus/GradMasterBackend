package com.github.jbarus.gradmasterbackend.controllers;

import com.github.jbarus.gradmasterbackend.exceptions.MalformedRequestException;
import com.github.jbarus.gradmasterbackend.exceptions.NoSuchDataException;
import com.github.jbarus.gradmasterbackend.exceptions.calculationstart.NoSuchContextException;
import com.github.jbarus.gradmasterbackend.services.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return handleCreateRelationOperation(() -> relationService.addPositiveRelations(contextId, relations));
    }

    @PostMapping("/negative/{contextId}")
    public ResponseEntity<?> addNegativeRelation(@PathVariable UUID contextId, @RequestBody List<UUID> relations) {
        return handleCreateRelationOperation(() -> relationService.addNegativeRelations(contextId, relations));
    }

    @PatchMapping("/positive/{contextId}")
    public ResponseEntity<?> updatePositiveRelation(@PathVariable UUID contextId, @RequestBody List<UUID> relations) {
        return handleRelationOperation(() -> relationService.updatePositiveRelations(contextId, relations));
    }

    @PatchMapping("/negative/{contextId}")
    public ResponseEntity<?> updateNegativeRelation(@PathVariable UUID contextId, @RequestBody List<UUID> relations) {
        return handleRelationOperation(() -> relationService.updateNegativeRelations(contextId, relations));
    }

    @GetMapping("/positive/{contextId}")
    public ResponseEntity<?> getPositiveRelations(@PathVariable UUID contextId) {
        return handleRelationOperation(() -> relationService.getPositiveRelations(contextId));
    }

    @GetMapping("/negative/{contextId}")
    public ResponseEntity<?> getNegativeRelations(@PathVariable UUID contextId) {
        return handleRelationOperation(() -> relationService.getNegativeRelations(contextId));
    }

    @DeleteMapping("/positive/{contextId}")
    public ResponseEntity<?> deletePositiveRelation(@PathVariable UUID contextId) {
        return handleRelationOperation(()->relationService.deletePositiveRelations(contextId));
    }

    @DeleteMapping("/negative/{contextId}")
    public ResponseEntity<?> deleteNegativeRelation(@PathVariable UUID contextId) {
        return handleRelationOperation(()->relationService.deleteNegativeRelations(contextId));
    }

    private ResponseEntity<?> handleRelationOperation(ServiceOperation operation) {
        try {
            return ResponseEntity.ok(operation.execute());
        }catch (NoSuchContextException | NoSuchDataException ex) {
            return ResponseEntity.notFound().build();
        }catch (MalformedRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }
    private ResponseEntity<?> handleCreateRelationOperation(ServiceOperation operation) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(operation.execute());
        }catch (NoSuchContextException | NoSuchDataException ex) {
            return ResponseEntity.notFound().build();
        }catch (MalformedRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @FunctionalInterface
    private interface ServiceOperation {
        Object execute() throws Exception;
    }
}
