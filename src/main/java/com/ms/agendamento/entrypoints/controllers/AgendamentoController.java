package com.ms.agendamento.entrypoints.controllers;

import com.ms.agendamento.application.usecase.AtualizaAgendamentoUseCase;
import com.ms.agendamento.application.usecase.BuscaAgendamentoUseCase;
import com.ms.agendamento.application.usecase.DeletaAgendamentoUseCase;
import com.ms.agendamento.application.usecase.InserirAgendamentoUseCase;
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/agendamento")
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


    //TODO: No graphql o nome do parametro deve ser request, se não ele não entende que é um request

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

        filter = Optional.ofNullable(filter).orElse(new AgendamentoFilter());

        TipoAtendimento tipo = Optional.ofNullable(filter.getTipoAtendimento())
                .map(t -> TipoAtendimento.valueOf(t.name()))
                .orElse(null);

        StatusAgendamento st = Optional.ofNullable(filter.getStatus())
                .map(s -> StatusAgendamento.valueOf(s.name()))
                .orElse(null);

        List<AgendamentoDomain> domain = buscaAgendamentoUseCase.buscaAgendamento(filter.getPacienteId(),
                filter.getMedicoId(), tipo, st, filter.getDataAgendamento());

        return AgendamentoPresenter.toAgendamentoListDto(domain);
    }

    @MutationMapping
    public AgendamentoDto updateAgendamento(@Argument Long id, @Argument AgendamentoRequestDto request) {
        var domain = AgendamentoPresenter.toAgendamentoDomain(request);
        AgendamentoDomain atualizado = atualizaAgendamentoUseCase.atualizarAgendamento(id, domain);
        return AgendamentoPresenter.toAgendamentoDto(atualizado);
    }
}
