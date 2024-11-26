package com.github.jbarus.gradmasterbackend.rabbitmq;

import com.github.jbarus.gradmasterbackend.models.dto.SolutionDTO;
import com.github.jbarus.gradmasterbackend.models.problem.ProblemContext;
import com.github.jbarus.gradmasterbackend.models.problem.Solution;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SolutionListener {
    @RabbitListener(queues = RabbitMQConfig.SOLUTION_QUEUE_NAME)
    public void receiveMessage(SolutionDTO solutionDTO) {
        System.out.println("Received SolutionDTO: " + solutionDTO);
        System.out.println(solutionDTO.getCommittees());
        System.out.println(solutionDTO.getUnassignedStudents());
        ProblemContext problemContext = ProblemContext.getInstance(solutionDTO.getId());
        Solution solution = new Solution();
        solution.setCommittees(solutionDTO.getCommittees());
        solution.setUnassignedStudents(solutionDTO.getUnassignedStudents());
        problemContext.setSolution(solution);
        problemContext.setInProgress(false);
    }
}
