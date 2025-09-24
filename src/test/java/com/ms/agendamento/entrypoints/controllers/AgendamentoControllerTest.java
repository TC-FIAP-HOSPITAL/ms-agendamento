//package com.ms.agendamento.entrypoints.controllers;
//
//import com.ms.agendamento.application.usecase.AtualizaAgendamentoUseCase;
//import com.ms.agendamento.application.usecase.BuscaAgendamentoUseCase;
//import com.ms.agendamento.application.usecase.DeletaAgendamentoUseCase;
//import com.ms.agendamento.application.usecase.InserirAgendamentoUseCase;
//import com.ms.agendamento.domain.StatusAgendamento;
//import com.ms.agendamento.domain.TipoAtendimento;
//import com.ms.agendamento.domain.model.AgendamentoDomain;
//import com.ms.agendamento.mocks.AgendamentoMock;
//import com.ms.agendamentoDomain.gen.model.AgendamentoDto;
//import com.ms.agendamentoDomain.gen.model.AgendamentoRequestDto;
//import com.ms.agendamentoDomain.gen.model.StatusDto;
//import com.ms.agendamentoDomain.gen.model.TipoAtendimentoDto;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.doNothing;
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
//        AgendamentoRequestDto agendamentoRequestDto = AgendamentoMock.getAgendamentoRequestDto();
//
//        doNothing().when(atualizaAgendamentoUseCase).atualizarAgendamento(any(Long.class), any());
//
//        ResponseEntity<AgendamentoDto> response = controller._updateAgendamento(id, agendamentoRequestDto);
//
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//        verify(atualizaAgendamentoUseCase, times(1)).atualizarAgendamento(any(Long.class), any());
//    }
//
//    @Test
//    void buscaAgendamento_sucesso(){
//        Long pacienteId = 1L;
//        Long medicoId = 1L;
//        TipoAtendimento tipo = TipoAtendimento.CONSULTA;
//        StatusAgendamento status = StatusAgendamento.AGENDADO;
//        String dataAgendamento = "2025-09-19";
//        TipoAtendimentoDto tipoDto = TipoAtendimentoDto.CONSULTA;
//        StatusDto statusDto = StatusDto.AGENDADO;
//
//        List<AgendamentoDomain>  agendamentoDomainList = AgendamentoMock.getAgendamentoDomainList();
//        when(buscaAgendamentoUseCase.buscaAgendamento(pacienteId, medicoId, tipo, status, dataAgendamento)).thenReturn(agendamentoDomainList);
//        ResponseEntity<List<AgendamentoDto>> response = controller._findAgendamentos(pacienteId, medicoId, statusDto, tipoDto, dataAgendamento);
//
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(buscaAgendamentoUseCase, times(1)).buscaAgendamento(pacienteId, medicoId, tipo, status, dataAgendamento);
//    }
//
//    @Test
//    void buscaAgendamento_semParamentros(){
//        List<AgendamentoDomain> agendamentoDomainList = AgendamentoMock.getAgendamentoDomainList();
//
//        when(buscaAgendamentoUseCase.buscaAgendamento(null, null, null, null, null)).thenReturn(agendamentoDomainList);
//        ResponseEntity<List<AgendamentoDto>> response = controller._findAgendamentos(null, null, null, null, null);
//
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(buscaAgendamentoUseCase, times(1)).buscaAgendamento(null, null, null, null, null);
//    }
//
//    @Test
//    void deletaAgendamento_sucesso(){
//        Long id = 1L;
//        doNothing().when(deletaAgendamentoUseCase).deletaAgendamento(id);
//
//        ResponseEntity<Boolean> response = controller._deleteAgendamento(id);
//
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//        verify(deletaAgendamentoUseCase, times(1)).deletaAgendamento(id);
//    }
//
//    @Test
//    void deveCriarAgendamentoComSucesso() {
//        AgendamentoRequestDto requestDto = AgendamentoMock.getAgendamentoRequestDto();
//
//        ResponseEntity<Void> response = controller._createAgendamento(requestDto);
//
//        assertNotNull(response);
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertNull(response.getBody());
//
//        verify(inserirAgendamentoUseCase, times(1)).inserirAgendamento(any(AgendamentoDomain.class));
//    }
//}