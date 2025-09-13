package com.ms.agendamento.domain.domainService.exception;

public class AgendamentoExistenteException extends RuntimeException {
    public AgendamentoExistenteException(String message) {
        super(message);
    }
}
