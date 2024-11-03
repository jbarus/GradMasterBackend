package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.models.dto.ContextDisplayInfoDTO;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContextDisplayInfoService {

    public ContextDisplayInfoDTO createContextDisplayInfo(String name, LocalDate date) {
        UUID uuid = UUID.randomUUID();
        ProblemContext problemContext = ProblemContext.getOrCreateInstance(uuid);
        problemContext.setName(name);
        problemContext.setDate(date);

        System.out.println(problemContext);

        return new ContextDisplayInfoDTO(uuid, name, date);
    }

    public ContextDisplayInfoDTO getContextDisplayInfo(UUID uuid) {
        ProblemContext problemContext = ProblemContext.getInstance(uuid);
        if (problemContext != null) {
            return new ContextDisplayInfoDTO(problemContext.getUuid(), problemContext.getName(), problemContext.getDate());
        }
        return null;
    }

    public List<ContextDisplayInfoDTO> getAllContextDisplayInfos() {
        return ProblemContext.getAvailableContexts().stream()
                .map(context -> new ContextDisplayInfoDTO(context.getUuid(), context.getName(), context.getDate()))
                .collect(Collectors.toList());
    }

    public ContextDisplayInfoDTO updateContextDisplayInfo(UUID uuid, String name, LocalDate date) {
        ProblemContext problemContext = ProblemContext.getInstance(uuid);
        if (problemContext != null) {
            problemContext.setName(name);
            problemContext.setDate(date);
            return new ContextDisplayInfoDTO(problemContext.getUuid(), name, date);
        }
        return null;
    }

    public boolean deleteContextDisplayInfo(UUID uuid) {
        return ProblemContext.deleteContext(uuid) != null;
    }
}
