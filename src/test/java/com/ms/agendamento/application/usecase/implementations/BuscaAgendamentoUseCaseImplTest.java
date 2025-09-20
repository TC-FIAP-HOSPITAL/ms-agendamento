package com.ms.agendamento.application.usecase.implementations;

import com.ms.agendamento.application.gateways.Agendamento;
import com.ms.agendamento.domain.domainService.AgendamentoDomainService;
import com.ms.agendamento.domain.model.AgendamentoDomain;
import com.ms.agendamento.mocks.AgendamentoMock;
import com.ms.agendamentoDomain.gen.model.AgendamentoRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscaAgendamentoUseCaseImplTest {

    @InjectMocks
    private BuscaAgendamentoUseCaseImpl buscaAgendamentoUseCase;

    @Mock
    private Agendamento agendamento;

//    @Mock
//    private AgendamentoDomainService agendamentoDomainService;

//    @Test
//    void buscaAgendamentoPorId() {
//        // Arrange
//        Long id = 1L;
//        when(agendamento.buscarId(id)).thenReturn(true);
//
//        // Act
//        Optional<AgendamentoDomain> resultado = agendamento.buscarId(id);
//
//        // Assert
//        assertTrue(resultado);
//        verify(agendamento, times(1)).buscarId(id);
//    }

//    void buscaAgendamento_sucesso(){
//        AgendamentoRequestDto requestDto = AgendamentoMock.getAgendamentoRequestDto();
//
//        buscaAgendamentoUseCase.buscaAgendamento(requestDto.getPacienteId(), requestDto.getMedicoId(), requestDto.getTipoAtendimento(), requestDto.getStatus(), requestDto.getDataAgendamento());
//    }

}