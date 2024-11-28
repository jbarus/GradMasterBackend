package com.github.jbarus.gradmasterbackend.mappers;

import com.github.jbarus.gradmasterbackend.models.dto.ProblemParametersDTO;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemParameters;

import java.util.UUID;

public class ProblemParametersMapper {
    public static ProblemParameters convertProblemParametersDTOToProblemParameters(ProblemParametersDTO dto) {
        if (dto == null) {
            return null;
        }

        return new ProblemParameters(
                dto.getCommitteeSize(),
                dto.getMaxNumberOfNonHabilitatedEmployees(),
                dto.getCalculationTimeInSeconds()
        );
    }

    public static ProblemParametersDTO convertProblemParametersToProblemParametersDTO(UUID contextId, ProblemParameters entity) {
        if (entity == null) {
            return null;
        }

        return ProblemParametersDTO.builder()
                .id(contextId)
                .committeeSize(entity.getCommitteeSize())
                .maxNumberOfNonHabilitatedEmployees(entity.getMaxNumberOfNonHabilitatedEmployees())
                .calculationTimeInSeconds(entity.getCalculationTimeInSeconds())
                .build();
    }
}
