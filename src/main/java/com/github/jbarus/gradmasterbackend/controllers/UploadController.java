package com.github.jbarus.gradmasterbackend.controllers;

import com.github.jbarus.gradmasterbackend.models.Student;
import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
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
    public ResponseEntity<HashMap<LocalDate, List<UniversityEmployee>>> handleUniversityEmployeeFile(@RequestParam("universityEmployees") MultipartFile file) {
        if(file.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        HashMap<LocalDate, List<UniversityEmployee>> universityEmployees;
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

    @PostMapping(path = "/student")
    public ResponseEntity<HashMap<LocalDate, List<Student>>> handleStudentFile(@RequestParam("students") MultipartFile file) {
        if(file.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        HashMap<LocalDate, List<Student>> universityEmployees;
        try{
            universityEmployees = studentService.prepareStudents(file);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }

        if(universityEmployees == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(universityEmployees);
    }

}
