package com.ms.agendamento.entrypoints.controllers.presenter;

import com.ms.agendamento.domain.model.AgendamentoDomain;
import com.ms.agendamento.entrypoints.controllers.dtos.AgendamentoDto;
import com.ms.agendamento.entrypoints.controllers.dtos.AgendamentoRequestDto;
import com.ms.agendamento.entrypoints.controllers.mappers.AgendamentoDtoMapper;

import java.util.List;

public class AgendamentoPresenter {

    public static AgendamentoDomain toAgendamentoDomain(AgendamentoRequestDto agendamentoRequestDto) {
        return AgendamentoDtoMapper.INSTANCE.toAgendamentoDomain(agendamentoRequestDto);
    }

    public static List<AgendamentoDto> toAgendamentoListDto(List<AgendamentoDomain> domains) {
        return AgendamentoDtoMapper.INSTANCE.toAgendamentoListDto(domains);
    }

    public static AgendamentoDto toAgendamentoDto(AgendamentoDomain agendamento) {
        return AgendamentoDtoMapper.INSTANCE.toAgendamentoDto(agendamento);
    }
}
