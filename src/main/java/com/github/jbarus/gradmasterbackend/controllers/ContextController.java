package com.github.jbarus.gradmasterbackend.controllers;

import com.github.jbarus.gradmasterbackend.context.Context;
import com.github.jbarus.gradmasterbackend.models.dto.ContextDataDTO;
import com.github.jbarus.gradmasterbackend.models.dto.ContextOverviewDTO;

import com.github.jbarus.gradmasterbackend.services.ContextService;
import com.github.jbarus.gradmasterbackend.utils.converters.ContextDataDTOConverter;
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
    public ResponseEntity<List<ContextOverviewDTO>> getAllContexts() {
        return ResponseEntity.ok().body(contextService.getAllContexts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContextOverviewDTO> getContextById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(contextService.getContextById(id));
    }

    @GetMapping("/full/{id}")
    public ResponseEntity<ContextDataDTO> getContextByFullId(@PathVariable UUID id) {
        Context context = Context.getInstance(id);
        return ResponseEntity.ok().body(ContextDataDTOConverter.convertToDTO(context));
    }
}
