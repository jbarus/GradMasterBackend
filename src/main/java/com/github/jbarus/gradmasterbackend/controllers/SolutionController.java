package com.github.jbarus.gradmasterbackend.controllers;

import com.github.jbarus.gradmasterbackend.services.SolutionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/solutions")
public class SolutionController {

    private final SolutionService solutionService;

    public SolutionController(SolutionService solutionService) {
        this.solutionService = solutionService;
    }

    @GetMapping
    public String placeholder() {
        // Implement this latter, the main take is to have a flag "calculationInProgress"
        // when is set to true user can't change solution bc it will get overwritten
        return "Placeholder";
    }
}
