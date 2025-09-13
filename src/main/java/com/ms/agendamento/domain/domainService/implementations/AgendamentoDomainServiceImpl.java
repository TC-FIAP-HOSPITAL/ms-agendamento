package com.ms.agendamento.domain.domainService.implementations;

import com.ms.agendamento.application.gateways.Agendamento;
import com.ms.agendamento.domain.domainService.AgendamentoDomainService;
import com.ms.agendamento.domain.domainService.exception.AgendamentoExistenteException;
import com.ms.agendamento.domain.domainService.exception.AgendamentoNaoExisteException;
import com.ms.agendamento.domain.model.AgendamentoDomain;

import java.time.LocalDateTime;

public class AgendamentoDomainServiceImpl implements AgendamentoDomainService {

    private final Agendamento agendamento;

    public AgendamentoDomainServiceImpl(Agendamento agendamento) {
        this.agendamento = agendamento;
    }

    @Override
    public void checarExistenciaAgendamento(Long pacienteId, Long medicoId, LocalDateTime dataAgendamento) {
        if(!this.agendamento.buscarAgendamentoPorPacienteMedicoEData(pacienteId, medicoId, dataAgendamento).isPresent()) {
            throw new AgendamentoExistenteException("Agendamento já existente");
        }
    }

    @Override
    public AgendamentoDomain findByIdAgendamento(Long idAgendamento){
        return this.agendamento.buscarId(idAgendamento)
                .orElseThrow(() -> new AgendamentoNaoExisteException("Agendamento não encontrado"));
    }
}
