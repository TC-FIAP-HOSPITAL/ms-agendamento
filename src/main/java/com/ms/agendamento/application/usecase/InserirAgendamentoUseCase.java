package com.ms.agendamento.application.usecase;

import com.ms.agendamento.domain.model.AgendamentoDomain;

public interface InserirAgendamentoUseCase {

    AgendamentoDomain inserirAgendamento(AgendamentoDomain agendamentoDomain);
}
