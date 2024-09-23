package com.github.jbarus.gradmasterbackend.Pipelines.Filters;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FilterGroup {
    FilterGroupType value();
}
