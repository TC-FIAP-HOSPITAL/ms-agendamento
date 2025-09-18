package com.ms.agendamento.entrypoints.controllers.mappers;

import com.ms.agendamento.domain.model.AgendamentoDomain;
import com.ms.agendamentoDomain.gen.model.AgendamentoDto;
import com.ms.agendamentoDomain.gen.model.AgendamentoRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

//@Mapper(componentModel = "spring",
//        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AgendamentoDtoMapper {

//    AgendamentoDtoMapper INSTANCE = Mappers.getMapper(AgendamentoDtoMapper.class);
//
//    AgendamentoDomain toAgendamentoDomain(AgendamentoRequestDto domain);
//
//    AgendamentoDto toAgendamentoDto(AgendamentoDomain domain);
//
//    List<AgendamentoDto> toAgendamentoListDto(List<AgendamentoDomain> agendamentoList);
}
