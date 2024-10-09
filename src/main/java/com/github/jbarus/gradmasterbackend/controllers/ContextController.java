package com.github.jbarus.gradmasterbackend.controllers;

import com.github.jbarus.gradmasterbackend.models.dto.ContextDTO;

import com.github.jbarus.gradmasterbackend.services.ContextService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contexts")
public class ContextController {

    private final ContextService contextService;

    public ContextController(ContextService contextService) {
        this.contextService = contextService;
    }

    @GetMapping
    public ResponseEntity<List<ContextDTO>> getAllContexts() {
        return ResponseEntity.ok().body(contextService.getAllContexts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContextDTO> getContextById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(contextService.getContextById(id));
    }
}
