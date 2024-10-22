package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.mappers.ProblemContextMapper;
import com.github.jbarus.gradmasterbackend.models.dto.ContextDisplayInfoDTO;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProblemContextService {
    public List<ContextDisplayInfoDTO> getAllContexts() {
        return ProblemContext.getAvailableContexts().stream()
                .map(ProblemContextMapper::problemContextToContextDisplayInfoDTOConverter)
                .collect(Collectors.toList());
    }

    public ResponseEntity<ContextDisplayInfoDTO> getContextById(UUID contextId) {
        ProblemContext problemContext = ProblemContext.getInstance(contextId);
        if (problemContext == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(ProblemContextMapper.problemContextToContextDisplayInfoDTOConverter(problemContext));
    }

    public ResponseEntity<ContextDisplayInfoDTO> modifyContextById(UUID contextId, ContextDisplayInfoDTO contextDisplayInfoDTO) {
        ProblemContext problemContext = ProblemContext.getInstance(contextId);
        if (problemContext == null) {
            return ResponseEntity.notFound().build();
        }
        problemContext.setName(contextDisplayInfoDTO.getName());
        problemContext.setDate(contextDisplayInfoDTO.getDate());

        return ResponseEntity.ok(ProblemContextMapper.problemContextToContextDisplayInfoDTOConverter(problemContext));
    }

    public ResponseEntity<Void> deleteContextById(UUID contextId) {
        ProblemContext problemContext = ProblemContext.deleteContext(contextId);
        if (problemContext == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
