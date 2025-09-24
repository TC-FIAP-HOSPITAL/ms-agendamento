package com.ms.agendamento.infraestruture.dataproviders.database.implementations;

import com.ms.agendamento.application.gateways.MessagePublisher;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQMessagePublisher implements MessagePublisher {

    private final AmqpTemplate amqpTemplate;

    public RabbitMQMessagePublisher(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public void publish(Object message) {
        amqpTemplate.convertAndSend(
                "agendamento-exchange",
                "agendamento-routingKey",
                message
        );
    }
}
