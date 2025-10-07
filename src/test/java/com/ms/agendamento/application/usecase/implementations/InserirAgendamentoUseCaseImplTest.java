package com.ms.agendamento.application.usecase.implementations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.ms.agendamento.entrypoints.controllers.presenter.AgendamentoPresenter;
import com.ms.agendamento.infraestruture.dataproviders.client.dto.UsuarioDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ms.agendamento.application.exceptions.UsuarioNaoEncontradoException;
import com.ms.agendamento.application.gateways.Agendamento;
import com.ms.agendamento.application.gateways.MessagePublisher;
import com.ms.agendamento.application.gateways.UsuarioClient;
import com.ms.agendamento.domain.domainService.AgendamentoDomainService;
import com.ms.agendamento.domain.domainService.exception.AgendamentoExistenteException;
import com.ms.agendamento.domain.model.AgendamentoDomain;
import com.ms.agendamento.mocks.AgendamentoMock;

@ExtendWith(MockitoExtension.class)
class InserirAgendamentoUseCaseImplTest {

    @InjectMocks
    private InserirAgendamentoUseCaseImpl useCase;

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
    void inserirAgendamento_sucesso() {
        // Configura autenticação com JWT
        var authentication = new TestingAuthenticationToken("user", "jwt-token");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AgendamentoDomain domain = AgendamentoMock.getAgendamentoDomain();

        // Método void: checagem de existência do agendamento
        doNothing().when(agendamentoDomainService).checarExistenciaAgendamento(
                domain.getPacienteId(),
                domain.getMedicoId(),
                domain.getDataAgendamento()
        );

//        // Métodos que retornam algo: checagem de usuários
//        when(usuarioClient.checaExistenciaUsuario(domain.getPacienteId(), "jwt-token"))
//                .thenReturn(new UsuarioDto(
//                        domain.getPacienteId().toString(),
//                        "Paciente Teste",
//                        "paciente@email.com",
//                        "pacienteUser",
//                        "PACIENTE"
//                ));
//        when(usuarioClient.checaExistenciaUsuario(domain.getMedicoId(), "jwt-token"))
//                .thenReturn(new UsuarioDto(
//                        domain.getMedicoId().toString(),
//                        "Medico Teste",
//                        "medico@email.com",
//                        "medicoUser",
//                        "MEDICO"
//                ));

        // Simula salvar o agendamento
        when(agendamento.salvar(any(AgendamentoDomain.class))).thenReturn(domain);

        // Executa o caso de uso
        AgendamentoDomain resultado = useCase.inserirAgendamento(domain);

        // Verifica se a data de criação foi setada
        assertThat(resultado.getDataCriacao()).isNotNull();

        // Verifica interações
        verify(agendamentoDomainService).checarExistenciaAgendamento(
                domain.getPacienteId(),
                domain.getMedicoId(),
                domain.getDataAgendamento()
        );

        // Permite múltiplas chamadas sem quebrar
//        verify(usuarioClient, atLeastOnce()).checaExistenciaUsuario(domain.getPacienteId(), "jwt-token");
//        verify(usuarioClient, atLeastOnce()).checaExistenciaUsuario(domain.getMedicoId(), "jwt-token");

        verify(agendamento).salvar(domain);
        verify(messagePublisher).publish(AgendamentoPresenter.toAgendamentoDto(domain), "jwt-token");
    }


    @Test
    void inserirAgendamentoExistente_lancaExcecao() {
        var authentication = new TestingAuthenticationToken("user", "jwt-token");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AgendamentoDomain domain = AgendamentoMock.getAgendamentoDomain();

        doThrow(new AgendamentoExistenteException("Esse agendamento já foi cadastrado"))
                .when(agendamentoDomainService)
                .checarExistenciaAgendamento(domain.getPacienteId(), domain.getMedicoId(), domain.getDataAgendamento());

        assertThrows(AgendamentoExistenteException.class, () -> useCase.inserirAgendamento(domain));

        verify(agendamentoDomainService).checarExistenciaAgendamento(
                domain.getPacienteId(),
                domain.getMedicoId(),
                domain.getDataAgendamento()
        );

        verifyNoInteractions(usuarioClient, agendamento, messagePublisher);
    }

}
