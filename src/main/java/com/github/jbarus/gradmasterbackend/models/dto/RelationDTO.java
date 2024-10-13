package com.github.jbarus.gradmasterbackend.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class RelationDTO {
    private List<UUID> positive;
    private List<UUID> negative;
}
