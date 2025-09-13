package com.ms.agendamento.infraestruture.dataproviders.database.implementations;

import com.ms.agendamento.application.gateways.Agendamento;
import com.ms.agendamento.domain.StatusAgendamento;
import com.ms.agendamento.domain.TipoAtendimento;
import com.ms.agendamento.domain.model.AgendamentoDomain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class AgendamentoImpl implements Agendamento {

    @Override
    public void deleta(AgendamentoDomain agendamentoDomain) {

    }

    @Override
    public void salvar(AgendamentoDomain agendamentoDomain) {

    }

    @Override
    public Optional<AgendamentoDomain> buscarId(Long id) {
        return Optional.empty();
    }

    @Override
    public List<AgendamentoDomain> buscaAgendamento(Long pacienteId, Long medicoId, TipoAtendimento tipo, StatusAgendamento status) {
        return List.of();
    }

    @Override
    public Optional<AgendamentoDomain> buscarAgendamentoPorPacienteMedicoEData(Long pacienteId, Long medicoId, LocalDateTime dataAgendamento) {
        return Optional.empty();
    }
}
