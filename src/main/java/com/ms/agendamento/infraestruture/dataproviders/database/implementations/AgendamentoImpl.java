package com.ms.agendamento.infraestruture.dataproviders.database.implementations;

import com.ms.agendamento.application.gateways.Agendamento;
import com.ms.agendamento.domain.Especialidade;
import com.ms.agendamento.domain.StatusAgendamento;
import com.ms.agendamento.domain.TipoAtendimento;
import com.ms.agendamento.domain.model.AgendamentoDomain;
import com.ms.agendamento.infraestruture.dataproviders.database.mappers.AgendamentoEntityMapper;
import com.ms.agendamento.infraestruture.dataproviders.database.repositories.AgendamentoRepository;
import com.ms.agendamento.infraestruture.dataproviders.database.specification.AgendamentoSpecification;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AgendamentoImpl implements Agendamento {

    private final AgendamentoRepository repository;

    public AgendamentoImpl(AgendamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void deleta(AgendamentoDomain agendamentoDomain) {
        var entity = AgendamentoEntityMapper.INSTANCE.toAgendamentoEntity(agendamentoDomain);
        this.repository.delete(entity);
    }

    @Override
    public void salvar(AgendamentoDomain agendamentoDomain) {
        var entity = AgendamentoEntityMapper.INSTANCE.toAgendamentoEntity(agendamentoDomain);
        this.repository.save(entity);
    }

    @Override
    public Optional<AgendamentoDomain> buscarId(Long id) {
        var entity = this.repository.findById(id);
        return entity.map(AgendamentoEntityMapper.INSTANCE::toAgendamentoDomain);
    }

    @Override
    public List<AgendamentoDomain> buscaAgendamento(Long pacienteId, Long medicoId, TipoAtendimento tipo, StatusAgendamento status, Especialidade especialidade, OffsetDateTime dataAgendamento) {
        var spec = AgendamentoSpecification.filtrar(pacienteId, medicoId, tipo, status, especialidade, dataAgendamento);
        return repository.findAll(spec)
                .stream()
                .map(AgendamentoEntityMapper.INSTANCE::toAgendamentoDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AgendamentoDomain> buscarAgendamentoPorPacienteMedicoEData(Long pacienteId, Long medicoId, OffsetDateTime dataAgendamento) {
        var agendamento = this.repository.findByPacienteIdAndMedicoIdAndDataAgendamento(pacienteId, medicoId, dataAgendamento);
        return agendamento.map(AgendamentoEntityMapper.INSTANCE::toAgendamentoDomain);
    }
}
