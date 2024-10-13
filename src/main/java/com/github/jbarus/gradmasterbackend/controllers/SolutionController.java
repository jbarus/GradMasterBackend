package com.github.jbarus.gradmasterbackend.controllers;

import com.github.jbarus.gradmasterbackend.config.RabbitMQConfig;
import com.github.jbarus.gradmasterbackend.context.Context;
import com.github.jbarus.gradmasterbackend.models.communication.UploadResponse;
import com.github.jbarus.gradmasterbackend.models.communication.UploadResult;
import com.github.jbarus.gradmasterbackend.models.dto.ContextDataDTO;
import com.github.jbarus.gradmasterbackend.utils.converters.ContextDataDTOConverter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/solutions")
public class SolutionController {
    private final RabbitTemplate rabbitTemplate;

    public SolutionController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping()
    public ResponseEntity<String> getSolutions() {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,RabbitMQConfig.ROUTING_KEY,"Hello World");
        return ResponseEntity.ok("Hello World");
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity<UploadResponse<ContextDataDTO>> postSolutions(@PathVariable UUID id) {
        Context context = Context.getInstance(id);
        if(context == null) {
            return ResponseEntity.badRequest().body(new UploadResponse<>(UploadResult.INVALID_INPUT,null));
        }
        ContextDataDTO contextDataDTO = ContextDataDTOConverter.convertToDTO(context);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,RabbitMQConfig.ROUTING_KEY,contextDataDTO);
        return ResponseEntity.ok(new UploadResponse<>(UploadResult.SUCCESS,ContextDataDTOConverter.convertToDTO(context)));
    }
}
