package com.ms.agendamento.domain;

public enum Especialidade {

    CARDIOLOGIA("Cardiologia"),
    DERMATOLOGIA("Dermatologia"),
    ENDOCRINOLOGIA("Endocrinologia"),
    GASTROENTEROLOGIA("Gastroenterologia"),
    GERIATRIA("Geriatria"),
    GINECOLOGIA("Ginecologia"),
    HEMATOLOGIA("Hematologia"),
    INFECTOLOGIA("Infectologia"),
    NEFROLOGIA("Nefrologia"),
    NEUROLOGIA("Neurologia"),
    OBSTETRICIA("Obstetrícia"),
    OFTALMOLOGIA("Oftalmologia"),
    ONCOLOGIA("Oncologia"),
    ORTOPEDIA("Ortopedia"),
    OTORRINOLARINGOLOGIA("Otorrinolaringologia"),
    PEDIATRIA("Pediatria"),
    PSIQUIATRIA("Psiquiatria"),
    REUMATOLOGIA("Reumatologia"),
    UROLOGIA("Urologia");

    private final String descricao;

    Especialidade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
