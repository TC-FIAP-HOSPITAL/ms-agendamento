package com.ms.agendamento.infraestruture.dataproviders.config.rabbitmq;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

@ExtendWith(MockitoExtension.class)
class RabbitMQConfigTest {

    private final RabbitMQConfig config = new RabbitMQConfig();

    @Test
    void exchange_shouldExposeConfiguredName() {
        DirectExchange exchange = config.exchange();

        assertThat(exchange.getName()).isEqualTo(RabbitMQConfig.EXCHANGE_NAME);
        assertThat(exchange.isDurable()).isTrue();
    }

    @Test
    void queues_shouldBeDurableAndNamed() {
        Queue notificacao = config.notificacaoQueue();
        Queue historico = config.historicoQueue();

        assertThat(notificacao.getName()).isEqualTo(RabbitMQConfig.NOTIFICACAO_QUEUE_NAME);
        assertThat(notificacao.isDurable()).isTrue();
        assertThat(historico.getName()).isEqualTo(RabbitMQConfig.HISTORICO_QUEUE_NAME);
        assertThat(historico.isDurable()).isTrue();
    }

    @Test
    void bindings_shouldLinkQueuesToExchangeWithRoutingKey() {
        Queue notificacao = config.notificacaoQueue();
        Queue historico = config.historicoQueue();
        DirectExchange exchange = config.exchange();

        Binding notificacaoBinding = config.notificacaoBinding(notificacao, exchange);
        Binding historicoBinding = config.historicoBinding(historico, exchange);

        assertThat(notificacaoBinding.getRoutingKey()).isEqualTo(RabbitMQConfig.ROUTING_KEY);
        assertThat(historicoBinding.getRoutingKey()).isEqualTo(RabbitMQConfig.ROUTING_KEY);
        assertThat(notificacaoBinding.getExchange()).isEqualTo(exchange.getName());
        assertThat(historicoBinding.getExchange()).isEqualTo(exchange.getName());
    }

    @Test
    void messageConverter_shouldReturnJacksonConverter() {
        assertThat(config.messageConverter()).isInstanceOf(Jackson2JsonMessageConverter.class);
    }

    @Test
    void amqpTemplate_shouldUseConfiguredConverter() {
        ConnectionFactory connectionFactory = mock(ConnectionFactory.class);

        RabbitTemplate template = (RabbitTemplate) config.amqpTemplate(connectionFactory);

        assertThat(template.getConnectionFactory()).isEqualTo(connectionFactory);
        assertThat(template.getMessageConverter()).isInstanceOf(Jackson2JsonMessageConverter.class);
    }
}

