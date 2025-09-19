package com.ms.agendamento.application.gateways;

import com.ms.agendamento.domain.StatusAgendamento;
import com.ms.agendamento.domain.TipoAtendimento;
import com.ms.agendamento.domain.model.AgendamentoDomain;

import java.util.List;
import java.util.Optional;

public interface Agendamento {

    void deleta(AgendamentoDomain agendamentoDomain);

    void salvar(AgendamentoDomain agendamentoDomain);

    Optional<AgendamentoDomain> buscarId(Long id);

    List<AgendamentoDomain> buscaAgendamento(Long pacienteId, Long medicoId, TipoAtendimento tipo, StatusAgendamento status, String dataAgendamento);

    Optional<AgendamentoDomain> buscarAgendamentoPorPacienteMedicoEData(Long pacienteId, Long medicoId, String dataAgendamento);
}
