package com.ms.agendamento.application.usecase.implementations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ms.agendamento.application.gateways.Agendamento;
import com.ms.agendamento.application.gateways.MessagePublisher;
import com.ms.agendamento.domain.domainService.AgendamentoDomainService;
import com.ms.agendamento.domain.domainService.exception.AgendamentoExistenteException;
import com.ms.agendamento.domain.model.AgendamentoDomain;
import com.ms.agendamento.mocks.AgendamentoMock;

@ExtendWith(MockitoExtension.class)
class InserirAgendamentoUseCaseImplTest {

    @InjectMocks
    private InserirAgendamentoUseCaseImpl useCase;

    @Mock
    private Agendamento agendamento;

    @Mock
    private AgendamentoDomainService agendamentoDomainService;

    @Mock
    private MessagePublisher messagePublisher;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void inserirAgendamento_sucesso() {
        var authentication = new TestingAuthenticationToken("user", "jwt-token");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AgendamentoDomain domain = AgendamentoMock.getAgendamentoDomain();
        doNothing().when(agendamentoDomainService).checarExistenciaAgendamento(domain.getPacienteId(),
                domain.getMedicoId(), domain.getDataAgendamento());
        when(agendamento.salvar(any(AgendamentoDomain.class))).thenReturn(Optional.of(domain));

        AgendamentoDomain resultado = useCase.inserirAgendamento(domain);

        assertThat(resultado.getDataCriacao()).isNotNull();
        verify(agendamentoDomainService).checarExistenciaAgendamento(domain.getPacienteId(), domain.getMedicoId(),
                domain.getDataAgendamento());
        verify(agendamento).salvar(domain);
        verify(messagePublisher).publish(Optional.of(domain), "jwt-token");
    }

    @Test
    void inserirAgendamentoExistente_lancaExcecao() {
        var authentication = new TestingAuthenticationToken("user", "jwt-token");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AgendamentoDomain domain = AgendamentoMock.getAgendamentoDomain();
        doThrow(new AgendamentoExistenteException("Esse agendamento já foi cadastrado"))
                .when(agendamentoDomainService)
                .checarExistenciaAgendamento(domain.getPacienteId(), domain.getMedicoId(), domain.getDataAgendamento());

        assertThrows(AgendamentoExistenteException.class, () -> useCase.inserirAgendamento(domain));

        verify(agendamentoDomainService).checarExistenciaAgendamento(domain.getPacienteId(), domain.getMedicoId(),
                domain.getDataAgendamento());
        verifyNoInteractions(agendamento, messagePublisher);
    }
}