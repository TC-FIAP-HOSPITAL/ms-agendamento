package com.ms.agendamento.entrypoints.controllers.dtos;

import com.ms.agendamentoDomain.gen.model.StatusDto;
import com.ms.agendamentoDomain.gen.model.TipoAtendimentoDto;

public class AgendamentoDto {

    private Long id;
    private Long pacienteId;
    private Long medicoId;
    private String dataAgendamento;
    private TipoAtendimentoDto tipoAtendimento;
    private StatusDto status;
    private String observacoes;
    private String dataCriacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Long medicoId) {
        this.medicoId = medicoId;
    }

    public String getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(String dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public TipoAtendimentoDto getTipoAtendimento() {
        return tipoAtendimento;
    }

    public void setTipoAtendimento(TipoAtendimentoDto tipoAtendimento) {
        this.tipoAtendimento = tipoAtendimento;
    }

    public StatusDto getStatus() {
        return status;
    }

    public void setStatus(StatusDto status) {
        this.status = status;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
