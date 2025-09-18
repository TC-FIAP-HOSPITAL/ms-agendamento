package com.ms.agendamento.application.usecase;

import com.ms.agendamento.domain.StatusAgendamento;
import com.ms.agendamento.domain.TipoAtendimento;
import com.ms.agendamento.domain.model.AgendamentoDomain;

import java.util.List;

public interface BuscaAgendamentoUseCase {

    List<AgendamentoDomain> buscaAgendamento(String pacienteId, String medicoId, TipoAtendimento tipo, StatusAgendamento status);
}
