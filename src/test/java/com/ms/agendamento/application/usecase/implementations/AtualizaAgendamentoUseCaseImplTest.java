package com.ms.agendamento.application.usecase.implementations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

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
import com.ms.agendamento.application.gateways.UsuarioClient;
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

    @Mock
    private UsuarioClient usuarioClient;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void atualizarAgendamento_sucesso() {
        // Configura contexto de autenticação
        var autenticacao = new TestingAuthenticationToken("user", "jwt-token");
        SecurityContextHolder.getContext().setAuthentication(autenticacao);

        // Dados mock
        AgendamentoDomain existente = AgendamentoMock.getAgendamentoDomain();
        // Garantir IDs diferentes para paciente e médico
        existente.setPacienteId(1L);
        existente.setMedicoId(2L);

        AgendamentoDomain atualizado = AgendamentoMock.getAgendamentoDomain();
        atualizado.setId(existente.getId());
        atualizado.setMotivo("Atualizado");
        atualizado.setPacienteId(existente.getPacienteId());
        atualizado.setMedicoId(existente.getMedicoId());

        // Mocks
        when(agendamentoDomainService.findByIdAgendamento(existente.getId())).thenReturn(existente);
        when(agendamento.salvar(any(AgendamentoDomain.class))).thenReturn(atualizado);

        // Chamada do use case
        AgendamentoDomain resultado = useCase.atualizarAgendamento(existente.getId(), atualizado);

        // Verificações
        assertThat(resultado.getId()).isEqualTo(existente.getId());
        assertThat(resultado.getDataCriacao()).isEqualTo(existente.getDataCriacao());

        verify(agendamentoDomainService).findByIdAgendamento(existente.getId());
        verify(usuarioClient).checaExistenciaUsuario(existente.getPacienteId(), "jwt-token");
        verify(usuarioClient).checaExistenciaUsuario(existente.getMedicoId(), "jwt-token");
        verify(agendamento).salvar(atualizado);
        verify(messagePublisher).publish(atualizado, "jwt-token");
    }

    @Test
    void atualizarAgendamento_idInexistente() {
        var autenticacao = new TestingAuthenticationToken("user", "jwt-token");
        SecurityContextHolder.getContext().setAuthentication(autenticacao);

        // Cria um ID inexistente para evitar conflito com mocks existentes
        Long idInexistente = 999L;
        AgendamentoDomain solicitado = AgendamentoMock.getAgendamentoDomain();
        solicitado.setId(idInexistente);

        when(agendamentoDomainService.findByIdAgendamento(idInexistente)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> useCase.atualizarAgendamento(idInexistente, solicitado));

        assertThat(exception.getMessage()).isEqualTo("Agendamento não encontrado");

        verify(agendamentoDomainService).findByIdAgendamento(idInexistente);
        verify(agendamento, never()).salvar(any());
        verify(messagePublisher, never()).publish(any(), any());
        verify(usuarioClient, never()).checaExistenciaUsuario(any(), any());
    }
}
