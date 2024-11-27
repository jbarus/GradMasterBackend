package com.github.jbarus.gradmasterbackend.services;

import com.github.jbarus.gradmasterbackend.exceptions.calculationstart.MissingDataException;
import com.github.jbarus.gradmasterbackend.mappers.ProblemContextMapper;
import com.github.jbarus.gradmasterbackend.models.communication.CalculationStartStatus;
import com.github.jbarus.gradmasterbackend.models.dto.ProblemDTO;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;
import com.github.jbarus.gradmasterbackend.rabbitmq.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProblemService {
    private final RabbitTemplate rabbitTemplate;

    public ProblemService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public ProblemDTO publishProblem(UUID contextId) {
        ProblemContext problemContext = getValidContext(contextId);

        validateProblemContext(problemContext);

        ProblemDTO problemDTO = ProblemContextMapper.problemContextToProblemDTOConverter(problemContext);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.PROBLEM_ROUTING_KEY, problemDTO);
        problemContext.setCalculationInProgress(true);
        return problemDTO;
    }

    private void validateProblemContext(ProblemContext problemContext) {
        if (problemContext.getUniversityEmployees() == null || problemContext.getUniversityEmployees().isEmpty()) {
            throw new MissingDataException(CalculationStartStatus.MISSING_OR_INCOMPLETE_UNIVERSITY_EMPLOYEE);
        }

        if (problemContext.getStudents() == null || problemContext.getStudents().isEmpty()) {
            throw new MissingDataException(CalculationStartStatus.MISSING_OR_INCOMPLETE_STUDENT);
        }

        if(problemContext.getStudentReviewerMapping() == null || problemContext.getStudentReviewerMapping().isEmpty()) {
            throw new MissingDataException(CalculationStartStatus.MISSING_STUDENT_REVIEWER_MAPPING);
        }

        if (problemContext.getProblemParameters() == null) {
            throw new MissingDataException(CalculationStartStatus.MISSING_OR_INCOMPLETE_PROBLEM_PARAMETER);
        }

        if(problemContext.getUniversityEmployees().size() % problemContext.getProblemParameters().getCommitteeSize() != 0) {
            throw new MissingDataException(CalculationStartStatus.INVALID_UNIVERSITY_EMPLOYEE_NUMBER);
        }


    }

    private ProblemContext getValidContext(UUID contextId) {
        ProblemContext context = ProblemContext.getInstance(contextId);
        if (context == null) {
            throw new IllegalArgumentException("Invalid context ID: " + contextId);
        }
        return context;
    }
}
