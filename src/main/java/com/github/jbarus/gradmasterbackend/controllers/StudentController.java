package com.github.jbarus.gradmasterbackend.controllers;

import com.github.jbarus.gradmasterbackend.exceptions.InvalidInputException;
import com.github.jbarus.gradmasterbackend.exceptions.UninitializedContextException;
import com.github.jbarus.gradmasterbackend.exceptions.BusinessLogicException;
import com.github.jbarus.gradmasterbackend.models.Student;
import com.github.jbarus.gradmasterbackend.models.communication.Response;
import com.github.jbarus.gradmasterbackend.models.communication.UploadStatus;
import com.github.jbarus.gradmasterbackend.models.dto.StudentDTO;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;
import com.github.jbarus.gradmasterbackend.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> uploadStudentFile(@RequestParam("students") MultipartFile file, @PathVariable UUID id) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(new Response<>(UploadStatus.INVALID_INPUT));
        }
        try {
            StudentDTO result = studentService.handleStudentFile(file, id);
            return ResponseEntity.ok(new Response<>(UploadStatus.SUCCESS, result));
        } catch (InvalidInputException e) {
            return ResponseEntity.badRequest().body(new Response<>(UploadStatus.INVALID_INPUT));
        } catch (UninitializedContextException e) {
            return ResponseEntity.badRequest().body(new Response<>(UploadStatus.UNINITIALIZED_CONTEXT));
        } catch (BusinessLogicException e) {
            return ResponseEntity.badRequest().body(new Response<>(e.getStatus()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentsByContext(@PathVariable UUID id) {
        try {
            List<Student> students = studentService.getStudentsByContext(id);
            return ResponseEntity.ok(students);
        } catch (UninitializedContextException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudentsByContext(@PathVariable UUID id, @RequestBody List<Student> students) {
        try {
            List<Student> updatedStudents = studentService.updateStudentsByContext(id, students);
            return ResponseEntity.ok(updatedStudents);
        } catch (UninitializedContextException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
