package com.ms.agendamento.infraestruture.dataproviders.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "agendamento-exchange";
    public static final String ROUTING_KEY = "agendamento-routing-key";

    public static final String NOTIFICACAO_QUEUE_NAME = "notificacao-queue";
    public static final String HISTORICO_QUEUE_NAME = "historico-queue";


    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue notificacaoQueue() {
        return new Queue(NOTIFICACAO_QUEUE_NAME, true);
    }

    @Bean
    public Queue historicoQueue() {
        return new Queue(HISTORICO_QUEUE_NAME, true);
    }

    @Bean
    public Binding notificacaoBinding(Queue notificacaoQueue, DirectExchange exchange) {
        return BindingBuilder.bind(notificacaoQueue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public Binding historicoBinding(Queue historicoQueue, DirectExchange exchange) {
        return BindingBuilder.bind(historicoQueue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
