package com.ms.agendamento.infraestruture.dataproviders.database.repositories;

import com.ms.agendamento.domain.StatusAgendamento;
import com.ms.agendamento.domain.TipoAtendimento;
import com.ms.agendamento.infraestruture.dataproviders.database.entities.AgendamentoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgendamentoRepository extends MongoRepository<AgendamentoEntity, String> {

    List<AgendamentoEntity> findByPacienteIdAndMedicoIdAndTipoAtendimentoAndStatus(String pacienteId, String medicoId, TipoAtendimento tipo, StatusAgendamento status);

    Optional<AgendamentoEntity> findByPacienteIdAndMedicoIdAndDataAgendamento(String pacienteId, String medicoId, LocalDateTime dataAgendamento);
}
