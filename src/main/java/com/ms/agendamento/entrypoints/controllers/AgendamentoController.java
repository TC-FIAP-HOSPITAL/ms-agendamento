package com.ms.agendamento.entrypoints.controllers;

import com.ms.agendamentoDomain.AgendamentoApi;
import com.ms.agendamentoDomain.gen.model.AgendamentoDto;
import com.ms.agendamentoDomain.gen.model.AgendamentoRequestDto;
import com.ms.agendamentoDomain.gen.model.StatusDto;
import com.ms.agendamentoDomain.gen.model.TipoAtendimentoDto;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.util.List;

public class AgendamentoController implements AgendamentoApi {

    @Override
    public ResponseEntity<AgendamentoDto> _createAgendamento(AgendamentoRequestDto agendamentoRequestDto) {
        return null;
    }

    @Override
    public ResponseEntity<Void> _deleteAgendamento(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<AgendamentoDto>> _findAgendamentos(Long pacienteId, Long medicoId, StatusDto status, TipoAtendimentoDto tipoAtendimento, OffsetDateTime dataAgendamento) {
        return null;
    }

    @Override
    public ResponseEntity<AgendamentoDto> _updateAgendamento(Long id, AgendamentoRequestDto agendamentoRequestDto) {
        return null;
    }
}
