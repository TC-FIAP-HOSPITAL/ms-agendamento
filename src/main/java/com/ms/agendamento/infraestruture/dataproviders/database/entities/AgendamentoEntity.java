package com.ms.agendamento.infraestruture.dataproviders.database.entities;

import com.ms.agendamento.domain.StatusAgendamento;
import com.ms.agendamento.domain.TipoAtendimento;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "TB_AGENDAMENTO")
public class AgendamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pacienteId;

    private Long medicoId;

    private LocalDateTime dataAgendamento;

    @Enumerated(EnumType.STRING)
    private TipoAtendimento tipoAtendimento;

    @Enumerated(EnumType.STRING)
    private StatusAgendamento status;

    private String observacoes;

    @Column(nullable = false, updatable = false)
    private Date dataCriacao;

    @Column(nullable = false, updatable = false)
    private Date dataAtualizacao;

}
