package com.github.jbarus.gradmasterbackend.controllers;

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

    @PostMapping
    public ResponseEntity<Response<UploadStatus, UniversityEmployeeDTO>> uploadUniversityEmployeesFile(@RequestParam("universityEmployees") MultipartFile file) {
        if(file.isEmpty()) {
            return ResponseEntity.badRequest().body(new Response<>(UploadStatus.INVALID_INPUT, null));
        }

        return universityEmployeeService.handleUniversityEmployeeFile(file);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<UniversityEmployee>> getUniversityEmployeeByContext(@PathVariable UUID id) {
        return universityEmployeeService.getUniversityEmployeeByContext(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<List<UniversityEmployee>> updateUniversityEmployeeByContext(@PathVariable UUID id, @RequestBody List<UniversityEmployee> universityEmployees) {
        return universityEmployeeService.updateUniversityEmployeeByContext(id, universityEmployees);
    }
}
