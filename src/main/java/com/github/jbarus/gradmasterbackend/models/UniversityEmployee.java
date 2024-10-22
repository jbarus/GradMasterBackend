package com.github.jbarus.gradmasterbackend.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class UniversityEmployee {
    private UUID id;
    private String firstName;
    private String secondName;
    private boolean isHabilitated;
    private LocalTime timeslotStart;
    private LocalTime timeslotEnd;
    private int preferredCommitteeDuration;

    public UniversityEmployee(UUID id, String firstName, String secondName, boolean isHabilitated, LocalTime timeslotStart, LocalTime timeslotEnd, int preferredCommitteeDuration) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.isHabilitated = isHabilitated;
        this.timeslotStart = timeslotStart;
        this.timeslotEnd = timeslotEnd;
        this.preferredCommitteeDuration = preferredCommitteeDuration;
    }

    public UniversityEmployee(String firstName, String secondName, boolean isHabilitated, LocalTime timeslotStart, LocalTime timeslotEnd, int preferredCommitteeDuration) {
        this(UUID.randomUUID(), firstName, secondName,isHabilitated,timeslotStart, timeslotEnd, preferredCommitteeDuration);
    }

    public UniversityEmployee() {
    }

    @Override
    public String toString() {
        return "UniversityEmployee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", isHabilitated=" + isHabilitated +
                ", timeslotStart=" + timeslotStart +
                ", timeslotEnd=" + timeslotEnd +
                ", preferredCommitteeDuration=" + preferredCommitteeDuration +
                '}';
    }
}
