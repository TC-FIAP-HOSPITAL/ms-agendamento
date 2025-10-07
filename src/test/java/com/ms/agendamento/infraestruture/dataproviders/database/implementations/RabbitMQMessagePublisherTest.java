//package com.ms.agendamento.infraestruture.dataproviders.database.implementations;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import com.ms.agendamento.infraestruture.dataproviders.messaging.publisher.RabbitMQMessagePublisher;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.MessageProperties;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.MessageConverter;
//
//import com.ms.agendamento.infraestruture.dataproviders.config.rabbitmq.RabbitMQConfig;
//
//@ExtendWith(MockitoExtension.class)
//class RabbitMQMessagePublisherTest {
//
//    @Mock
//    private RabbitTemplate rabbitTemplate;
//
//    @Mock
//    private MessageConverter messageConverter;
//
//    @InjectMocks
//    private RabbitMQMessagePublisher publisher;
//
//    @Test
//    void publish_shouldAddAuthorizationHeaderAndSendMessage() {
//        Object payload = new Object();
//        Message message = new Message(new byte[0], new MessageProperties());
//
//        when(rabbitTemplate.getMessageConverter()).thenReturn(messageConverter);
//        when(messageConverter.toMessage(any(), any(MessageProperties.class))).thenAnswer(invocation -> {
//            Object body = invocation.getArgument(0);
//            MessageProperties props = invocation.getArgument(1);
//            return new Message(body.toString().getBytes(), props);
//        });
//
//        publisher.publish(payload, "jwt-token");
//
//        ArgumentCaptor<MessageProperties> propertiesCaptor = ArgumentCaptor.forClass(MessageProperties.class);
////        verify(messageConverter).toMessage(any(), propertiesCaptor.capture());
//
//        MessageProperties capturedProps = propertiesCaptor.getValue();
//        assertThat(capturedProps.getHeaders()).containsEntry("Authorization", "Bearer jwt-token");
//
//        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
//        verify(rabbitTemplate).send(
//                eq(RabbitMQConfig.EXCHANGE_NAME),
//                eq(RabbitMQConfig.ROUTING_KEY),
//                messageCaptor.capture());
//
//        assertThat(messageCaptor.getValue().getMessageProperties().getHeaders())
//                .containsEntry("Authorization", "Bearer jwt-token");
//    }
//}
