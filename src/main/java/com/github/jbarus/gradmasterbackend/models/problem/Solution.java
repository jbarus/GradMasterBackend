package com.github.jbarus.gradmasterbackend.models.problem;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Solution {
    List<Committee> committees;

    public Solution(List<Committee> committees) {
        this.committees = committees;
    }

    public Solution() {
    }
}
