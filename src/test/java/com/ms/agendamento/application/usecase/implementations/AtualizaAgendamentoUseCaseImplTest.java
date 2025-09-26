package com.ms.agendamento.application.usecase.implementations;


import com.ms.agendamento.application.gateways.Agendamento;
import com.ms.agendamento.application.gateways.MessagePublisher;
import com.ms.agendamento.domain.domainService.AgendamentoDomainService;
import com.ms.agendamento.domain.domainService.exception.AgendamentoNaoExisteException;
import com.ms.agendamento.domain.model.AgendamentoDomain;
import com.ms.agendamento.mocks.AgendamentoMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtualizaAgendamentoUseCaseImplTest {

    @InjectMocks
    private AtualizaAgendamentoUseCaseImpl atualizaAgendamentoUseCase;

    @Mock
    private Agendamento agendamento;

    @Mock
    private AgendamentoDomainService agendamentoDomainService;

    @Mock
    private MessagePublisher messagePublisher;

    @Test
    void atualizarAgendamento_sucesso() {
        // Arrange
        AgendamentoDomain agendamentoDomain = AgendamentoMock.getAgendamentoDomain();
        when(agendamentoDomainService.findByIdAgendamento(agendamentoDomain.getId()))
                .thenReturn(agendamentoDomain);
        doNothing().when(agendamento).salvar(agendamentoDomain);

        // Act
        AgendamentoDomain resultado = atualizaAgendamentoUseCase.atualizarAgendamento(
                agendamentoDomain.getId(), agendamentoDomain);

        // Assert
        verify(agendamentoDomainService, times(1)).findByIdAgendamento(agendamentoDomain.getId());
        verify(agendamento, times(1)).salvar(agendamentoDomain);
        verify(messagePublisher, times(1)).publish(agendamentoDomain);

        assertEquals(agendamentoDomain.getId(), resultado.getId());
    }

    @Test
    void atualizarAgendamento_idInexistente() {
        // Arrange
        Long idInexistente = 999L;
        AgendamentoDomain agendamentoDomain = AgendamentoMock.getAgendamentoDomain();
        when(agendamentoDomainService.findByIdAgendamento(idInexistente)).thenReturn(null);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                atualizaAgendamentoUseCase.atualizarAgendamento(idInexistente, agendamentoDomain)
        );

        assertEquals("Agendamento não encontrado", exception.getMessage());

        verify(agendamentoDomainService, times(1)).findByIdAgendamento(idInexistente);
        verifyNoInteractions(agendamento, messagePublisher);
    }

    @Test
    void atualizarAgendamento_objetoInexistente_anyId() {
        // Arrange
        Long id = 1L;
        AgendamentoDomain agendamentoDomain = AgendamentoMock.getAgendamentoDomain();
        when(agendamentoDomainService.findByIdAgendamento(any()))
                .thenReturn(null);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                atualizaAgendamentoUseCase.atualizarAgendamento(id, agendamentoDomain)
        );

        assertEquals("Agendamento não encontrado", exception.getMessage());

        verify(agendamentoDomainService, times(1)).findByIdAgendamento(any());
        verifyNoInteractions(agendamento, messagePublisher);
    }
}