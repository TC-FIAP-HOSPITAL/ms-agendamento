package com.ms.agendamento.entrypoints.controllers.dtos;

import com.ms.agendamento.domain.Especialidade;
import com.ms.agendamento.domain.StatusAgendamento;
import com.ms.agendamento.domain.TipoAtendimento;

import java.time.OffsetDateTime;

public record AgendamentoFilter (
     Long pacienteId,
     Long medicoId,
     StatusAtendimentoDto status,
     EspecialidadeDto especialidade,
     TipoAtendimentoDto tipoAtendimento,
     String dataAgendamento
){
    public static AgendamentoFilter vazio() {
        return new AgendamentoFilter(null, null, null, null, null, null);
    }

    public TipoAtendimento tipoAtendimentoDomain() {
        return tipoAtendimento == null ? null : TipoAtendimento.valueOf(tipoAtendimento.name());
    }

    public StatusAgendamento statusDomain() {
        return status == null ? null : StatusAgendamento.valueOf(status.name());
    }

    public Especialidade especialidadeDomain() {
        return especialidade == null ? null : Especialidade.valueOf(especialidade.name());
    }

    public OffsetDateTime dataAgendamentoDomain() {
        return dataAgendamento == null ? null : OffsetDateTime.parse(dataAgendamento + "T00:00:00Z");
    }
}
