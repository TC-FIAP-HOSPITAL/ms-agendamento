package com.ms.agendamento.application.usecase.implementations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
import com.ms.agendamento.domain.model.AgendamentoDomain;
import com.ms.agendamento.mocks.AgendamentoMock;

@ExtendWith(MockitoExtension.class)
class AtualizaAgendamentoUseCaseImplTest {

    @InjectMocks
    private AtualizaAgendamentoUseCaseImpl useCase;

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
    void atualizarAgendamento_sucesso() {
        var autenticacao = new TestingAuthenticationToken("user", "jwt-token");
        SecurityContextHolder.getContext().setAuthentication(autenticacao);

        AgendamentoDomain existente = AgendamentoMock.getAgendamentoDomain();
        AgendamentoDomain atualizado = AgendamentoMock.getAgendamentoDomain();
        atualizado.setMotivo("Atualizado");

        when(agendamentoDomainService.findByIdAgendamento(existente.getId())).thenReturn(existente);
        when(agendamento.salvar(any(AgendamentoDomain.class))).thenReturn(Optional.of(atualizado));

        AgendamentoDomain resultado = useCase.atualizarAgendamento(existente.getId(), atualizado);

        assertThat(resultado.getId()).isEqualTo(existente.getId());
        assertThat(resultado.getDataCriacao()).isEqualTo(existente.getDataCriacao());

        verify(agendamentoDomainService).findByIdAgendamento(existente.getId());
        verify(agendamento).salvar(atualizado);
        verify(messagePublisher).publish(Optional.of(atualizado), "jwt-token");
    }

    @Test
    void atualizarAgendamento_idInexistente() {
        var autenticacao = new TestingAuthenticationToken("user", "jwt-token");
        SecurityContextHolder.getContext().setAuthentication(autenticacao);

        AgendamentoDomain solicitado = AgendamentoMock.getAgendamentoDomain();
        when(agendamentoDomainService.findByIdAgendamento(solicitado.getId())).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> useCase.atualizarAgendamento(solicitado.getId(), solicitado));

        assertThat(exception.getMessage()).isEqualTo("Agendamento não encontrado");
        verify(agendamentoDomainService).findByIdAgendamento(solicitado.getId());
        verifyNoInteractions(agendamento, messagePublisher);
    }
}