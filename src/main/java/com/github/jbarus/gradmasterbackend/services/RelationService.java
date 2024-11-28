package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.mappers.RelationMapper;
import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import com.github.jbarus.gradmasterbackend.models.dto.RelationDTO;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RelationService {
    public RelationDTO addPositiveRelation(UUID contextId, RelationDTO relationDTO) {
        return addRelation(contextId, relationDTO, RelationType.POSITIVE);
    }

    public RelationDTO addNegativeRelation(UUID contextId, RelationDTO relationDTO) {
        return addRelation(contextId, relationDTO, RelationType.NEGATIVE);
    }

    public RelationDTO updatePositiveRelation(UUID contextId, RelationDTO relationDTO) {
        return updateRelation(contextId, relationDTO, RelationType.POSITIVE);
    }

    public RelationDTO updateNegativeRelation(UUID contextId, RelationDTO relationDTO) {
        return updateRelation(contextId, relationDTO, RelationType.NEGATIVE);
    }

    public RelationDTO getPositiveRelations(UUID contextId) {
        return getRelations(contextId, RelationType.POSITIVE);
    }

    public RelationDTO getNegativeRelations(UUID contextId) {
        return getRelations(contextId, RelationType.NEGATIVE);
    }

    private RelationDTO addRelation(UUID contextId, RelationDTO relationDTO, RelationType type) {
        List<UUID> relations = RelationMapper.convertRelationDTOToRelationList(relationDTO);
        validateRelations(contextId, relations);
        ProblemContext context = getValidContext(contextId);

        if (type == RelationType.POSITIVE) {
            context.setPositiveCorrelationMapping(relations);
            return RelationMapper.convertRelationListToRelationDTO(contextId, context.getPositiveCorrelationMapping());
        } else {
            context.setNegativeCorrelationMapping(relations);
            return RelationMapper.convertRelationListToRelationDTO(contextId, context.getNegativeCorrelationMapping());
        }
    }

    private RelationDTO updateRelation(UUID contextId, RelationDTO relationDTO, RelationType type) {
        return addRelation(contextId, relationDTO, type);
    }

    private RelationDTO getRelations(UUID contextId, RelationType type) {
        ProblemContext context = getValidContext(contextId);
        List<UUID> relationIds = type == RelationType.POSITIVE
                ? context.getPositiveCorrelationMapping()
                : context.getNegativeCorrelationMapping();

        return RelationMapper.convertRelationListToRelationDTO(contextId, relationIds);
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
