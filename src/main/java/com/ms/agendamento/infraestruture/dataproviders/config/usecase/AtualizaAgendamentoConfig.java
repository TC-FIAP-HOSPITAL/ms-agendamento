package com.ms.agendamento.infraestruture.dataproviders.config.usecase;

import com.ms.agendamento.application.gateways.Agendamento;
import com.ms.agendamento.application.gateways.MessagePublisher;
import com.ms.agendamento.application.gateways.UsuarioClient;
import com.ms.agendamento.application.usecase.implementations.AtualizaAgendamentoUseCaseImpl;
import com.ms.agendamento.domain.domainService.AgendamentoDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AtualizaAgendamentoConfig {

    @Bean
    public AtualizaAgendamentoUseCaseImpl  atualizaAgendamentoUseCase(Agendamento agendamento,
                                                                      MessagePublisher messagePublisher,
                                                                      UsuarioClient usuarioClient,
                                                                      AgendamentoDomainService agendamentoDomainService) {
        return new AtualizaAgendamentoUseCaseImpl(agendamento,messagePublisher, usuarioClient, agendamentoDomainService);
    }
}
