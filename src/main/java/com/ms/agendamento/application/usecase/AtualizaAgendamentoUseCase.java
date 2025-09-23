package com.ms.agendamento.application.usecase;

import com.ms.agendamento.domain.model.AgendamentoDomain;

public interface AtualizaAgendamentoUseCase {

    AgendamentoDomain atualizarAgendamento(Long id, AgendamentoDomain agendamentoDomain);
}
