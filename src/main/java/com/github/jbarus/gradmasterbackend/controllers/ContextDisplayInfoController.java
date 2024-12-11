package com.github.jbarus.gradmasterbackend.controllers;

import com.github.jbarus.gradmasterbackend.exceptions.MalformedRequestException;
import com.github.jbarus.gradmasterbackend.exceptions.calculationstart.NoSuchContextException;
import com.github.jbarus.gradmasterbackend.models.ContextDisplayInfo;
import com.github.jbarus.gradmasterbackend.models.dto.ContextDisplayInfoDTO;
import com.github.jbarus.gradmasterbackend.services.ContextDisplayInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ContextDisplayInfoDTO> createContextDisplayInfo(@RequestBody ContextDisplayInfo contextDisplayInfo) {
        try{
            ContextDisplayInfoDTO createdContext = contextDisplayInfoService.createContextDisplayInfo(contextDisplayInfo.getName(), contextDisplayInfo.getDate());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdContext);
        } catch (MalformedRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ContextDisplayInfoDTO> getContextDisplayInfo(@PathVariable UUID uuid) {
        try {
            ContextDisplayInfoDTO context = contextDisplayInfoService.getContextDisplayInfo(uuid);
            return ResponseEntity.ok(context);
        } catch (NoSuchContextException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<ContextDisplayInfoDTO>> getAllContextDisplayInfos() {
        try{
            List<ContextDisplayInfoDTO> contextList = contextDisplayInfoService.getAllContextDisplayInfos();
            return ResponseEntity.ok(contextList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<ContextDisplayInfoDTO> updateContextDisplayInfo(
            @PathVariable UUID uuid,
            @RequestBody ContextDisplayInfo contextDisplayInfo
    ) {
        try {
            ContextDisplayInfoDTO updatedContext = contextDisplayInfoService.updateContextDisplayInfo(uuid, contextDisplayInfo.getName(), contextDisplayInfo.getDate());
            return ResponseEntity.ok(updatedContext);
        } catch (NoSuchContextException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (MalformedRequestException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ContextDisplayInfoDTO> deleteContextDisplayInfo(@PathVariable UUID uuid) {
        try{
            ContextDisplayInfoDTO deletedContext = contextDisplayInfoService.deleteContextDisplayInfo(uuid);
            return ResponseEntity.ok(deletedContext);
        }catch (NoSuchContextException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }
}
