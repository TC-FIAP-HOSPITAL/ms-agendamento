package com.ms.agendamento.infraestruture.dataproviders.client.dto;

public record UsuarioDto(
        String id,
        String name,
        String email,
        String username,
        String role
) { }