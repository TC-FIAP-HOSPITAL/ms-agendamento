package com.ms.agendamento.infraestruture.dataproviders.database.mappers;

import com.ms.agendamento.domain.model.AgendamentoDomain;
import com.ms.agendamento.infraestruture.dataproviders.database.entities.AgendamentoEntity;
import com.ms.agendamento.mocks.AgendamentoMock;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AgendamentoEntityMapperTest {

    private final AgendamentoEntityMapper mapper = AgendamentoEntityMapper.INSTANCE;

    @Test
    void toAgendamentoDomain_shouldMapEntityFields() {
        AgendamentoEntity entity = AgendamentoMock.getAgendamentoEntity();

        AgendamentoDomain domain = mapper.toAgendamentoDomain(entity);

        assertThat(domain.getId()).isEqualTo(entity.getId());
        assertThat(domain.getPacienteId()).isEqualTo(entity.getPacienteId());
        assertThat(domain.getMedicoId()).isEqualTo(entity.getMedicoId());
        assertThat(domain.getDataAgendamento()).isEqualTo(entity.getDataAgendamento());
        assertThat(domain.getEspecialidade()).isEqualTo(entity.getEspecialidade());
    }

    @Test
    void toAgendamentoEntity_shouldMapDomainFields() {
        AgendamentoDomain domain = AgendamentoMock.getAgendamentoDomain();

        AgendamentoEntity entity = mapper.toAgendamentoEntity(domain);

        assertThat(entity.getPacienteId()).isEqualTo(domain.getPacienteId());
        assertThat(entity.getMedicoId()).isEqualTo(domain.getMedicoId());
        assertThat(entity.getDataAgendamento()).isEqualTo(domain.getDataAgendamento());
        assertThat(entity.getEspecialidade()).isEqualTo(domain.getEspecialidade());
    }
}

