package com.github.jbarus.gradmasterbackend.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String PROBLEM_QUEUE_NAME = "problem";
    public static final String SOLUTION_QUEUE_NAME = "solution";
    public static final String EXCHANGE_NAME = "gradmaster";
    public static final String PROBLEM_ROUTING_KEY = EXCHANGE_NAME + "." + PROBLEM_QUEUE_NAME;
    public static final String SOLUTION_ROUTING_KEY = EXCHANGE_NAME + "." + SOLUTION_QUEUE_NAME;

    @Bean
    public Queue problemQueue() {
        return new Queue(PROBLEM_QUEUE_NAME, true);
    }

    @Bean
    public Queue solutionQueue() {
        return new Queue(SOLUTION_QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange myExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding problemBinding(Queue problemQueue, TopicExchange myExchange) {
        return BindingBuilder.bind(problemQueue).to(myExchange).with(PROBLEM_ROUTING_KEY);
    }
    @Bean
    public Binding solutionBinding(Queue solutionQueue, TopicExchange myExchange) {
        return BindingBuilder.bind(solutionQueue).to(myExchange).with(SOLUTION_ROUTING_KEY);
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }

}
