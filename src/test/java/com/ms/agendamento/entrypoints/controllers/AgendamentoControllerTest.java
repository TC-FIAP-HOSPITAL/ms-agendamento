//package com.ms.agendamento.entrypoints.controllers;
//
//import com.ms.agendamento.application.usecase.AtualizaAgendamentoUseCase;
//import com.ms.agendamento.application.usecase.BuscaAgendamentoUseCase;
//import com.ms.agendamento.application.usecase.DeletaAgendamentoUseCase;
//import com.ms.agendamento.application.usecase.InserirAgendamentoUseCase;
//import com.ms.agendamento.domain.Especialidade;
//import com.ms.agendamento.domain.StatusAgendamento;
//import com.ms.agendamento.domain.TipoAtendimento;
//import com.ms.agendamento.domain.model.AgendamentoDomain;
//import com.ms.agendamento.entrypoints.controllers.dtos.*;
//import com.ms.agendamento.mocks.AgendamentoMock;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.OffsetDateTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class AgendamentoControllerTest {
//
//    @InjectMocks
//    private AgendamentoController controller;
//
//    @Mock
//    private InserirAgendamentoUseCase inserirAgendamentoUseCase;
//
//    @Mock
//    private BuscaAgendamentoUseCase  buscaAgendamentoUseCase;
//
//    @Mock
//    private AtualizaAgendamentoUseCase atualizaAgendamentoUseCase;
//
//    @Mock
//    private DeletaAgendamentoUseCase deletaAgendamentoUseCase;
//
//
//    @Test
//    void atualizaAgendamento_sucesso(){
//        Long id = 1L;
//        AgendamentoRequestDto requestDto = AgendamentoMock.getAgendamentoRequestDto();
//        AgendamentoDomain mockResponse = AgendamentoMock.getAgendamentoDomain();
//
//        when(atualizaAgendamentoUseCase.atualizarAgendamento(eq(id), any(AgendamentoDomain.class)))
//                .thenReturn(mockResponse);
//
//        AgendamentoDto response = controller.updateAgendamento(id, requestDto);
//
//        assertNotNull(response);
//        verify(atualizaAgendamentoUseCase, times(1))
//                .atualizarAgendamento(eq(id), any(AgendamentoDomain.class));
//    }
//
//    @Test
//    void buscaAgendamento_sucesso(){
//        // Dados de entrada
//        TipoAtendimento tipo = TipoAtendimento.CONSULTA;
//        StatusAgendamento status = StatusAgendamento.AGENDADO;
//        Especialidade especialidade = Especialidade.CARDIOLOGIA;
//
//        AgendamentoFilter filter = new AgendamentoFilter(
//                1L,
//                1L,
//                StatusAtendimentoDto.AGENDADO,
//                EspecialidadeDto.CARDIOLOGIA,
//                TipoAtendimentoDto.CONSULTA,
//                "2025-01-01"
//        );
//
//        List<AgendamentoDomain> agendamentoDomainList = AgendamentoMock.getAgendamentoDomainList();
//        when(buscaAgendamentoUseCase.buscaAgendamento(
//                eq(1L),
//                eq(1L),
//                eq(tipo),
//                eq(status),
//                eq(especialidade),
//                any(OffsetDateTime.class)
//        )).thenReturn(agendamentoDomainList);
//
//        List<AgendamentoDto> response = controller.findAgendamento(filter);
//
//        assertNotNull(response);
//        verify(buscaAgendamentoUseCase, times(1)).buscaAgendamento(
//                eq(1L),
//                eq(1L),
//                eq(tipo),
//                eq(status),
//                eq(especialidade),
//                any(OffsetDateTime.class)
//        );
//    }
//
//    @Test
//    void buscaAgendamento_semParamentros(){
//        List<AgendamentoDomain> agendamentoDomainList = AgendamentoMock.getAgendamentoDomainList();
//
//        AgendamentoFilter filter = new AgendamentoFilter(
//                1L,
//                1L,
//                StatusAtendimentoDto.AGENDADO,
//                EspecialidadeDto.CARDIOLOGIA,
//                TipoAtendimentoDto.CONSULTA,
//                "2025-01-01"
//        );
//
//        when(buscaAgendamentoUseCase.buscaAgendamento(any(), any(), any(), any(), any(), any())).thenReturn(agendamentoDomainList);
//
//        List<AgendamentoDto> response = controller.findAgendamento(filter);
//
//        assertNotNull(response);
//        verify(buscaAgendamentoUseCase, times(1)).buscaAgendamento(any(), any(), any(), any(), any(), any());
//    }
//
//    @Test
//    void deletaAgendamento_sucesso(){
//        Long id = 1L;
//        when(deletaAgendamentoUseCase.deletaAgendamento(id)).thenReturn(true);
//
//        Boolean response = controller.deleteAgendamento(id);
//        assertTrue(response);
//        verify(deletaAgendamentoUseCase, times(1)).deletaAgendamento(id);
//    }
//
//    @Test
//    void deveCriarAgendamentoComSucesso() {
//        AgendamentoRequestDto requestDto = AgendamentoMock.getAgendamentoRequestDto();
//
//        when(inserirAgendamentoUseCase.inserirAgendamento(any(AgendamentoDomain.class)))
//                .thenReturn(new  AgendamentoDomain());
//
//        AgendamentoDto response = controller.createAgendamento(requestDto);
//
//        assertNotNull(response);
//        verify(inserirAgendamentoUseCase, times(1)).inserirAgendamento(any(AgendamentoDomain.class));
//    }
//}