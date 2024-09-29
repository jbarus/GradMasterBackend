package com.github.jbarus.gradmasterbackend.pipelines.filters;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FilterOrder {
    int value();
}
