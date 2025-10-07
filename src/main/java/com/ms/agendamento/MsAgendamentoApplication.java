package com.ms.agendamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.ms.agendamento.infraestruture.dataproviders.client")

public class MsAgendamentoApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsAgendamentoApplication.class, args);
	}
}
