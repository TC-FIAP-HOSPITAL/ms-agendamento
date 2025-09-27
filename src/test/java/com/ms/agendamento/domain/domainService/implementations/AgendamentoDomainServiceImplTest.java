package com.ms.agendamento.domain.domainService.implementations;

import com.ms.agendamento.application.gateways.Agendamento;
import com.ms.agendamento.domain.domainService.exception.AgendamentoExistenteException;
import com.ms.agendamento.domain.domainService.exception.AgendamentoNaoExisteException;
import com.ms.agendamento.domain.model.AgendamentoDomain;
import com.ms.agendamento.mocks.AgendamentoMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgendamentoDomainServiceImplTest {

    @InjectMocks
    private AgendamentoDomainServiceImpl agendamentoDomainService;

    @Mock
    private Agendamento agendamento;

    @Test
    void checarExistenciaAgendamento_existente(){
        AgendamentoDomain domain = AgendamentoMock.getAgendamentoDomain();

        when(agendamento.buscarAgendamentoPorPacienteMedicoEData(anyLong(), anyLong(), any())).thenReturn(Optional.of(domain));

        assertThrows(AgendamentoExistenteException.class, () -> agendamentoDomainService.checarExistenciaAgendamento(anyLong(), anyLong(), any()));
        verify(agendamento).buscarAgendamentoPorPacienteMedicoEData(anyLong(), anyLong(), any());
    }

    @Test
    void checarExistenciaAgendamento_naoExistente(){
        when(agendamento.buscarAgendamentoPorPacienteMedicoEData(anyLong(), anyLong(), any())).thenReturn(Optional.empty());

        agendamentoDomainService.checarExistenciaAgendamento(anyLong(), anyLong(), any());

        verify(agendamento).buscarAgendamentoPorPacienteMedicoEData(anyLong(), anyLong(), any());
    }

    @Test
    void encontrarAgendamentoById_sucesso(){
        AgendamentoDomain domain = AgendamentoMock.getAgendamentoDomain();

        when(agendamento.buscarId(1L)).thenReturn(Optional.of(domain));
        AgendamentoDomain result = agendamentoDomainService.findByIdAgendamento(1L);

        verify(agendamento, times(1)).buscarId(1L);
        assertNotNull(result);
        assertEquals(domain, result);
    }

    @Test
    void encontrarAgendamento_naoExistente(){
        when(agendamento.buscarId(1L)).thenReturn(Optional.empty());

        assertThrows(AgendamentoNaoExisteException.class, () ->  agendamentoDomainService.findByIdAgendamento(1L));

        verify(agendamento, times(1)).buscarId(1L);
    }
}