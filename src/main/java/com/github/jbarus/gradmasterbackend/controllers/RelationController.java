package com.github.jbarus.gradmasterbackend.controllers;

import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;
import com.github.jbarus.gradmasterbackend.services.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
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
    public ResponseEntity<List<UUID>> addPositiveRelation(@PathVariable UUID contextId, @RequestBody List<UUID> relation) {
        return relationService.addPositiveRelation(relation, contextId);
    }

    @PostMapping("/negative/{contextId}")
    public ResponseEntity<List<UUID>> addNegativeRelation(@PathVariable UUID contextId, @RequestBody List<UUID> relation) {
        return relationService.addNegativeRelation(relation, contextId);
    }

    @PutMapping("/positive/{contextId}")
    public ResponseEntity<List<UUID>> updatePositiveRelation(@PathVariable UUID contextId, @RequestBody List<UUID> relation) {
        return relationService.updatePositiveRelation(contextId, relation);
    }

    @PutMapping("/negative/{contextId}")
    public ResponseEntity<List<UUID>> updateNegativeRelation(@PathVariable UUID contextId, @RequestBody List<UUID> relation) {
        return relationService.updateNegativeRelation(contextId, relation);
    }

    @GetMapping("/all/{contextId}")
    public ResponseEntity<HashMap<String, List<UUID>>> getAllRelations(@PathVariable UUID contextId) {
        return relationService.getAllRelations(contextId);
    }

    @PostMapping("/test/{contextId}")
    public ResponseEntity<HashMap<String,List<UUID>>> testRelationAdd(@PathVariable UUID contextId) {
        ProblemContext problemContext = ProblemContext.getInstance(contextId);
        if (problemContext == null) {
            return ResponseEntity.badRequest().build();
        }

        List<UUID> positiveRelation = new ArrayList<>();
        positiveRelation.add(problemContext.getUniversityEmployees().get(5).getId());
        positiveRelation.add(problemContext.getUniversityEmployees().get(7).getId());

        positiveRelation.add(problemContext.getUniversityEmployees().get(12).getId());
        positiveRelation.add(problemContext.getUniversityEmployees().get(13).getId());

        positiveRelation.add(problemContext.getUniversityEmployees().get(14).getId());
        positiveRelation.add(problemContext.getUniversityEmployees().get(15).getId());

        List<UUID> negativeRelation = new ArrayList<>();
        negativeRelation.add(problemContext.getUniversityEmployees().get(0).getId());
        negativeRelation.add(problemContext.getUniversityEmployees().get(1).getId());

        negativeRelation.add(problemContext.getUniversityEmployees().get(6).getId());
        negativeRelation.add(problemContext.getUniversityEmployees().get(8).getId());

        negativeRelation.add(problemContext.getUniversityEmployees().get(13).getId());
        negativeRelation.add(problemContext.getUniversityEmployees().get(15).getId());

        problemContext.setPositiveCorrelationMapping(positiveRelation);
        problemContext.setNegativeCorrelationMapping(negativeRelation);

        HashMap<String,List<UUID>> hashMap = new HashMap<>();
        hashMap.put("positive", positiveRelation);
        hashMap.put("negative", negativeRelation);
        return ResponseEntity.ok().body(hashMap);
    }

    @GetMapping("/positive/{contextId}")
    public ResponseEntity<List<UniversityEmployee>> getPositiveRelations(@PathVariable UUID contextId) {
        return relationService.getPositiveRelations(contextId);
    }

    @GetMapping("/negative/{contextId}")
    public ResponseEntity<List<UniversityEmployee>> getNegativeRelations(@PathVariable UUID contextId) {
        return relationService.getNegativeRelations(contextId);
    }
}
