package com.github.jbarus.gradmasterbackend.models.problem;

import com.github.jbarus.gradmasterbackend.models.Student;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Solution {
    private List<Committee> committees;
    private List<Student> unassignedStudents;

    public Solution(List<Committee> committees) {
        this.committees = committees;
    }

    public Solution() {
    }
}
