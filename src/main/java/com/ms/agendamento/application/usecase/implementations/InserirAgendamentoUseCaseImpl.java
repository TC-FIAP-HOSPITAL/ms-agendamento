package com.ms.agendamento.application.usecase.implementations;

import com.ms.agendamento.application.exceptions.UsuarioNaoEncontradoException;
import com.ms.agendamento.application.gateways.Agendamento;
import com.ms.agendamento.application.gateways.MessagePublisher;
import com.ms.agendamento.application.gateways.UsuarioClient;
import com.ms.agendamento.application.usecase.InserirAgendamentoUseCase;
import com.ms.agendamento.domain.domainService.AgendamentoDomainService;
import com.ms.agendamento.domain.model.AgendamentoDomain;
import com.ms.agendamento.entrypoints.controllers.presenter.AgendamentoPresenter;
import com.ms.agendamento.infraestruture.dataproviders.client.UsuarioFeignClient;
import com.ms.agendamento.infraestruture.dataproviders.client.dto.UsuarioDto;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.Objects;

public class InserirAgendamentoUseCaseImpl implements InserirAgendamentoUseCase {

    private final Agendamento agendamento;
    private final MessagePublisher messagePublisher;
    private final AgendamentoDomainService agendamentoDomainService;
    private final UsuarioClient usuarioClient;

    public InserirAgendamentoUseCaseImpl(Agendamento agendamento,
                                         MessagePublisher messagePublisher,
                                         AgendamentoDomainService agendamentoDomainService,
                                         UsuarioClient usuarioClient) {
        this.agendamento = agendamento;
        this.messagePublisher = messagePublisher;
        this.agendamentoDomainService = agendamentoDomainService;
        this.usuarioClient = usuarioClient;
    }

    @Override
    public AgendamentoDomain inserirAgendamento(AgendamentoDomain agendamentoDomain) {
        this.agendamentoDomainService.checarExistenciaAgendamento(
                agendamentoDomain.getPacienteId(),
                agendamentoDomain.getMedicoId(),
                agendamentoDomain.getDataAgendamento());

        agendamentoDomain.setDataCriacao(new Date());

        String jwt = SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials()
                .toString();

        //usuarioClient.checaExistenciaUsuario(agendamentoDomain.getPacienteId(), jwt);
        //usuarioClient.checaExistenciaUsuario(agendamentoDomain.getMedicoId(), jwt);

        var agendamentoSaved = this.agendamento.salvar(agendamentoDomain);

        var dto = AgendamentoPresenter.toAgendamentoDto(agendamentoSaved);
        messagePublisher.publish(dto, jwt);
        return agendamentoSaved;
    }
}
