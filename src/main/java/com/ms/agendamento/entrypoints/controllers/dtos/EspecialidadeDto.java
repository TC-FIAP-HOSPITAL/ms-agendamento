package com.ms.agendamento.entrypoints.controllers.dtos;

public enum EspecialidadeDto {

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

    private String descricao;

    EspecialidadeDto(String descricao) {
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
