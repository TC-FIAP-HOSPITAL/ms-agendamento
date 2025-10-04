package com.ms.agendamento.entrypoints.controllers;

import com.ms.agendamento.application.usecase.AtualizaAgendamentoUseCase;
import com.ms.agendamento.application.usecase.BuscaAgendamentoUseCase;
import com.ms.agendamento.application.usecase.DeletaAgendamentoUseCase;
import com.ms.agendamento.application.usecase.InserirAgendamentoUseCase;
import com.ms.agendamento.domain.Especialidade;
import com.ms.agendamento.domain.StatusAgendamento;
import com.ms.agendamento.domain.TipoAtendimento;
import com.ms.agendamento.domain.model.AgendamentoDomain;
import com.ms.agendamento.entrypoints.controllers.dtos.AgendamentoDto;
import com.ms.agendamento.entrypoints.controllers.dtos.AgendamentoFilter;
import com.ms.agendamento.entrypoints.controllers.dtos.AgendamentoRequestDto;
import com.ms.agendamento.entrypoints.controllers.presenter.AgendamentoPresenter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/v1")
public class AgendamentoController {

    private final InserirAgendamentoUseCase inserirAgendamentoUseCase;
    private final AtualizaAgendamentoUseCase atualizaAgendamentoUseCase;
    private final DeletaAgendamentoUseCase deletaAgendamentoUseCase;
    private final BuscaAgendamentoUseCase buscaAgendamentoUseCase;

    public AgendamentoController(InserirAgendamentoUseCase inserirAgendamentoUseCase,
                                 AtualizaAgendamentoUseCase atualizaAgendamentoUseCase,
                                 DeletaAgendamentoUseCase deletaAgendamentoUseCase,
                                 BuscaAgendamentoUseCase buscaAgendamentoUseCase) {
        this.inserirAgendamentoUseCase = inserirAgendamentoUseCase;
        this.atualizaAgendamentoUseCase = atualizaAgendamentoUseCase;
        this.deletaAgendamentoUseCase = deletaAgendamentoUseCase;
        this.buscaAgendamentoUseCase = buscaAgendamentoUseCase;
    }

    @MutationMapping
    public AgendamentoDto createAgendamento(@Argument AgendamentoRequestDto request) {
        var domain = AgendamentoPresenter.toAgendamentoDomain(request);
        AgendamentoDomain salvo = inserirAgendamentoUseCase.inserirAgendamento(domain);
        return AgendamentoPresenter.toAgendamentoDto(salvo);
    }

    @MutationMapping
    public Boolean deleteAgendamento(@Argument Long id) {
        return deletaAgendamentoUseCase.deletaAgendamento(id);
    }

    @QueryMapping
    public List<AgendamentoDto> findAgendamento(@Argument AgendamentoFilter filter) {

        filter = Objects.requireNonNullElse(filter, AgendamentoFilter.vazio());

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
        var domain = AgendamentoPresenter.toAgendamentoDomain(request);
        AgendamentoDomain atualizado = atualizaAgendamentoUseCase.atualizarAgendamento(id, domain);
        return AgendamentoPresenter.toAgendamentoDto(atualizado);
    }
}
