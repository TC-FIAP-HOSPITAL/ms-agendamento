package com.ms.agendamento.infraestruture.dataproviders.database.implementations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import com.ms.agendamento.domain.Especialidade;
import com.ms.agendamento.domain.StatusAgendamento;
import com.ms.agendamento.domain.TipoAtendimento;
import com.ms.agendamento.domain.model.AgendamentoDomain;
import com.ms.agendamento.infraestruture.dataproviders.database.entities.AgendamentoEntity;
import com.ms.agendamento.infraestruture.dataproviders.database.repositories.AgendamentoRepository;
import com.ms.agendamento.mocks.AgendamentoMock;

@ExtendWith(MockitoExtension.class)
class AgendamentoImplTest {

    @InjectMocks
    private AgendamentoImpl agendamento;

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Test
    void deletaAgendamento_sucesso() {
        AgendamentoDomain domain = AgendamentoMock.getAgendamentoDomain();

        agendamento.deleta(domain);

        verify(agendamentoRepository).delete(any(AgendamentoEntity.class));
    }

    @Test
    void salvarAgendamento_sucesso() {
        AgendamentoDomain domain = AgendamentoMock.getAgendamentoDomain();
        AgendamentoEntity entity = AgendamentoMock.getAgendamentoEntity();

        when(agendamentoRepository.save(any(AgendamentoEntity.class))).thenReturn(entity);

        Optional<AgendamentoDomain> resultado = Optional.ofNullable(agendamento.salvar(domain));

        assertThat(resultado).isPresent();
        verify(agendamentoRepository).save(any(AgendamentoEntity.class));
    }

    @Test
    void buscarAgendamentoPorId_sucesso() {
        AgendamentoEntity entity = AgendamentoMock.getAgendamentoEntity();
        when(agendamentoRepository.findById(entity.getId())).thenReturn(Optional.of(entity));

        Optional<AgendamentoDomain> resultado = agendamento.buscarId(entity.getId());

        assertThat(resultado).isPresent();
        verify(agendamentoRepository).findById(entity.getId());
    }

    @Test
    void buscaAgendamento_deveAplicarFiltro() {
        List<AgendamentoEntity> entities = AgendamentoMock.getAgendamentoEntityList();
        when(agendamentoRepository.findAll(any(Specification.class))).thenReturn(entities);

        List<AgendamentoDomain> resultado = agendamento.buscaAgendamento(1L, 2L, TipoAtendimento.CONSULTA,
                StatusAgendamento.AGENDADO, Especialidade.CARDIOLOGIA, OffsetDateTime.parse("2025-09-20T00:00:00Z"));

        assertThat(resultado).hasSameSizeAs(entities);
        verify(agendamentoRepository).findAll(any(Specification.class));
    }

    @Test
    void buscarAgendamentoPorPacienteMedicoEData() {
        AgendamentoEntity entity = AgendamentoMock.getAgendamentoEntity();
        when(agendamentoRepository.findByPacienteIdAndMedicoIdAndDataAgendamento(
                entity.getPacienteId(), entity.getMedicoId(), entity.getDataAgendamento()))
                .thenReturn(Optional.of(entity));

        Optional<AgendamentoDomain> resultado = agendamento.buscarAgendamentoPorPacienteMedicoEData(
                entity.getPacienteId(), entity.getMedicoId(), entity.getDataAgendamento());

        assertThat(resultado).isPresent();
        verify(agendamentoRepository).findByPacienteIdAndMedicoIdAndDataAgendamento(
                entity.getPacienteId(), entity.getMedicoId(), entity.getDataAgendamento());
    }
}