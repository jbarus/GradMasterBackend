package com.github.jbarus.gradmasterbackend.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Student {
    private UUID id;
    private String firstName;
    private String secondName;

    public Student(UUID id, String firstName, String secondName) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public Student(String firstName, String secondName) {
        this(UUID.randomUUID(), firstName, secondName);
    }

    public Student() {
    }
}
