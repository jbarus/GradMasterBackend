package com.github.jbarus.gradmasterbackend.rabbitmq;

import com.github.jbarus.gradmasterbackend.models.dto.CoreSolutionDTO;
import com.github.jbarus.gradmasterbackend.models.dto.SolutionDTO;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;
import com.github.jbarus.gradmasterbackend.models.problem.Solution;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SolutionListener {
    @RabbitListener(queues = RabbitMQConfig.SOLUTION_QUEUE_NAME)
    public void receiveMessage(CoreSolutionDTO coreSolutionDTO) {
        System.out.println("CoreSolutionDTO: " + coreSolutionDTO);
        ProblemContext problemContext = ProblemContext.getInstance(coreSolutionDTO.getId());
        Solution solution = new Solution();
        solution.setCommittees(coreSolutionDTO.getCommittees());
        solution.setUnassignedStudents(coreSolutionDTO.getUnassignedStudents());
        solution.setUnassignedUniversityEmployees(new ArrayList<>());
        problemContext.setSolution(solution);
        problemContext.setInProgress(false);
    }
}
