package com.ms.agendamento.infraestruture.dataproviders.client;

import com.ms.agendamento.infraestruture.dataproviders.client.dto.UsuarioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "usuario-service", url = "${usuario.service.url}")
public interface UsuarioFeignClient {

    @GetMapping("v1/users/{id}")
    UsuarioDto buscaUsuarioID(@PathVariable("id") Long id,
                              @RequestHeader("Authorization") String bearerToken);
}
