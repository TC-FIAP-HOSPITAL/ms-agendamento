package com.ms.agendamento.mocks;

import com.ms.agendamento.domain.StatusAgendamento;
import com.ms.agendamento.domain.TipoAtendimento;
import com.ms.agendamento.domain.model.AgendamentoDomain;
import com.ms.agendamento.entrypoints.controllers.dtos.AgendamentoRequestDto;
import com.ms.agendamento.entrypoints.controllers.dtos.EspecialidadeDto;
import com.ms.agendamento.entrypoints.controllers.dtos.StatusAtendimentoDto;
import com.ms.agendamento.entrypoints.controllers.dtos.TipoAtendimentoDto;
import com.ms.agendamento.infraestruture.dataproviders.database.entities.AgendamentoEntity;


import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

public class AgendamentoMock {

    public static AgendamentoDomain getAgendamentoDomain(){
        AgendamentoDomain agendamentoDomain = new AgendamentoDomain();
        agendamentoDomain.setId(1L);
        agendamentoDomain.setPacienteId(1L);
        agendamentoDomain.setMedicoId(1L);
        agendamentoDomain.setDataAgendamento(OffsetDateTime.parse("2025-09-20T00:00:00Z"));
        agendamentoDomain.setTipoAtendimento(TipoAtendimento.CONSULTA);
        agendamentoDomain.setStatus(StatusAgendamento.AGENDADO);
        agendamentoDomain.setObservacoes("Paciente necessita traver a receita anterior para a proxima consulta");
        agendamentoDomain.setDataCriacao(new Date());
        return agendamentoDomain;
    }

    public static AgendamentoRequestDto getAgendamentoRequestDto(){
        AgendamentoRequestDto agendamentoRequestDto = new AgendamentoRequestDto(
                1L,
                1L,
                "2025-09-19",
                TipoAtendimentoDto.CONSULTA,
                EspecialidadeDto.CARDIOLOGIA,
                StatusAtendimentoDto.AGENDADO,
                "Febre",
                "Paciente necessita trazer a receita anterior para a próxima consulta"
        );
        return agendamentoRequestDto;
    }

    public static AgendamentoEntity getAgendamentoEntity(){
        AgendamentoEntity agendamentoEntity = new AgendamentoEntity();
        agendamentoEntity.setId(1L);
        agendamentoEntity.setPacienteId(1L);
        agendamentoEntity.setMedicoId(1L);
        agendamentoEntity.setDataAgendamento(OffsetDateTime.parse("2025-09-20T00:00:00Z"));
        agendamentoEntity.setTipoAtendimento(TipoAtendimento.CONSULTA);
        agendamentoEntity.setStatus(StatusAgendamento.AGENDADO);
        agendamentoEntity.setObservacoes("Paciente necessita traver a receita anterior para a proxima consulta");
        agendamentoEntity.setDataCriacao(new Date());
        return agendamentoEntity;
    }

    public static List<AgendamentoEntity> getAgendamentoEntityList(){
        return List.of(getAgendamentoEntity());
    }

    public static List<AgendamentoDomain> getAgendamentoDomainList(){
        return List.of(getAgendamentoDomain());
    }
}
