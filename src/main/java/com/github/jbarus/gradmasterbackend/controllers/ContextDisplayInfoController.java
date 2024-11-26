package com.github.jbarus.gradmasterbackend.controllers;

import com.github.jbarus.gradmasterbackend.models.dto.ContextDisplayInfoDTO;
import com.github.jbarus.gradmasterbackend.services.ContextDisplayInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/context-display-infos")
public class ContextDisplayInfoController {
    private final ContextDisplayInfoService contextDisplayInfoService;

    public ContextDisplayInfoController(ContextDisplayInfoService contextDisplayInfoService) {
        this.contextDisplayInfoService = contextDisplayInfoService;
    }

    @PostMapping
    public ResponseEntity<ContextDisplayInfoDTO> createContextDisplayInfo(@RequestBody ContextDisplayInfoDTO requestDto) {
        ContextDisplayInfoDTO contextDisplayInfoDTO = contextDisplayInfoService.createContextDisplayInfo(requestDto.getName(), requestDto.getDate());
        return new ResponseEntity<>(contextDisplayInfoDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ContextDisplayInfoDTO> getContextDisplayInfo(@PathVariable UUID uuid) {
        ContextDisplayInfoDTO contextDisplayInfoDTO = contextDisplayInfoService.getContextDisplayInfo(uuid);
        return contextDisplayInfoDTO != null ? new ResponseEntity<>(contextDisplayInfoDTO, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<ContextDisplayInfoDTO>> getAllContextDisplayInfos() {
        List<ContextDisplayInfoDTO> contextDisplayInfos = contextDisplayInfoService.getAllContextDisplayInfos();
        return new ResponseEntity<>(contextDisplayInfos, HttpStatus.OK);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ContextDisplayInfoDTO> updateContextDisplayInfo(@PathVariable UUID uuid, @RequestBody ContextDisplayInfoDTO requestDto) {
        ContextDisplayInfoDTO updatedContextDisplayInfo = contextDisplayInfoService.updateContextDisplayInfo(uuid, requestDto.getName(), requestDto.getDate());
        return updatedContextDisplayInfo != null ? new ResponseEntity<>(updatedContextDisplayInfo, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteContextDisplayInfo(@PathVariable UUID uuid) {
        boolean deleted = contextDisplayInfoService.deleteContextDisplayInfo(uuid);
        return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
