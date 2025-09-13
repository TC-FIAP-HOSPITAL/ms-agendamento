package com.ms.agendamento.domain.domainService.exception;

public class AgendamentoNaoExisteException extends RuntimeException     {
    public AgendamentoNaoExisteException(String message) {
        super(message);
    }
}
