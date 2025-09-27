package com.ms.agendamento.entrypoints.controllers.dtos;


public record AgendamentoDto (
     Long id,
     Long pacienteId,
     Long medicoId,
     String dataAgendamento,
     TipoAtendimentoDto tipoAtendimento,
     StatusAtendimentoDto status,
     EspecialidadeDto especialidade,
     String motivo,
     String observacoes,
     String dataCriacao
){}
