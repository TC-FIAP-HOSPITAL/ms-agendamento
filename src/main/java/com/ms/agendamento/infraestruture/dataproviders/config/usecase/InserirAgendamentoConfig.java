package com.ms.agendamento.infraestruture.dataproviders.config.usecase;

import com.ms.agendamento.application.gateways.Agendamento;
import com.ms.agendamento.application.gateways.MessagePublisher;
import com.ms.agendamento.application.usecase.implementations.InserirAgendamentoUseCaseImpl;
import com.ms.agendamento.domain.domainService.implementations.AgendamentoDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InserirAgendamentoConfig {

    @Bean
    public InserirAgendamentoUseCaseImpl inserirAgendamentoUseCaseImpl(Agendamento agendamento,
                                                                       AgendamentoDomainServiceImpl agendamentoDomainService,
                                                                       MessagePublisher messagePublisher) {
        return new InserirAgendamentoUseCaseImpl(agendamento, messagePublisher, agendamentoDomainService);

    }
}
