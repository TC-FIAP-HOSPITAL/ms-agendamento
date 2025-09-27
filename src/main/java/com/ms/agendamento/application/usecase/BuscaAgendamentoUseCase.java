package com.ms.agendamento.application.usecase;

import com.ms.agendamento.domain.Especialidade;
import com.ms.agendamento.domain.StatusAgendamento;
import com.ms.agendamento.domain.TipoAtendimento;
import com.ms.agendamento.domain.model.AgendamentoDomain;

import java.time.OffsetDateTime;
import java.util.List;

public interface BuscaAgendamentoUseCase {

    List<AgendamentoDomain> buscaAgendamento(Long pacienteId,
                                             Long medicoId,
                                             TipoAtendimento tipo,
                                             StatusAgendamento status,
                                             Especialidade especialidade,
                                             OffsetDateTime dataAgendamento);
}
