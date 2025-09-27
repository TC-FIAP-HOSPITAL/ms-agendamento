package com.ms.agendamento.entrypoints.controllers.dtos;

public record AgendamentoRequestDto (
    Long pacienteId,
    Long medicoId,
    String dataAgendamento,
    TipoAtendimentoDto tipoAtendimento,
    EspecialidadeDto especialidade,
    StatusAtendimentoDto status,
    String motivo,
    String observacoes
){}



