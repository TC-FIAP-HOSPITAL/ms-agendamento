package com.ms.agendamento.application.usecase.implementations;

import com.ms.agendamento.application.gateways.Agendamento;
import com.ms.agendamento.application.usecase.AtualizaAgendamentoUseCase;
import com.ms.agendamento.domain.domainService.AgendamentoDomainService;
import com.ms.agendamento.domain.model.AgendamentoDomain;

import java.util.Objects;

public class AtualizaAgendamentoUseCaseImpl implements AtualizaAgendamentoUseCase {

    private final Agendamento agendamento;
    private final AgendamentoDomainService agendamentoDomainService;

    public AtualizaAgendamentoUseCaseImpl(Agendamento agendamento, AgendamentoDomainService agendamentoDomainService) {
        this.agendamento = agendamento;
        this.agendamentoDomainService = agendamentoDomainService;
    }

    @Override
    public AgendamentoDomain atualizarAgendamento(Long id, AgendamentoDomain agendamentoDomain) {
        AgendamentoDomain domain = this.agendamentoDomainService.findByIdAgendamento(id);

        if(Objects.nonNull(domain)){
            agendamentoDomain.setId(domain.getId());
            agendamentoDomain.setDataCriacao(domain.getDataCriacao());
        }
        this.agendamento.salvar(agendamentoDomain);
        return agendamentoDomain;
    }
}
