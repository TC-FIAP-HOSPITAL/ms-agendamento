package com.ms.agendamento.infraestruture.dataproviders.database.mappers;

import com.ms.agendamento.domain.model.AgendamentoDomain;
import com.ms.agendamento.infraestruture.dataproviders.database.entities.AgendamentoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AgendamentoEntityMapper {

    AgendamentoEntityMapper INSTANCE = Mappers.getMapper(AgendamentoEntityMapper.class);

    AgendamentoDomain toAgendamentoDomain(AgendamentoEntity agendamentoEntity);

    AgendamentoEntity toAgendamentoEntity(AgendamentoDomain agendamentoDomain);
}
