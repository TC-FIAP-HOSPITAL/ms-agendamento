package com.ms.agendamento.application.usecase;

import org.springframework.stereotype.Service;

@Service
public interface DeletaAgendamentoUseCase {

    void deletaAgendamento(String id);
}
