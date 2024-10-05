package com.github.jbarus.gradmasterbackend.controllers;

import com.github.jbarus.gradmasterbackend.context.Context;
import com.github.jbarus.gradmasterbackend.models.Student;
import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import com.github.jbarus.gradmasterbackend.models.communication.UploadResponse;
import com.github.jbarus.gradmasterbackend.services.StudentService;
import com.github.jbarus.gradmasterbackend.services.UniversityEmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/upload")
public class UploadController {
    private final UniversityEmployeeService universityEmployeeService;
    private final StudentService studentService;

    public UploadController(UniversityEmployeeService universityEmployeeService, StudentService studentService) {
        this.universityEmployeeService = universityEmployeeService;
        this.studentService = studentService;
    }

    @PostMapping(path = "/university-employee")
    public ResponseEntity<UploadResponse> handleUniversityEmployeeFile(@RequestParam("universityEmployees") MultipartFile file) {
        if(file.isEmpty()) {
            return ResponseEntity.badRequest().body(UploadResponse.INVALID_INPUT);
        }

        return universityEmployeeService.prepareUniversityEmployees(file);
    }

    @PostMapping(path = "/student")
    public ResponseEntity<UploadResponse> handleStudentFile(@RequestParam("students") MultipartFile file) {
        if(file.isEmpty()) {
            return ResponseEntity.badRequest().body(UploadResponse.INVALID_INPUT);
        }
        if(Context.getNumberOfAvailableContexts() == 0){
            return ResponseEntity.badRequest().body(UploadResponse.UNINITIALIZED_CONTEXT);
        }

        return studentService.prepareStudents(file);
    }

}
