package com.ms.agendamento.application.usecase.implementations;

import com.ms.agendamento.application.gateways.Agendamento;
import com.ms.agendamento.application.usecase.InserirAgendamentoUseCase;
import com.ms.agendamento.domain.domainService.AgendamentoDomainService;
import com.ms.agendamento.domain.model.AgendamentoDomain;

import java.util.Date;

public class InserirAgendamentoUseCaseImpl implements InserirAgendamentoUseCase {

    private final Agendamento agendamento;
    private final AgendamentoDomainService agendamentoDomainService;

    public InserirAgendamentoUseCaseImpl(Agendamento agendamento, AgendamentoDomainService agendamentoDomainService) {
        this.agendamento = agendamento;
        this.agendamentoDomainService = agendamentoDomainService;
    }

    @Override
    public void inserirAgendamento(AgendamentoDomain agendamentoDomain) {
        this.agendamentoDomainService.checarExistenciaAgendamento(
                agendamentoDomain.getPacienteId(),
                agendamentoDomain.getMedicoId(),
                agendamentoDomain.getDataAgendamento());

        var domain = agendamentoDomain;
        domain.setDataCriacao(new Date());

        this.agendamento.salvar(agendamentoDomain);
    }
}
