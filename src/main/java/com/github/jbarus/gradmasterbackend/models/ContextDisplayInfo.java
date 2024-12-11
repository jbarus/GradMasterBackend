package com.github.jbarus.gradmasterbackend.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ContextDisplayInfo {
    private String name;
    private LocalDate date;
}
