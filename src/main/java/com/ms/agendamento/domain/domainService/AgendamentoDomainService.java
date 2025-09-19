package com.ms.agendamento.domain.domainService;

import com.ms.agendamento.domain.model.AgendamentoDomain;
import org.springframework.stereotype.Service;


@Service
public interface AgendamentoDomainService {

    void checarExistenciaAgendamento(Long idPaciente, Long idMedico, String dataAgendamento);

    AgendamentoDomain findByIdAgendamento(Long idAgendamento);
}
