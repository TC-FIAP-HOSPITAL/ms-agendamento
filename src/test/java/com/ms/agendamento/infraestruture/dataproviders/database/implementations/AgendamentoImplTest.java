package com.ms.agendamento.infraestruture.dataproviders.database.implementations;

import com.ms.agendamento.domain.model.AgendamentoDomain;
import com.ms.agendamento.infraestruture.dataproviders.database.entities.AgendamentoEntity;
import com.ms.agendamento.infraestruture.dataproviders.database.repositories.AgendamentoRepository;
import com.ms.agendamento.mocks.AgendamentoMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        verify(agendamentoRepository, times(1)).delete(any(AgendamentoEntity.class));
    }

    @Test
    void atualizaAgendamento_sucesso() {
        AgendamentoDomain domain = AgendamentoMock.getAgendamentoDomain();

        agendamento.salvar(domain);
        verify(agendamentoRepository, times(1)).save(any(AgendamentoEntity.class));
    }

    @Test
    void buscaAgendamentoPorId_sucesso(){
        AgendamentoDomain domain = AgendamentoMock.getAgendamentoDomain();
        AgendamentoEntity entity = AgendamentoMock.getAgendamentoEntity();

        when(agendamentoRepository.findById(domain.getId())).thenReturn(Optional.of(entity));

        Optional<AgendamentoDomain> result = agendamento.buscarId(domain.getId());

        assertTrue(result.isPresent());
        assertEquals(domain.getId(), result.get().getId());
        verify(agendamentoRepository, times(1)).findById(domain.getId());
    }

    @Test
    void buscarTodosAgendamento(){
        List<AgendamentoEntity> entities = List.of(AgendamentoMock.getAgendamentoEntity());
        when(agendamentoRepository.findAll(any(Specification.class))).thenReturn(entities);

        agendamento.buscaAgendamento(null, null, null, null, null, null);
        verify(agendamentoRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    void deveCriarAgendamentoComSucesso() {
        AgendamentoDomain domain = AgendamentoMock.getAgendamentoDomain();

        agendamento.salvar(domain);
        verify(agendamentoRepository, times(1)).save(any(AgendamentoEntity.class));
    }
}