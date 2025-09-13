package com.ms.agendamento.application.usecase.implementations;

import com.ms.agendamento.application.gateways.Agendamento;
import com.ms.agendamento.application.usecase.DeletaAgendamentoUseCase;
import com.ms.agendamento.domain.domainService.AgendamentoDomainService;
import com.ms.agendamento.domain.domainService.implementations.AgendamentoDomainServiceImpl;
import com.ms.agendamento.domain.model.AgendamentoDomain;

public class DeletaAgendamentoUseCaseImpl implements DeletaAgendamentoUseCase {

    private final Agendamento agendamento;
    private final AgendamentoDomainService agendamentoDomainService;

    public DeletaAgendamentoUseCaseImpl(Agendamento agendamento) {
        this.agendamento = agendamento;
        this.agendamentoDomainService = new AgendamentoDomainServiceImpl(agendamento);
    }

    @Override
    public void deletaAgendamento(Long idAgendamento) {
        //Verifica a existencia do agendamento antes de deletar
        AgendamentoDomain agendamento = this.agendamentoDomainService.findByIdAgendamento(idAgendamento);
        this.agendamento.deleta(agendamento);
    }
}
