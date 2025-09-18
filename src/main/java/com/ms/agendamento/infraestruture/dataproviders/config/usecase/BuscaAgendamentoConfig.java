package com.ms.agendamento.infraestruture.dataproviders.config.usecase;

import com.ms.agendamento.application.gateways.Agendamento;
import com.ms.agendamento.application.usecase.implementations.BuscaAgendamentoUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BuscaAgendamentoConfig {

    @Bean
    public BuscaAgendamentoUseCaseImpl  buscaAgendamentoUseCase(Agendamento agendamento) {
        return new BuscaAgendamentoUseCaseImpl(agendamento);
    }
}
