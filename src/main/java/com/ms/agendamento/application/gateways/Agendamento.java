package com.ms.agendamento.application.gateways;

import com.ms.agendamento.domain.StatusAgendamento;
import com.ms.agendamento.domain.TipoAtendimento;
import com.ms.agendamento.domain.model.AgendamentoDomain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface Agendamento {

    void deleta(AgendamentoDomain agendamentoDomain);

    void salvar(AgendamentoDomain agendamentoDomain);

    Optional<AgendamentoDomain> buscarId(String id);

    List<AgendamentoDomain> buscaAgendamento(String pacienteId, String medicoId, TipoAtendimento tipo, StatusAgendamento status);

    Optional<AgendamentoDomain> buscarAgendamentoPorPacienteMedicoEData(String pacienteId, String medicoId, LocalDateTime dataAgendamento);
}
