package com.ms.agendamento.entrypoints.controllers.dtos;

public class AgendamentoRequestDto {

    private Long pacienteId;
    private Long medicoId;
    private String dataAgendamento;
    private TipoAtendimentoDto tipoAtendimento;
    private StatusAtendimentoDto status;
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

    public StatusAtendimentoDto getStatus() {
        return status;
    }

    public void setStatus(StatusAtendimentoDto status) {
        this.status = status;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
