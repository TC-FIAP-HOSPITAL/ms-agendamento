package com.ms.agendamento.infraestruture.dataproviders.client;

import com.ms.agendamento.application.exceptions.UsuarioNaoEncontradoException;
import com.ms.agendamento.application.gateways.UsuarioClient;
import com.ms.agendamento.infraestruture.dataproviders.client.dto.UsuarioDto;
import feign.FeignException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioClientImpl implements UsuarioClient {

    private final UsuarioFeignClient usuarioFeignClient;

    public UsuarioClientImpl(UsuarioFeignClient usuarioFeignClient) {
        this.usuarioFeignClient = usuarioFeignClient;
    }

    @Override
    public UsuarioDto checaExistenciaUsuario(Long id, String token) {
        try {
            return usuarioFeignClient.buscaUsuarioID(id, token);
        } catch (FeignException.NotFound e) {
            throw new UsuarioNaoEncontradoException(String.format("Usuário %s não encontrado", id));
        }
    }
}
