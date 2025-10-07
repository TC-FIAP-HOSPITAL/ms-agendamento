package com.ms.agendamento.infraestruture.dataproviders.messaging.publisher;

import com.ms.agendamento.application.gateways.MessagePublisher;
import com.ms.agendamento.infraestruture.dataproviders.config.rabbitmq.RabbitMQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQMessagePublisher implements MessagePublisher {

    private final AmqpTemplate amqpTemplate;

    public RabbitMQMessagePublisher(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public void publish(Object payload, String jwtToken) {
        amqpTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                payload,
                message -> {
                    message.getMessageProperties().setHeader("Authorization", "Bearer " + jwtToken);
                    return message;
                }
        );
    }
}
