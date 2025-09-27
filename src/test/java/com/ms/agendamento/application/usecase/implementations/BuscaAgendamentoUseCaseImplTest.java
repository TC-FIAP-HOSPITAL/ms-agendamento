package com.ms.agendamento.application.usecase.implementations;

import com.ms.agendamento.application.gateways.Agendamento;
import com.ms.agendamento.domain.Especialidade;
import com.ms.agendamento.domain.StatusAgendamento;
import com.ms.agendamento.domain.TipoAtendimento;
import com.ms.agendamento.domain.model.AgendamentoDomain;
import com.ms.agendamento.mocks.AgendamentoMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscaAgendamentoUseCaseImplTest {

    @InjectMocks
    private BuscaAgendamentoUseCaseImpl buscaAgendamentoUseCase;

    @Mock
    private Agendamento agendamento;

    @Test
    void deveBuscarAgendamento_semParamentos() {

        List<AgendamentoDomain> domains = AgendamentoMock.getAgendamentoDomainList();

        when(agendamento.buscaAgendamento(null, null, null, null, null, null)).thenReturn(domains);
        List<AgendamentoDomain> result = buscaAgendamentoUseCase.buscaAgendamento(null, null, null, null, null, null);

        assertNotNull(result);
        assertEquals(domains.size(), result.size());
        verify(agendamento, times(1)).buscaAgendamento(null, null, null, null, null,  null);
    }

    @Test
    void deveBuscarAgendamento_pacienteId() {

        List<AgendamentoDomain> domains = AgendamentoMock.getAgendamentoDomainList();

        when(agendamento.buscaAgendamento(1L, null, null, null, null,  null)).thenReturn(domains);
        List<AgendamentoDomain> result = buscaAgendamentoUseCase.buscaAgendamento(1L, null, null, null, null, null);

        assertNotNull(result);
        assertEquals(domains.size(), result.size());
        verify(agendamento, times(1)).buscaAgendamento(1L, null, null, null,  null, null);
    }

    @Test
    void deveBuscarAgendamento_medicoId() {
        List<AgendamentoDomain> domains = AgendamentoMock.getAgendamentoDomainList();

        when(agendamento.buscaAgendamento(null, 1L, null, null, null,  null)).thenReturn(domains);
        List<AgendamentoDomain> result = buscaAgendamentoUseCase.buscaAgendamento(null, 1L, null, null,  null, null);

        assertNotNull(result);
        assertEquals(domains.size(), result.size());
        verify(agendamento, times(1)).buscaAgendamento(null, 1L, null, null, null, null);
    }

    @Test
    void deveBuscarAgendamento_tipoAtendimento() {
        List<AgendamentoDomain> domains = AgendamentoMock.getAgendamentoDomainList();

        when(agendamento.buscaAgendamento(null, null, TipoAtendimento.CONSULTA, null, null,  null)).thenReturn(domains);
        List<AgendamentoDomain> result = buscaAgendamentoUseCase.buscaAgendamento(null, null, TipoAtendimento.CONSULTA, null, null, null);

        assertNotNull(result);
        assertEquals(domains.size(), result.size());
        verify(agendamento, times(1)).buscaAgendamento(null, null, TipoAtendimento.CONSULTA, null, null, null);
    }

    @Test
    void deveBuscarAgendamento_statusAtendimento() {
        List<AgendamentoDomain> domains = AgendamentoMock.getAgendamentoDomainList();

        when(agendamento.buscaAgendamento(null, null, null, StatusAgendamento.AGENDADO, null,null)).thenReturn(domains);
        List<AgendamentoDomain> result = buscaAgendamentoUseCase.buscaAgendamento(null, null, null, StatusAgendamento.AGENDADO, null,null);

        assertNotNull(result);
        assertEquals(domains.size(), result.size());
        verify(agendamento, times(1)).buscaAgendamento(null, null, null, StatusAgendamento.AGENDADO, null, null);
    }

    @Test
    void deveBuscarAgendamento_especialidade() {
        List<AgendamentoDomain> domains = AgendamentoMock.getAgendamentoDomainList();

        when(agendamento.buscaAgendamento(null, null, null, null, Especialidade.CARDIOLOGIA,null)).thenReturn(domains);
        List<AgendamentoDomain> result = buscaAgendamentoUseCase.buscaAgendamento(null, null, null, null, Especialidade.CARDIOLOGIA, null);

        assertNotNull(result);
        assertEquals(domains.size(), result.size());
        verify(agendamento, times(1)).buscaAgendamento(null, null, null, null, Especialidade.CARDIOLOGIA,null);
    }

    @Test
    void deveBuscarAgendamento_dataAgendamento() {
        List<AgendamentoDomain> domains = AgendamentoMock.getAgendamentoDomainList();

        when(agendamento.buscaAgendamento(null, null, null, null, null, OffsetDateTime.parse("2025-09-20T00:00:00Z"))).thenReturn(domains);
        List<AgendamentoDomain> result = buscaAgendamentoUseCase.buscaAgendamento(null, null, null, null, null, OffsetDateTime.parse("2025-09-20T00:00:00Z"));

        assertNotNull(result);
        assertEquals(domains.size(), result.size());
        verify(agendamento, times(1)).buscaAgendamento(null, null, null, null, null,OffsetDateTime.parse("2025-09-20T00:00:00Z"));
    }
}