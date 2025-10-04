package com.ms.agendamento.application.usecase.implementations;

import com.ms.agendamento.application.gateways.Agendamento;
import com.ms.agendamento.application.gateways.MessagePublisher;
import com.ms.agendamento.application.usecase.InserirAgendamentoUseCase;
import com.ms.agendamento.domain.domainService.AgendamentoDomainService;
import com.ms.agendamento.domain.model.AgendamentoDomain;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

public class InserirAgendamentoUseCaseImpl implements InserirAgendamentoUseCase {

    private final Agendamento agendamento;
    private final MessagePublisher messagePublisher;
    private final AgendamentoDomainService agendamentoDomainService;

    public InserirAgendamentoUseCaseImpl(Agendamento agendamento,
                                         MessagePublisher messagePublisher,
                                         AgendamentoDomainService agendamentoDomainService) {
        this.agendamento = agendamento;
        this.messagePublisher = messagePublisher;
        this.agendamentoDomainService = agendamentoDomainService;
    }

    @Override
    public AgendamentoDomain inserirAgendamento(AgendamentoDomain agendamentoDomain) {
        this.agendamentoDomainService.checarExistenciaAgendamento(
                agendamentoDomain.getPacienteId(),
                agendamentoDomain.getMedicoId(),
                agendamentoDomain.getDataAgendamento());

        agendamentoDomain.setDataCriacao(new Date());
        var agendamentoSaved = this.agendamento.salvar(agendamentoDomain);

        String jwt = SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials()
                .toString();

        messagePublisher.publish(agendamentoSaved, jwt);
        return agendamentoDomain;
    }
}
