package com.github.jbarus.gradmasterbackend.controllers;

import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import com.github.jbarus.gradmasterbackend.services.UniversityEmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/upload")
public class UploadController {
    private final UniversityEmployeeService universityEmployeeService;

    public UploadController(UniversityEmployeeService universityEmployeeService) {
        this.universityEmployeeService = universityEmployeeService;
    }

    @PostMapping(path = "/university-employee")
    public ResponseEntity<List<UniversityEmployee>> handleUniversityEmployeeFile(@RequestParam("universityEmployees") MultipartFile file) {
        if(file.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        List<UniversityEmployee> universityEmployees = null;
        try{
            universityEmployees = universityEmployeeService.prepareUniversityEmployees(file);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }

        if(universityEmployees == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(universityEmployees);
    }

}
