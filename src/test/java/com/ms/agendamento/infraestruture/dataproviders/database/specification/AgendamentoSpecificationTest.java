package com.ms.agendamento.infraestruture.dataproviders.database.specification;

import com.ms.agendamento.domain.StatusAgendamento;
import com.ms.agendamento.domain.TipoAtendimento;
import com.ms.agendamento.infraestruture.dataproviders.database.entities.AgendamentoEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AgendamentoSpecificationTest {

    @Test
    void testFiltrarComTodosParametros(){
        Specification<AgendamentoEntity> specification = AgendamentoSpecification.filtrar(
                1L, 1L, TipoAtendimento.CONSULTA, StatusAgendamento.AGENDADO, "2025-09-20");

        assertNotNull(specification);
    }

    @Test
    void testFiltrarApenasComPacientId(){
        Specification<AgendamentoEntity> specification = AgendamentoSpecification.filtrar(
                1L, null, null, null, null);

        assertNotNull(specification);
    }

    @Test
    void testFiltrarApenasComMedicoId(){
        Specification<AgendamentoEntity> specification = AgendamentoSpecification.filtrar(
                null, 1L, null, null, null);

        assertNotNull(specification);
    }

    @Test
    void testFiltrarApenasComTipoAtendimento(){
        Specification<AgendamentoEntity> specification = AgendamentoSpecification.filtrar(
                null, null, TipoAtendimento.CONSULTA, null, null);

        assertNotNull(specification);
    }

    @Test
    void testFiltrarApenasComStatusAtendimento(){
        Specification<AgendamentoEntity> specification = AgendamentoSpecification.filtrar(
                null, null, null, StatusAgendamento.AGENDADO, null);

        assertNotNull(specification);
    }

    @Test
    void testFiltrarApenasComDataAgendamento(){
        Specification<AgendamentoEntity> specification = AgendamentoSpecification.filtrar(
                null, null, null, null, "2025-09-20");

        assertNotNull(specification);
    }
}