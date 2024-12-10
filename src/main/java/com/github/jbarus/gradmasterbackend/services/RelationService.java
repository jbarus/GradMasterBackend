package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.mappers.RelationMapper;
import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import com.github.jbarus.gradmasterbackend.models.dto.RelationDTO;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;
import com.github.jbarus.gradmasterbackend.utils.ContextUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RelationService {
    public RelationDTO addPositiveRelation(UUID contextId, List<UUID> relations) {
        return addRelation(contextId, relations, RelationType.POSITIVE);
    }

    public RelationDTO addNegativeRelation(UUID contextId, List<UUID> relations) {
        return addRelation(contextId, relations, RelationType.NEGATIVE);
    }

    public RelationDTO updatePositiveRelation(UUID contextId, List<UUID> relations) {
        return updateRelation(contextId, relations, RelationType.POSITIVE);
    }

    public RelationDTO updateNegativeRelation(UUID contextId, List<UUID> relations) {
        return updateRelation(contextId, relations, RelationType.NEGATIVE);
    }

    public RelationDTO getPositiveRelations(UUID contextId) {
        return getRelations(contextId, RelationType.POSITIVE);
    }

    public RelationDTO getNegativeRelations(UUID contextId) {
        return getRelations(contextId, RelationType.NEGATIVE);
    }

    private RelationDTO addRelation(UUID contextId, List<UUID> relations, RelationType type) {
        validateRelations(contextId, relations);
        ProblemContext context = getValidContext(contextId);

        if (type == RelationType.POSITIVE) {
            context.setPositiveCorrelationMapping(relations);
            return RelationMapper.convertRelationListToRelationDTO(contextId, ContextUtils.getPositiveRelationListByProblemContext(context));
        } else {
            context.setNegativeCorrelationMapping(relations);
            return RelationMapper.convertRelationListToRelationDTO(contextId, ContextUtils.getNegativeRelationListByProblemContext(context));
        }
    }

    private RelationDTO updateRelation(UUID contextId, List<UUID> relations, RelationType type) {
        return addRelation(contextId, relations, type);
    }

    private RelationDTO getRelations(UUID contextId, RelationType type) {
        ProblemContext context = getValidContext(contextId);
        List<UniversityEmployee> relations = type == RelationType.POSITIVE
                ? ContextUtils.getPositiveRelationListByProblemContext(context)
                : ContextUtils.getNegativeRelationListByProblemContext(context);

        return RelationMapper.convertRelationListToRelationDTO(contextId, relations);
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
