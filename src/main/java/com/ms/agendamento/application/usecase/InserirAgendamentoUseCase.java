package com.ms.agendamento.application.usecase;

import com.ms.agendamento.domain.model.AgendamentoDomain;
import org.springframework.stereotype.Service;

@Service
public interface InserirAgendamentoUseCase {

    void inserirAgendamento(AgendamentoDomain agendamentoDomain);
}
