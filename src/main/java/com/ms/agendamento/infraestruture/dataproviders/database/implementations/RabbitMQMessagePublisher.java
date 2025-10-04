package com.ms.agendamento.infraestruture.dataproviders.database.implementations;

import com.ms.agendamento.application.gateways.MessagePublisher;
import com.ms.agendamento.infraestruture.dataproviders.config.rabbitmq.RabbitMQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQMessagePublisher implements MessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQMessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(Object payload, String jwtToken) {
        MessageProperties props = new MessageProperties();
        props.setHeader("Authorization", "Bearer " + jwtToken);

        Message message = rabbitTemplate.getMessageConverter().toMessage(payload, props);
        rabbitTemplate.send(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, message);
    }
}
