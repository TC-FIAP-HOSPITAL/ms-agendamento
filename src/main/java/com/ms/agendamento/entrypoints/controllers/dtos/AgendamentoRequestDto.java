package com.ms.agendamento.entrypoints.controllers.dtos;

import com.ms.agendamentoDomain.gen.model.StatusDto;
import com.ms.agendamentoDomain.gen.model.TipoAtendimentoDto;

public class AgendamentoRequestDto {

    private Long pacienteId;
    private Long medicoId;
    private String dataAgendamento;
    private TipoAtendimentoDto tipoAtendimento;
    private StatusDto status;
    private String observacoes;

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
}
