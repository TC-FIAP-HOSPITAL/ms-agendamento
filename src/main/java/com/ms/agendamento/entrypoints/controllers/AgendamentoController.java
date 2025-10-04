package com.ms.agendamento.entrypoints.controllers;

import com.ms.agendamento.application.usecase.AtualizaAgendamentoUseCase;
import com.ms.agendamento.application.usecase.BuscaAgendamentoUseCase;
import com.ms.agendamento.application.usecase.DeletaAgendamentoUseCase;
import com.ms.agendamento.application.usecase.InserirAgendamentoUseCase;
import com.ms.agendamento.domain.model.AgendamentoDomain;
import com.ms.agendamento.entrypoints.controllers.dtos.AgendamentoDto;
import com.ms.agendamento.entrypoints.controllers.dtos.AgendamentoFilter;
import com.ms.agendamento.entrypoints.controllers.dtos.AgendamentoRequestDto;
import com.ms.agendamento.entrypoints.controllers.presenter.AgendamentoPresenter;
import com.ms.agendamento.infraestruture.dataproviders.config.security.Role;
import com.ms.agendamento.infraestruture.dataproviders.config.security.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/v1")
public class AgendamentoController {

    private final InserirAgendamentoUseCase inserirAgendamentoUseCase;
    private final AtualizaAgendamentoUseCase atualizaAgendamentoUseCase;
    private final DeletaAgendamentoUseCase deletaAgendamentoUseCase;
    private final BuscaAgendamentoUseCase buscaAgendamentoUseCase;
    private final SecurityUtil securityUtil;

    public AgendamentoController(InserirAgendamentoUseCase inserirAgendamentoUseCase,
                                 AtualizaAgendamentoUseCase atualizaAgendamentoUseCase,
                                 DeletaAgendamentoUseCase deletaAgendamentoUseCase,
                                 BuscaAgendamentoUseCase buscaAgendamentoUseCase,
                                 SecurityUtil securityUtil) {
        this.inserirAgendamentoUseCase = inserirAgendamentoUseCase;
        this.atualizaAgendamentoUseCase = atualizaAgendamentoUseCase;
        this.deletaAgendamentoUseCase = deletaAgendamentoUseCase;
        this.buscaAgendamentoUseCase = buscaAgendamentoUseCase;
        this.securityUtil = securityUtil;
    }

    @MutationMapping
    public AgendamentoDto createAgendamento(@Argument AgendamentoRequestDto request) {
        ensureCanEdit();
        var domain = AgendamentoPresenter.toAgendamentoDomain(request);
        AgendamentoDomain salvo = inserirAgendamentoUseCase.inserirAgendamento(domain);
        return AgendamentoPresenter.toAgendamentoDto(salvo);
    }

    @MutationMapping
    public Boolean deleteAgendamento(@Argument Long id) {
        ensureAdmin();
        return deletaAgendamentoUseCase.deletaAgendamento(id);
    }


    @QueryMapping
    public List<AgendamentoDto> findAgendamento(@Argument AgendamentoFilter filter) {
        filter = Optional.ofNullable(filter).orElse(new AgendamentoFilter(null, null, null, null, null, null));

        Role role = securityUtil.getRole();
        boolean isAdmin = securityUtil.isAdmin();

        // Permite apenas ADMIN e ENFERMEIRO
        if (!(Role.ENFERMEIRO.equals(role) || isAdmin)) {
            throw new AccessDeniedException("Access denied: insufficient permissions to view history");
        }

        Long currentUserId = securityUtil.getUserId();
        if (currentUserId == null) {
            throw new AccessDeniedException("Access denied: unable to determine authenticated user");
        }

        List<AgendamentoDomain> domain = buscaAgendamentoUseCase.buscaAgendamento(
                filter.pacienteId(),
                filter.medicoId(),
                filter.tipoAtendimentoDomain(),
                filter.statusDomain(),
                filter.especialidadeDomain(),
                filter.dataAgendamentoDomain()
        );

        return AgendamentoPresenter.toAgendamentoListDto(domain);
    }

    @MutationMapping
    public AgendamentoDto updateAgendamento(@Argument Long id, @Argument AgendamentoRequestDto request) {
        ensureCanEdit();

        var domain = AgendamentoPresenter.toAgendamentoDomain(request);
        AgendamentoDomain atualizado = atualizaAgendamentoUseCase.atualizarAgendamento(id, domain);
        return AgendamentoPresenter.toAgendamentoDto(atualizado);
    }

    private void ensureCanEdit() {
        Role role = securityUtil.getRole();
        boolean isAdmin = securityUtil.isAdmin();

        if (!(Role.ENFERMEIRO.equals(role) || isAdmin)) {
            throw new AccessDeniedException("Access denied: only admins and doctors can modify history");
        }
    }

    private void ensureAdmin() {
        boolean isAdmin = securityUtil.isAdmin();

        if (!isAdmin) {
            throw new AccessDeniedException("Access denied: only admins can perform this action");
        }
    }
}
