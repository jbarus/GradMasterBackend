package com.github.jbarus.gradmasterbackend.controllers;

import com.github.jbarus.gradmasterbackend.context.Context;
import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/university-employees")
public class UniversityEmployeeController {

    @GetMapping(path = "/{id}")
    public ResponseEntity<List<UniversityEmployee>> getListOfUniversityEmployeesForContext(@PathVariable("id") UUID id) {
        Context context = Context.getInstance(id);
        if(context == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(context.getUniversityEmployeeList());
    }
}
