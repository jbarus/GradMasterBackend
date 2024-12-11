package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.exceptions.MalformedRequestException;
import com.github.jbarus.gradmasterbackend.exceptions.calculationstart.NoSuchContextException;
import com.github.jbarus.gradmasterbackend.mappers.ProblemContextMapper;
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
        if(name == null || name.isEmpty() || date == null) {
            throw new MalformedRequestException("Name or Date not found for the given UUID");
        }
        UUID uuid = UUID.randomUUID();
        ProblemContext problemContext = ProblemContext.getOrCreateInstance(uuid);
        problemContext.setName(name);
        problemContext.setDate(date);

        return new ContextDisplayInfoDTO(uuid, name, date);
    }

    public ContextDisplayInfoDTO getContextDisplayInfo(UUID uuid) {
        ProblemContext problemContext = ProblemContext.getInstance(uuid);
        if (problemContext == null) {
            throw new NoSuchContextException("ProblemContext not found for the given UUID");
        }
        return new ContextDisplayInfoDTO(problemContext.getUuid(), problemContext.getName(), problemContext.getDate());
    }

    public List<ContextDisplayInfoDTO> getAllContextDisplayInfos() {
        return ProblemContext.getAvailableContexts().stream()
                .map(context -> new ContextDisplayInfoDTO(context.getUuid(), context.getName(), context.getDate()))
                .collect(Collectors.toList());
    }

    public ContextDisplayInfoDTO updateContextDisplayInfo(UUID uuid, String name, LocalDate date) {
        ProblemContext problemContext = ProblemContext.getInstance(uuid);
        if (problemContext == null) {
            throw new NoSuchContextException("ProblemContext not found for the given UUID");
        }
        if(name == null || name.isEmpty() || date == null) {
            throw new MalformedRequestException("Name or Date not found for the given UUID");
        }
        problemContext.setName(name);
        problemContext.setDate(date);
        return new ContextDisplayInfoDTO(problemContext.getUuid(), name, date);
    }

    public ContextDisplayInfoDTO deleteContextDisplayInfo(UUID uuid) {
        ProblemContext problemContext = ProblemContext.deleteContext(uuid);
        if(problemContext == null) {
            throw new NoSuchContextException("ProblemContext not found for the given UUID");
        }
        return ProblemContextMapper.problemContextToContextDisplayInfoDTOConverter(problemContext);
    }
}
