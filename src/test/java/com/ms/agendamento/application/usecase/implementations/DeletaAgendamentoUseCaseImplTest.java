package com.ms.agendamento.application.usecase.implementations;

import com.ms.agendamento.application.gateways.Agendamento;
import com.ms.agendamento.domain.domainService.AgendamentoDomainService;
import com.ms.agendamento.domain.domainService.exception.AgendamentoNaoExisteException;
import com.ms.agendamento.domain.model.AgendamentoDomain;
import com.ms.agendamento.mocks.AgendamentoMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeletaAgendamentoUseCaseImplTest {

    @InjectMocks
    private DeletaAgendamentoUseCaseImpl deletaAgendamentoUseCase;

    @Mock
    private Agendamento agendamento;

    @Mock
    private AgendamentoDomainService agendamentoDomainService;

    @Test
    void deletaAgendamento() {
        Long id = 1L;
        AgendamentoDomain domain = AgendamentoMock.getAgendamentoDomain();

        when(agendamentoDomainService.findByIdAgendamento(id)).thenReturn(domain);
        doNothing().when(agendamento).deleta(domain);

        deletaAgendamentoUseCase.deletaAgendamento(id);

        verify(agendamentoDomainService, times(1)).findByIdAgendamento(id);
        verify(agendamento, times(1)).deleta(domain);
    }

    @Test
    void deletaAgendamentoNaoEncontrado(){
        when(agendamentoDomainService.findByIdAgendamento(1L)).thenThrow(new AgendamentoNaoExisteException("Agendamento não encontrado"));

        assertThrows(AgendamentoNaoExisteException.class, () -> deletaAgendamentoUseCase.deletaAgendamento(1L));

        verify(agendamentoDomainService, times(1)).findByIdAgendamento(1L);
    }
}