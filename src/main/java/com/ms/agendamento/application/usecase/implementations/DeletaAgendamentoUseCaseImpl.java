package com.ms.agendamento.application.usecase.implementations;

import com.ms.agendamento.application.gateways.Agendamento;
import com.ms.agendamento.application.usecase.DeletaAgendamentoUseCase;
import com.ms.agendamento.domain.domainService.AgendamentoDomainService;
import com.ms.agendamento.domain.domainService.exception.AgendamentoNaoExisteException;
import com.ms.agendamento.domain.model.AgendamentoDomain;

public class DeletaAgendamentoUseCaseImpl implements DeletaAgendamentoUseCase {

    private final Agendamento agendamento;
    private final AgendamentoDomainService agendamentoDomainService;

    public DeletaAgendamentoUseCaseImpl(Agendamento agendamento, AgendamentoDomainService agendamentoDomainService) {
        this.agendamento = agendamento;
        this.agendamentoDomainService = agendamentoDomainService;
    }

    @Override
    public Boolean deletaAgendamento(Long idAgendamento) {
        AgendamentoDomain agendamento = this.agendamentoDomainService.findByIdAgendamento(idAgendamento);

        if(agendamento == null){
            throw new AgendamentoNaoExisteException("Agendamento não encontrado");
        }
        this.agendamento.deleta(agendamento);
        return true;
    }
}
