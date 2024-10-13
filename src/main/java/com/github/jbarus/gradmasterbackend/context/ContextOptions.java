package com.github.jbarus.gradmasterbackend.context;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContextOptions {
    private int committeeSize = 3;
    private int maxNumberOfNonHabilitatedEmployees = committeeSize - 1;
}
