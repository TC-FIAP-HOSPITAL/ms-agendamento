package com.ms.agendamento.infraestruture.dataproviders.config.usecase;

import com.ms.agendamento.application.gateways.Agendamento;
import com.ms.agendamento.application.usecase.implementations.DeletaAgendamentoUseCaseImpl;
import com.ms.agendamento.domain.domainService.AgendamentoDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeletaAgendamentoConfig {

    @Bean
    public DeletaAgendamentoUseCaseImpl deletaAgendamentoUseCaseImpl(Agendamento agendamento,
                                                                     AgendamentoDomainService agendamentoDomainService) {
        return new DeletaAgendamentoUseCaseImpl(agendamento, agendamentoDomainService);

    }
}
