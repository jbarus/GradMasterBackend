package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.context.Context;
import com.github.jbarus.gradmasterbackend.models.dto.ContextDTO;
import com.github.jbarus.gradmasterbackend.utils.converters.ContextDTOConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ContextService {
    public List<ContextDTO> getAllContexts() {
        List<ContextDTO> contexts = new ArrayList<>();

        for (Context context : Context.getAvailableContexts()){
            contexts.add(ContextDTOConverter.convertToDTO(context));
        }
        return contexts;
    }

    public ContextDTO getContextById(UUID id) {
        return ContextDTOConverter.convertToDTO(Context.getInstance(id));
    }

    public ContextDTO modifyContextRepresentation (ContextDTO contextDTO) {
        Context context = Context.getInstance(contextDTO.getId());
        if(context != null) {
            context.setName(contextDTO.getName());
            return contextDTO;
        }
        return null;
    }
    
    public ContextDTO deleteContext(ContextDTO contextDTO) {
        return ContextDTOConverter.convertToDTO(Context.deleteContext(contextDTO.getId()));
    }
}
