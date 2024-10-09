package com.github.jbarus.gradmasterbackend.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class ContextDTO {
    UUID id;
    String name;
    LocalDate date;

    public ContextDTO(UUID id, String name, LocalDate date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }
}
