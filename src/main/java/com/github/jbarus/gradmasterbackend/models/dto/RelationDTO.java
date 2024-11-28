package com.github.jbarus.gradmasterbackend.models.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelationDTO {
    private UUID id;
    private List<UUID> relationList;
}
