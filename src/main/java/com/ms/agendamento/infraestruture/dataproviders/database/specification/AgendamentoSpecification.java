package com.ms.agendamento.infraestruture.dataproviders.database.specification;

import com.ms.agendamento.domain.Especialidade;
import com.ms.agendamento.domain.StatusAgendamento;
import com.ms.agendamento.domain.TipoAtendimento;
import com.ms.agendamento.infraestruture.dataproviders.database.entities.AgendamentoEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class AgendamentoSpecification {

    public static Specification<AgendamentoEntity> filtrar(Long pacienteId, Long medicoId, TipoAtendimento tipo, StatusAgendamento status, Especialidade especialidade, OffsetDateTime dataAgendamento){
        return (root, query, cb)-> {
            List<Predicate> predicates = new ArrayList<>();

            if(pacienteId != null){
                predicates.add(cb.equal(root.get("pacienteId"), pacienteId));
            }

            if(medicoId != null){
                predicates.add(cb.equal(root.get("medicoId"), medicoId));
            }

            if(tipo != null){
                predicates.add(cb.equal(root.get("tipoAtendimento"), tipo));
            }

            if(status != null){
                predicates.add(cb.equal(root.get("status"), status));
            }

            if(especialidade != null){
                predicates.add(cb.equal(root.get("especialidade"), especialidade));
            }

            if(dataAgendamento != null){
                predicates.add(cb.equal(root.get("dataAgendamento"), dataAgendamento));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
