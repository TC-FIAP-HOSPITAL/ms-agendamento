package com.ms.agendamento.application.gateways;

public interface MessagePublisher {

    void publish(Object message, String jwtToken);
}
