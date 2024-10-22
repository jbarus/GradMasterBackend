package com.github.jbarus.gradmasterbackend.controllers;

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

    @PostMapping(path = "/{id}")
    public ResponseEntity<Response<UploadStatus, StudentDTO>> uploadStudentFile(@RequestParam("students") MultipartFile file, @PathVariable UUID id) {
        if(file.isEmpty()) {
            return ResponseEntity.badRequest().body(new Response<>(UploadStatus.INVALID_INPUT));
        }

        ProblemContext problemContext = ProblemContext.getInstance(id);

        if(problemContext == null) {
            return ResponseEntity.badRequest().body(new Response<>(UploadStatus.UNINITIALIZED_CONTEXT));
        }

        return studentService.handleStudentFile(file, id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Student>> getStudentsByContext(@PathVariable UUID id) {
        return studentService.getStudentsByContext(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<List<Student>> updateStudetnsByContext(@PathVariable UUID id, @RequestBody List<Student> students) {
        return studentService.updateStudentsByContext(id, students);
    }
}
