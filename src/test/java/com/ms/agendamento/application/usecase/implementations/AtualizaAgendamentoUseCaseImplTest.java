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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AtualizaAgendamentoUseCaseImplTest {

    @InjectMocks
    private AtualizaAgendamentoUseCaseImpl atualizaAgendamentoUseCase;

    @Mock
    private Agendamento agendamento;

    @Mock
    private AgendamentoDomainService agendamentoDomainService;

    @Test
    void atualizarAgendamento_sucesso(){
        //Arrange
        AgendamentoDomain agendamentoDomain = AgendamentoMock.getAgendamentoDomain();

        //Act
        when(agendamentoDomainService.findByIdAgendamento(agendamentoDomain.getId())).thenReturn(agendamentoDomain);
        doNothing().when(agendamento).salvar(agendamentoDomain);
        atualizaAgendamentoUseCase.atualizarAgendamento(agendamentoDomain.getId(),agendamentoDomain);

        //Assert
        verify(agendamentoDomainService, times(1)).findByIdAgendamento(agendamentoDomain.getId());
        verify(agendamento, times(1)).salvar(agendamentoDomain);
    }

    @Test
    void atualizaAgendamento_idInexistente(){
        AgendamentoDomain agendamentoDomain = AgendamentoMock.getAgendamentoDomain();
        when(agendamentoDomainService.findByIdAgendamento(agendamentoDomain.getId())).thenReturn(agendamentoDomain);

        atualizaAgendamentoUseCase.atualizarAgendamento(agendamentoDomain.getId(),agendamentoDomain);

        verify(agendamentoDomainService, times(1)).findByIdAgendamento(agendamentoDomain.getId());
    }

    @Test
    void atualizaAgendamento_objetoInexistente(){
        Long id = 1L;
        AgendamentoDomain agendamentoDomain = AgendamentoMock.getAgendamentoDomain();

        when(agendamentoDomainService.findByIdAgendamento(any())).thenThrow(new AgendamentoNaoExisteException("Agendamento não cadastrado"));

        assertThrows(AgendamentoNaoExisteException.class, () -> atualizaAgendamentoUseCase.atualizarAgendamento(id,agendamentoDomain));
        verify(agendamentoDomainService, times(1)).findByIdAgendamento(any());
    }
}