package com.github.jbarus.gradmasterbackend.mappers;

import com.github.jbarus.gradmasterbackend.models.dto.RelationDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RelationMapper {
    public static RelationDTO convertRelationListToRelationDTO(UUID id, List<UUID> relationList) {
        if (relationList == null) {
            return null;
        }

        return RelationDTO.builder()
                .id(id)
                .relationList(relationList)
                .build();
    }

    public static List<UUID> convertRelationDTOToRelationList(RelationDTO dto) {
        if (dto == null || dto.getRelationList() == null) {
            return null;
        }

        return new ArrayList<>(dto.getRelationList());
    }
}
