package com.ms.agendamento.application.usecase.implementations;

import com.ms.agendamento.application.exceptions.UsuarioNaoEncontradoException;
import com.ms.agendamento.application.gateways.Agendamento;
import com.ms.agendamento.application.gateways.MessagePublisher;
import com.ms.agendamento.application.gateways.UsuarioClient;
import com.ms.agendamento.application.usecase.AtualizaAgendamentoUseCase;
import com.ms.agendamento.domain.domainService.AgendamentoDomainService;
import com.ms.agendamento.domain.model.AgendamentoDomain;
import com.ms.agendamento.infraestruture.dataproviders.client.dto.UsuarioDto;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class AtualizaAgendamentoUseCaseImpl implements AtualizaAgendamentoUseCase {

    private final Agendamento agendamento;
    private final MessagePublisher messagePublisher;
    private final UsuarioClient  usuarioClient;
    private final AgendamentoDomainService agendamentoDomainService;

    public AtualizaAgendamentoUseCaseImpl(Agendamento agendamento,
                                          MessagePublisher messagePublisher,
                                          UsuarioClient usuarioClient,
                                          AgendamentoDomainService agendamentoDomainService) {
        this.agendamento = agendamento;
        this.messagePublisher = messagePublisher;
        this.usuarioClient = usuarioClient;
        this.agendamentoDomainService = agendamentoDomainService;
    }

    @Override
    public AgendamentoDomain atualizarAgendamento(Long id, AgendamentoDomain agendamentoDomain) {
        AgendamentoDomain domain = this.agendamentoDomainService.findByIdAgendamento(id);

        if(Objects.nonNull(domain)){
            agendamentoDomain.setId(domain.getId());
            agendamentoDomain.setDataCriacao(domain.getDataCriacao());
        } else {
            throw new RuntimeException("Agendamento não encontrado");
        }

        String jwt = SecurityContextHolder.getContext()
            .getAuthentication()
            .getCredentials()
            .toString();

        usuarioClient.checaExistenciaUsuario(agendamentoDomain.getPacienteId(), jwt);
        usuarioClient.checaExistenciaUsuario(agendamentoDomain.getMedicoId(), jwt);

        var agendamentoUpdated = this.agendamento.salvar(agendamentoDomain);

        messagePublisher.publish(agendamentoUpdated, jwt);
        return agendamentoDomain;
    }
}
