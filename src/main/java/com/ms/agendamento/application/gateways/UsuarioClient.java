package com.ms.agendamento.application.gateways;

import com.ms.agendamento.infraestruture.dataproviders.client.dto.UsuarioDto;

public interface UsuarioClient {

    UsuarioDto checaExistenciaUsuario(Long id, String token);
}
