package com.ms.agendamento.infraestruture.dataproviders.database.repositories;

import com.ms.agendamento.domain.StatusAgendamento;
import com.ms.agendamento.domain.TipoAtendimento;
import com.ms.agendamento.infraestruture.dataproviders.database.entities.AgendamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity, Long>, JpaSpecificationExecutor<AgendamentoEntity> {

    Optional<AgendamentoEntity> findByPacienteIdAndMedicoIdAndDataAgendamento(Long pacienteId, Long medicoId, String dataAgendamento);
}
