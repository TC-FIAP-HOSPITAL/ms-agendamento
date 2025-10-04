package com.ms.agendamento.application.usecase.implementations;

import com.ms.agendamento.application.gateways.Agendamento;
import com.ms.agendamento.application.gateways.MessagePublisher;
import com.ms.agendamento.application.usecase.AtualizaAgendamentoUseCase;
import com.ms.agendamento.domain.domainService.AgendamentoDomainService;
import com.ms.agendamento.domain.model.AgendamentoDomain;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class AtualizaAgendamentoUseCaseImpl implements AtualizaAgendamentoUseCase {

    private final Agendamento agendamento;
    private final MessagePublisher messagePublisher;
    private final AgendamentoDomainService agendamentoDomainService;

    public AtualizaAgendamentoUseCaseImpl(Agendamento agendamento,
                                          MessagePublisher messagePublisher,
                                          AgendamentoDomainService agendamentoDomainService) {
        this.agendamento = agendamento;
        this.messagePublisher = messagePublisher;
        this.agendamentoDomainService = agendamentoDomainService;
    }

        @Override
        public AgendamentoDomain atualizarAgendamento(Long id, AgendamentoDomain agendamentoDomain) {
            AgendamentoDomain domain = this.agendamentoDomainService.findByIdAgendamento(id);

            if(Objects.nonNull(domain)){
                agendamentoDomain.setId(domain.getId());
                agendamentoDomain.setDataCriacao(domain.getDataCriacao());
            } else {
                throw new RuntimeException("Agendamento não encontrado");
            }

            var agendamentoUpdated = this.agendamento.salvar(agendamentoDomain);

            String jwt = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getCredentials()
                    .toString();

            messagePublisher.publish(agendamentoUpdated, jwt);

            return agendamentoDomain;
        }
}
