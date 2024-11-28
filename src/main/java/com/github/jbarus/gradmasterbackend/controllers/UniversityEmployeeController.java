package com.github.jbarus.gradmasterbackend.controllers;

import com.github.jbarus.gradmasterbackend.exceptions.BusinessLogicException;
import com.github.jbarus.gradmasterbackend.exceptions.InvalidInputException;
import com.github.jbarus.gradmasterbackend.exceptions.UninitializedContextException;
import com.github.jbarus.gradmasterbackend.models.UniversityEmployee;
import com.github.jbarus.gradmasterbackend.models.communication.Response;
import com.github.jbarus.gradmasterbackend.models.communication.UploadStatus;
import com.github.jbarus.gradmasterbackend.models.dto.UniversityEmployeeDTO;
import com.github.jbarus.gradmasterbackend.services.UniversityEmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/university-employees")
public class UniversityEmployeeController {
    private final UniversityEmployeeService universityEmployeeService;

    public UniversityEmployeeController(UniversityEmployeeService universityEmployeeService) {
        this.universityEmployeeService = universityEmployeeService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> uploadUniversityEmployeesFileByContextId(@RequestParam("universityEmployees") MultipartFile file, @PathVariable UUID id) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(new Response<>(UploadStatus.INVALID_INPUT));
        }

        try {
            UniversityEmployeeDTO result = universityEmployeeService.handleUniversityEmployeeFile(file, id);
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
    public ResponseEntity<?> getUniversityEmployeeByContextId(@PathVariable UUID id) {
        try {
            UniversityEmployeeDTO result = universityEmployeeService.getUniversityEmployeeByContext(id);
            return ResponseEntity.ok(result);
        } catch (UninitializedContextException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUniversityEmployeeByContextId(@PathVariable UUID id, @RequestBody UniversityEmployeeDTO employeeDTO) {
        try {
            UniversityEmployeeDTO updatedResult = universityEmployeeService.updateUniversityEmployeeByContext(id, employeeDTO);
            return ResponseEntity.ok(updatedResult);
        } catch (UninitializedContextException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
