package com.ms.agendamento.entrypoints.controllers.presenter;

import com.ms.agendamento.domain.model.AgendamentoDomain;
import com.ms.agendamento.entrypoints.controllers.mappers.AgendamentoDtoMapper;
import com.ms.agendamentoDomain.gen.model.AgendamentoDto;
import com.ms.agendamentoDomain.gen.model.AgendamentoRequestDto;

import java.util.ArrayList;
import java.util.List;

public class AgendamentoPresenter {

    public static AgendamentoDomain toAgendamentoDomain(AgendamentoRequestDto agendamentoRequestDto) {
//        return AgendamentoDtoMapper.INSTANCE.toAgendamentoDomain(agendamentoRequestDto);
        return new AgendamentoDomain();
    }

    public static List<AgendamentoDto> toAgendamentoListDto(List<AgendamentoDomain> domains) {
//        return AgendamentoDtoMapper.INSTANCE.toAgendamentoListDto(domains);
        return new ArrayList<AgendamentoDto>();
    }
}
