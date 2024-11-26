package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RelationService {

    public List<UUID> addPositiveRelation(UUID contextId, List<UUID> relations) {
        return addRelation(contextId, relations, RelationType.POSITIVE);
    }

    public List<UUID> addNegativeRelation(UUID contextId, List<UUID> relations) {
        return addRelation(contextId, relations, RelationType.NEGATIVE);
    }

    public List<UUID> updatePositiveRelation(UUID contextId, List<UUID> relations) {
        return updateRelation(contextId, relations, RelationType.POSITIVE);
    }

    public List<UUID> updateNegativeRelation(UUID contextId, List<UUID> relations) {
        return updateRelation(contextId, relations, RelationType.NEGATIVE);
    }

    public Map<String, List<UUID>> getAllRelations(UUID contextId) {
        ProblemContext context = getValidContext(contextId);
        Map<String, List<UUID>> relations = new HashMap<>();
        relations.put("positiveRelations", context.getPositiveCorrelationMapping());
        relations.put("negativeRelations", context.getNegativeCorrelationMapping());
        return relations;
    }

    public List<UniversityEmployee> getPositiveRelations(UUID contextId) {
        return getRelations(contextId, RelationType.POSITIVE);
    }

    public List<UniversityEmployee> getNegativeRelations(UUID contextId) {
        return getRelations(contextId, RelationType.NEGATIVE);
    }

    private List<UUID> addRelation(UUID contextId, List<UUID> relations, RelationType type) {
        validateRelations(contextId, relations);
        ProblemContext context = getValidContext(contextId);
        if (type == RelationType.POSITIVE) {
            context.setPositiveCorrelationMapping(relations);
            return context.getPositiveCorrelationMapping();
        } else {
            context.setNegativeCorrelationMapping(relations);
            return context.getNegativeCorrelationMapping();
        }
    }

    private List<UUID> updateRelation(UUID contextId, List<UUID> relations, RelationType type) {
        return addRelation(contextId, relations, type);
    }

    private List<UniversityEmployee> getRelations(UUID contextId, RelationType type) {
        ProblemContext context = getValidContext(contextId);
        List<UUID> relationIds = type == RelationType.POSITIVE
                ? context.getPositiveCorrelationMapping()
                : context.getNegativeCorrelationMapping();

        return context.getUniversityEmployees().stream()
                .filter(employee -> relationIds.contains(employee.getId()))
                .toList();
    }

    private ProblemContext getValidContext(UUID contextId) {
        ProblemContext context = ProblemContext.getInstance(contextId);
        if (context == null) {
            throw new IllegalArgumentException("Invalid context ID: " + contextId);
        }
        return context;
    }

    private void validateRelations(UUID contextId, List<UUID> relations) {
        if (relations.isEmpty() || relations.size() < 2 || relations.size() % 2 != 0) {
            throw new IllegalArgumentException("Relations must contain at least two UUIDs and must be even in number.");
        }

        ProblemContext context = getValidContext(contextId);
        List<UUID> validEmployeeIds = context.getUniversityEmployees().stream()
                .map(UniversityEmployee::getId)
                .toList();

        for (UUID relationId : relations) {
            if (!validEmployeeIds.contains(relationId)) {
                throw new IllegalArgumentException("Invalid UUID in relations: " + relationId);
            }
        }
    }

    private enum RelationType {
        POSITIVE, NEGATIVE
    }
}
