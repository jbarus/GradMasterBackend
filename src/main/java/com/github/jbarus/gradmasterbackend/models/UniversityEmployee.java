package com.github.jbarus.gradmasterbackend.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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
    private List<Student> reviewedStudents = new ArrayList<>();

    public UniversityEmployee(String firstName, String secondName, boolean isHabilitated, LocalTime timeslotStart, LocalTime timeslotEnd, int preferredCommitteeDuration) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.secondName = secondName;
        this.isHabilitated = isHabilitated;
        this.timeslotStart = timeslotStart;
        this.timeslotEnd = timeslotEnd;
        this.preferredCommitteeDuration = preferredCommitteeDuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UniversityEmployee that = (UniversityEmployee) o;

        if (isHabilitated != that.isHabilitated) return false;
        if (!firstName.equals(that.firstName)) return false;
        return secondName.equals(that.secondName);
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + secondName.hashCode();
        result = 31 * result + (isHabilitated ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UniversityEmployee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", isHabilitated='" + isHabilitated + '\'' +
                '}';
    }
}
