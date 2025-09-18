package com.ms.agendamento.domain.domainService;

import com.ms.agendamento.domain.model.AgendamentoDomain;

import java.time.LocalDateTime;

public interface AgendamentoDomainService {

    void checarExistenciaAgendamento(String idPaciente, String idMedico, LocalDateTime dataAgendamento);

    AgendamentoDomain findByIdAgendamento(String idAgendamento);
}
