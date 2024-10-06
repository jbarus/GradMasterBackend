package com.github.jbarus.gradmasterbackend.controllers;

import com.github.jbarus.gradmasterbackend.context.Context;
import com.github.jbarus.gradmasterbackend.models.communication.UploadResponse;
import com.github.jbarus.gradmasterbackend.models.communication.UploadResult;
import com.github.jbarus.gradmasterbackend.services.StudentService;
import com.github.jbarus.gradmasterbackend.services.UniversityEmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<UploadResponse<List<UUID>>> handleUniversityEmployeeFile(@RequestParam("universityEmployees") MultipartFile file) {
        if(file.isEmpty()) {
            return ResponseEntity.badRequest().body(new UploadResponse<>(UploadResult.INVALID_INPUT));
        }

        return universityEmployeeService.prepareUniversityEmployees(file);
    }

    @PostMapping(path = "/student")
    public ResponseEntity<UploadResponse<List<UUID>>> handleStudentFile(@RequestParam("students") MultipartFile file) {
        if(file.isEmpty()) {
            return ResponseEntity.badRequest().body(new UploadResponse<>(UploadResult.INVALID_INPUT));
        }
        if(Context.getNumberOfAvailableContexts() == 0){
            return ResponseEntity.badRequest().body(new UploadResponse<>(UploadResult.UNINITIALIZED_CONTEXT));
        }

        return studentService.prepareStudents(file);
    }



}
