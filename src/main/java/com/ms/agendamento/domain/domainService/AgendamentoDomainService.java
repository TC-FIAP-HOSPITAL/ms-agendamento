package com.ms.agendamento.domain.domainService;

import com.ms.agendamento.domain.model.AgendamentoDomain;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface AgendamentoDomainService {

    void checarExistenciaAgendamento(Long idPaciente, Long idMedico, LocalDateTime dataAgendamento);

    AgendamentoDomain findByIdAgendamento(Long idAgendamento);
}
