package com.ms.agendamento.application.usecase;

import com.ms.agendamento.domain.model.AgendamentoDomain;

public interface AtualizaAgendamentoUseCase {

    void atualizarAgendamento(Long id, AgendamentoDomain agendamentoDomain);
}
