package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


@Service
public class RelationService {
    public ResponseEntity<List<UUID>> addPositiveRelation(List<UUID> relation, UUID contextId) {
        if(!checkValidity(relation, contextId)){
            return ResponseEntity.badRequest().body(relation);
        }
        ProblemContext problemContext = ProblemContext.getInstance(contextId);
        problemContext.setPositiveCorrelationMapping(relation);
        return ResponseEntity.ok().body(problemContext.getPositiveCorrelationMapping());
    }
    public ResponseEntity<List<UUID>> addNegativeRelation(List<UUID> relation, UUID contextId) {
        if(!checkValidity(relation, contextId)){
            return ResponseEntity.badRequest().body(relation);
        }
        ProblemContext problemContext = ProblemContext.getInstance(contextId);
        problemContext.setNegativeCorrelationMapping(relation);
        return ResponseEntity.ok().body(problemContext.getPositiveCorrelationMapping());
    }

    public ResponseEntity<List<UUID>> updateNegativeRelation(UUID contextId, List<UUID> relation) {
        if(!checkValidity(relation, contextId)){
            return ResponseEntity.badRequest().body(relation);
        }
        ProblemContext problemContext = ProblemContext.getInstance(contextId);
        problemContext.setNegativeCorrelationMapping(relation);
        return ResponseEntity.ok().body(problemContext.getPositiveCorrelationMapping());
    }

    public ResponseEntity<List<UUID>> updatePositiveRelation(UUID contextId, List<UUID> relation) {
        if(!checkValidity(relation, contextId)){
            return ResponseEntity.badRequest().body(relation);
        }
        ProblemContext problemContext = ProblemContext.getInstance(contextId);
        problemContext.setPositiveCorrelationMapping(relation);
        return ResponseEntity.ok().body(problemContext.getPositiveCorrelationMapping());
    }

    public ResponseEntity<HashMap<String, List<UUID>>> getAllRelations(UUID contextId) {
        ProblemContext problemContext = ProblemContext.getInstance(contextId);
        if (problemContext == null) {
            return ResponseEntity.notFound().build();
        }

        HashMap<String, List<UUID>> relations = new HashMap<>();
        relations.put("positiveRelations", problemContext.getPositiveCorrelationMapping());
        relations.put("negativeRelations", problemContext.getNegativeCorrelationMapping());

        return ResponseEntity.ok(relations);
    }

    private boolean checkValidity(List<UUID> relation, UUID contextId) {
        if(relation.isEmpty() || relation.size() < 2 || relation.size() % 2 != 0) {
            return false;
        }
        ProblemContext problemContext = ProblemContext.getInstance(contextId);

        return problemContext != null;
    }

    public ResponseEntity<List<UniversityEmployee>> getPositiveRelations(UUID contextId) {
        ProblemContext problemContext = ProblemContext.getInstance(contextId);
        if (problemContext == null) {
            return ResponseEntity.notFound().build();
        }

        List<UniversityEmployee> positiveRelations = new ArrayList<>();
        for(UUID uuid : problemContext.getPositiveCorrelationMapping()) {
            for(UniversityEmployee universityEmployee : problemContext.getUniversityEmployees()){
                if(uuid.equals(universityEmployee.getId())) {
                    positiveRelations.add(universityEmployee);
                }
            }
        }
        return ResponseEntity.ok().body(positiveRelations);
    }

    public ResponseEntity<List<UniversityEmployee>> getNegativeRelations(UUID contextId) {
        ProblemContext problemContext = ProblemContext.getInstance(contextId);
        if (problemContext == null) {
            return ResponseEntity.notFound().build();
        }

        List<UniversityEmployee> negativeRelations = new ArrayList<>();
        for(UUID uuid : problemContext.getNegativeCorrelationMapping()) {
            for(UniversityEmployee universityEmployee : problemContext.getUniversityEmployees()){
                if(uuid.equals(universityEmployee.getId())) {
                    negativeRelations.add(universityEmployee);
                }
            }
        }
        return ResponseEntity.ok().body(negativeRelations);
    }
}
