package com.ms.agendamento.infraestruture.dataproviders.database.specification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ms.agendamento.domain.Especialidade;
import com.ms.agendamento.domain.StatusAgendamento;
import com.ms.agendamento.domain.TipoAtendimento;
import com.ms.agendamento.infraestruture.dataproviders.database.entities.AgendamentoEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@ExtendWith(MockitoExtension.class)
class AgendamentoSpecificationTest {

    @Mock
    private Root<AgendamentoEntity> root;

    @Mock
    private CriteriaQuery<?> query;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private Predicate predicate;

    @BeforeEach
    void setUp() {
        lenient().when(root.get(anyString())).thenReturn(null);
        lenient().when(criteriaBuilder.equal(any(), any())).thenReturn(predicate);
        lenient().when(criteriaBuilder.and(any(Predicate[].class))).thenReturn(predicate);
    }

    @Test
    void filtrar_deveAdicionarPredicadosParaParametrosInformados() {
        OffsetDateTime data = OffsetDateTime.parse("2025-09-20T00:00:00Z");

        Predicate result = AgendamentoSpecification.filtrar(1L, 2L, TipoAtendimento.CONSULTA,
                StatusAgendamento.AGENDADO, Especialidade.CARDIOLOGIA, data)
                .toPredicate(root, query, criteriaBuilder);

        assertThat(result).isEqualTo(predicate);
        verify(root, times(6)).get(anyString());
        verify(criteriaBuilder).and(any(Predicate[].class));
    }

    @Test
    void filtrar_semParametrosRetornaAndVazio() {
        Predicate result = AgendamentoSpecification.filtrar(null, null, null, null, null, null)
                .toPredicate(root, query, criteriaBuilder);

        assertThat(result).isEqualTo(predicate);
        verify(root, never()).get(anyString());
        verify(criteriaBuilder, never()).equal(any(), any());
        verify(criteriaBuilder).and(any(Predicate[].class));
    }
}