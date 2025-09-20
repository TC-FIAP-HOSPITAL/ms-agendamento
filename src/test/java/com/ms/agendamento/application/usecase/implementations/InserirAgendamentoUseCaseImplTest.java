package com.ms.agendamento.application.usecase.implementations;

import com.ms.agendamento.application.gateways.Agendamento;
import com.ms.agendamento.domain.domainService.AgendamentoDomainService;
import com.ms.agendamento.domain.domainService.exception.AgendamentoExistenteException;
import com.ms.agendamento.domain.model.AgendamentoDomain;
import com.ms.agendamento.mocks.AgendamentoMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InserirAgendamentoUseCaseImplTest {

    @InjectMocks
    private InserirAgendamentoUseCaseImpl inserirAgendamentoUseCase;

    @Mock
    private Agendamento agendamento;

    @Mock
    private AgendamentoDomainService agendamentoDomainService;

    @Test
    void inserirAgendamento_sucesso(){
       AgendamentoDomain domain = AgendamentoMock.getAgendamentoDomain();

       doNothing().when(agendamentoDomainService).checarExistenciaAgendamento(domain.getPacienteId(), domain.getMedicoId(), domain.getDataAgendamento());
       doNothing().when(agendamento).salvar(domain);

       inserirAgendamentoUseCase.inserirAgendamento(domain);

       verify(agendamento, times(1)).salvar(domain);
    }

    @Test
    void inserirAgendamentoExistente(){
        AgendamentoDomain domain = AgendamentoMock.getAgendamentoDomain();

        doThrow(new AgendamentoExistenteException("Esse agendamento já foi cadastrado"))
                .when(agendamentoDomainService)
                .checarExistenciaAgendamento(
                        domain.getPacienteId(),
                        domain.getMedicoId(),
                        domain.getDataAgendamento()
                );;

        assertThrows(AgendamentoExistenteException.class, () -> inserirAgendamentoUseCase.inserirAgendamento(domain));

        verify(agendamentoDomainService, times(1)).checarExistenciaAgendamento(domain.getPacienteId(), domain.getMedicoId(), domain.getDataAgendamento());
    }
}