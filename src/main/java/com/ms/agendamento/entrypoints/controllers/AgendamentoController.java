//package com.ms.agendamento.entrypoints.controllers;
//
//import com.ms.agendamento.application.usecase.AtualizaAgendamentoUseCase;
//import com.ms.agendamento.application.usecase.BuscaAgendamentoUseCase;
//import com.ms.agendamento.application.usecase.DeletaAgendamentoUseCase;
//import com.ms.agendamento.application.usecase.InserirAgendamentoUseCase;
//import com.ms.agendamento.domain.StatusAgendamento;
//import com.ms.agendamento.domain.TipoAtendimento;
//import com.ms.agendamento.domain.model.AgendamentoDomain;
//import com.ms.agendamento.entrypoints.controllers.presenter.AgendamentoPresenter;
//import com.ms.agendamentoDomain.AgendamentoApi;
//import com.ms.agendamentoDomain.gen.model.AgendamentoDto;
//import com.ms.agendamentoDomain.gen.model.AgendamentoRequestDto;
//import com.ms.agendamentoDomain.gen.model.StatusDto;
//import com.ms.agendamentoDomain.gen.model.TipoAtendimentoDto;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.time.OffsetDateTime;
//import java.util.List;
//
//@Slf4j
//@Controller
//@RequestMapping("/agendamento")
//public class AgendamentoController implements AgendamentoApi {
//
//    private final InserirAgendamentoUseCase inserirAgendamentoUseCase;
//    private final AtualizaAgendamentoUseCase atualizaAgendamentoUseCase;
//    private final DeletaAgendamentoUseCase deletaAgendamentoUseCase;
//    private final BuscaAgendamentoUseCase buscaAgendamentoUseCase;
//
//    public AgendamentoController(InserirAgendamentoUseCase inserirAgendamentoUseCase,
//                                 AtualizaAgendamentoUseCase atualizaAgendamentoUseCase,
//                                 DeletaAgendamentoUseCase deletaAgendamentoUseCase,
//                                 BuscaAgendamentoUseCase buscaAgendamentoUseCase) {
//        this.inserirAgendamentoUseCase = inserirAgendamentoUseCase;
//        this.atualizaAgendamentoUseCase = atualizaAgendamentoUseCase;
//        this.deletaAgendamentoUseCase = deletaAgendamentoUseCase;
//        this.buscaAgendamentoUseCase = buscaAgendamentoUseCase;
//    }
//
//    @Override
//    public ResponseEntity<AgendamentoDto> _createAgendamento(AgendamentoRequestDto agendamentoRequestDto) {
//        var domain = AgendamentoPresenter.toAgendamentoDomain(agendamentoRequestDto);
//        inserirAgendamentoUseCase.inserirAgendamento(domain);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
//
//    @Override
//    public ResponseEntity<Void> _deleteAgendamento(Long id) {
//        deletaAgendamentoUseCase.deletaAgendamento(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @Override
//    public ResponseEntity<List<AgendamentoDto>> _findAgendamentos(Long pacienteId, Long medicoId, StatusDto status, TipoAtendimentoDto tipoAtendimento, OffsetDateTime dataAgendamento) {
//        return null;
//    }
//
//    @Override
//    public ResponseEntity<List<AgendamentoDto>> _findAgendamentos(Long pacienteId, Long medicoId, StatusDto status, TipoAtendimentoDto tipo, LocalDateTime dataAgendamento) {
//
//        List<AgendamentoDomain> domain = buscaAgendamentoUseCase.buscaAgendamento(pacienteId, medicoId,
//                TipoAtendimento.valueOf(tipo.name()),
//                StatusAgendamento.valueOf(status.name()));
//
//        List<AgendamentoDto> dtos = AgendamentoPresenter.toAgendamentoListDto(domain);
//        return ResponseEntity.ok(dtos);
//    }
//
//    @Override
//    public ResponseEntity<AgendamentoDto> _updateAgendamento(Long id, AgendamentoRequestDto agendamentoRequestDto) {
//        var domain =  AgendamentoPresenter.toAgendamentoDomain(agendamentoRequestDto);
//        atualizaAgendamentoUseCase.atualizarAgendamento(id, domain);
//        return ResponseEntity.noContent().build();
//    }
//}
