package com.ms.agendamento.application.usecase.implementations;

import com.ms.agendamento.application.gateways.Agendamento;
import com.ms.agendamento.application.usecase.BuscaAgendamentoUseCase;
import com.ms.agendamento.domain.StatusAgendamento;
import com.ms.agendamento.domain.TipoAtendimento;
import com.ms.agendamento.domain.model.AgendamentoDomain;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuscaAgendamentoUseCaseImpl implements BuscaAgendamentoUseCase {

    private final Agendamento agendamento;

    public BuscaAgendamentoUseCaseImpl(Agendamento agendamento) {
        this.agendamento = agendamento;
    }

    @Override
    public List<AgendamentoDomain> buscaAgendamento(Long pacienteId, Long medicoId, TipoAtendimento tipo, StatusAgendamento status, String dataAgendamento) {
        return this.agendamento.buscaAgendamento(pacienteId, medicoId, tipo, status, dataAgendamento);
    }
}
