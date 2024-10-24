package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.mappers.ProblemContextMapper;
import com.github.jbarus.gradmasterbackend.models.communication.CalculationStartStatus;
import com.github.jbarus.gradmasterbackend.models.communication.Response;
import com.github.jbarus.gradmasterbackend.models.dto.ProblemDTO;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;
import com.github.jbarus.gradmasterbackend.rabbitmq.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProblemService {

    private final RabbitTemplate rabbitTemplate;

    public ProblemService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public ResponseEntity<Response<CalculationStartStatus, ProblemDTO>> publishProblem(UUID contextId) {
        ProblemContext problemContext = ProblemContext.getInstance(contextId);
        if (problemContext == null) {
            return ResponseEntity.badRequest().body(new Response<>(CalculationStartStatus.NO_SUCH_CONTEXT, null));
        }

        if(problemContext.getUniversityEmployees() == null || problemContext.getUniversityEmployees().isEmpty()) {
            return ResponseEntity.badRequest().body(new Response<>(CalculationStartStatus.MISSING_OR_INCOMPLETE_UNIVERSITY_EMPLOYEE, null));
        }

        if (problemContext.getStudents() == null || problemContext.getStudents().isEmpty()) {
            return ResponseEntity.badRequest().body(new Response<>(CalculationStartStatus.MISSING_OR_INCOMPLETE_STUDENT, null));
        }

        if(problemContext.getProblemParameters() == null) {
            return ResponseEntity.badRequest().body(new Response<>(CalculationStartStatus.MISSING_OR_INCOMPLETE_PROBLEM_PARAMETER, null));
        }

        ProblemDTO problemDTO = ProblemContextMapper.problemContextToProblemDTOConverter(problemContext);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, problemDTO);
        return ResponseEntity.badRequest().body(new Response<>(CalculationStartStatus.SUCCESS, problemDTO));
    }
}
