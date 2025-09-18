package com.ms.agendamento.application.usecase.implementations;

import com.ms.agendamento.application.gateways.Agendamento;
import com.ms.agendamento.application.usecase.AtualizaAgendamentoUseCase;
import com.ms.agendamento.domain.domainService.AgendamentoDomainService;
import com.ms.agendamento.domain.model.AgendamentoDomain;

public class AtualizaAgendamentoUseCaseImpl implements AtualizaAgendamentoUseCase {

    private final Agendamento agendamento;
    private final AgendamentoDomainService agendamentoDomainService;

    public AtualizaAgendamentoUseCaseImpl(Agendamento agendamento, AgendamentoDomainService agendamentoDomainService) {
        this.agendamento = agendamento;
        this.agendamentoDomainService = agendamentoDomainService;
    }

    @Override
    public void atualizarAgendamento(Long id, AgendamentoDomain agendamentoDomain) {
        AgendamentoDomain agendamento = this.agendamentoDomainService.findByIdAgendamento(id);
        this.agendamento.salvar(agendamento);
    }
}
