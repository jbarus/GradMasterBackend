package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.exceptions.MalformedRequestException;
import com.github.jbarus.gradmasterbackend.exceptions.NoSuchDataException;
import com.github.jbarus.gradmasterbackend.exceptions.calculationstart.NoSuchContextException;
import com.github.jbarus.gradmasterbackend.mappers.RelationMapper;
import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import com.github.jbarus.gradmasterbackend.models.dto.RelationDTO;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;
import com.github.jbarus.gradmasterbackend.utils.ContextUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RelationService {
    public RelationDTO addPositiveRelations(UUID contextId, List<UUID> relations) {
        return addRelations(contextId, relations, RelationType.POSITIVE);
    }

    public RelationDTO addNegativeRelations(UUID contextId, List<UUID> relations) {
        return addRelations(contextId, relations, RelationType.NEGATIVE);
    }

    public RelationDTO updatePositiveRelations(UUID contextId, List<UUID> relations) {
        return updateRelations(contextId, relations, RelationType.POSITIVE);
    }

    public RelationDTO updateNegativeRelations(UUID contextId, List<UUID> relations) {
        return updateRelations(contextId, relations, RelationType.NEGATIVE);
    }

    public RelationDTO getPositiveRelations(UUID contextId) {
        return getRelations(contextId, RelationType.POSITIVE);
    }

    public RelationDTO getNegativeRelations(UUID contextId) {
        return getRelations(contextId, RelationType.NEGATIVE);
    }

    public RelationDTO deletePositiveRelations(UUID contextId) {
        return deleteRelations(contextId, RelationType.POSITIVE);
    }

    public RelationDTO deleteNegativeRelations(UUID contextId) {
        return deleteRelations(contextId, RelationType.NEGATIVE);
    }

    private RelationDTO addRelations(UUID contextId, List<UUID> relations, RelationType type) {
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

    private RelationDTO updateRelations(UUID contextId, List<UUID> relations, RelationType type) {
        ProblemContext context = getValidContext(contextId);
        if (type == RelationType.POSITIVE) {
            if(context.getPositiveCorrelationMapping() == null) {
                throw new NoSuchDataException("Invalid problem parameters ID: " + contextId);
            }
        } else {
            if(context.getNegativeCorrelationMapping() == null) {
                throw new NoSuchDataException("Invalid problem parameters ID: " + contextId);
            }
        }
        return addRelations(contextId, relations, type);
    }

    private RelationDTO getRelations(UUID contextId, RelationType type) {
        ProblemContext context = getValidContext(contextId);
        if (type == RelationType.POSITIVE) {
            if(context.getPositiveCorrelationMapping() == null) {
                throw new NoSuchDataException("Invalid problem parameters ID: " + contextId);
            }
        } else {
            if(context.getNegativeCorrelationMapping() == null) {
                throw new NoSuchDataException("Invalid problem parameters ID: " + contextId);
            }
        }
        List<UniversityEmployee> relations = type == RelationType.POSITIVE
                ? ContextUtils.getPositiveRelationListByProblemContext(context)
                : ContextUtils.getNegativeRelationListByProblemContext(context);

        return RelationMapper.convertRelationListToRelationDTO(contextId, relations);
    }

    public RelationDTO deleteRelations(UUID contextId, RelationType type) {
        ProblemContext context = getValidContext(contextId);
        if (type == RelationType.POSITIVE) {
            if(context.getPositiveCorrelationMapping() == null) {
                throw new NoSuchDataException("Invalid problem parameters ID: " + contextId);
            }
        } else {
            if(context.getNegativeCorrelationMapping() == null) {
                throw new NoSuchDataException("Invalid problem parameters ID: " + contextId);
            }
        }
        List<UniversityEmployee> relations = type == RelationType.POSITIVE
                ? ContextUtils.getPositiveRelationListByProblemContext(context)
                : ContextUtils.getNegativeRelationListByProblemContext(context);

        if (type == RelationType.POSITIVE) {
            context.setPositiveCorrelationMapping(null);
        } else {
            context.setNegativeCorrelationMapping(null);
        }
        return RelationMapper.convertRelationListToRelationDTO(contextId, relations);
    }

    private ProblemContext getValidContext(UUID contextId) {
        ProblemContext context = ProblemContext.getInstance(contextId);
        if (context == null) {
            throw new NoSuchContextException("Invalid context ID: " + contextId);
        }
        return context;
    }

    private void validateRelations(UUID contextId, List<UUID> relations) {
        if (relations.isEmpty() || relations.size() < 2 || relations.size() % 2 != 0) {
            throw new MalformedRequestException("Relations must contain at least two UUIDs and must be even in number.");
        }

        ProblemContext context = getValidContext(contextId);
        List<UUID> validEmployeeIds = context.getUniversityEmployees().stream()
                .map(UniversityEmployee::getId)
                .toList();

        for (UUID relationId : relations) {
            if (!validEmployeeIds.contains(relationId)) {
                throw new MalformedRequestException("Invalid UUID in relations: " + relationId);
            }
        }
    }

    private enum RelationType {
        POSITIVE, NEGATIVE
    }
}
