package com.ms.agendamento.entrypoints.controllers.mappers;

import com.ms.agendamento.domain.model.AgendamentoDomain;

import com.ms.agendamento.entrypoints.controllers.dtos.AgendamentoDto;
import com.ms.agendamento.entrypoints.controllers.dtos.AgendamentoRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AgendamentoDtoMapper {

    AgendamentoDtoMapper INSTANCE = Mappers.getMapper(AgendamentoDtoMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    AgendamentoDomain toAgendamentoDomain(AgendamentoRequestDto domain);

    AgendamentoDto toAgendamentoDto(AgendamentoDomain domain);

    List<AgendamentoDto> toAgendamentoListDto(List<AgendamentoDomain> agendamentoList);

    // ======== MÉTODOS PARA CONVERSÃO OFFSETDATETIME ↔ STRING ========
    default String map(OffsetDateTime dateTime) {
        return dateTime != null ? dateTime.toString() : null;
    }

    default OffsetDateTime map(String dateTime) {
        return dateTime != null ? OffsetDateTime.parse(dateTime) : null;
    }
}
