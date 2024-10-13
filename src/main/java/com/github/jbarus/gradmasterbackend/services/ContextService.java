package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.context.Context;
import com.github.jbarus.gradmasterbackend.models.dto.ContextOverviewDTO;
import com.github.jbarus.gradmasterbackend.utils.converters.ContextDTOConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ContextService {
    public List<ContextOverviewDTO> getAllContexts() {
        List<ContextOverviewDTO> contexts = new ArrayList<>();

        for (Context context : Context.getAvailableContexts()){
            contexts.add(ContextDTOConverter.convertToDTO(context));
        }
        return contexts;
    }

    public ContextOverviewDTO getContextById(UUID id) {
        return ContextDTOConverter.convertToDTO(Context.getInstance(id));
    }

    public ContextOverviewDTO modifyContextRepresentation (ContextOverviewDTO contextOverviewDTO) {
        Context context = Context.getInstance(contextOverviewDTO.getId());
        if(context != null) {
            context.setName(contextOverviewDTO.getName());
            return contextOverviewDTO;
        }
        return null;
    }
    
    public ContextOverviewDTO deleteContext(ContextOverviewDTO contextOverviewDTO) {
        return ContextDTOConverter.convertToDTO(Context.deleteContext(contextOverviewDTO.getId()));
    }
}
