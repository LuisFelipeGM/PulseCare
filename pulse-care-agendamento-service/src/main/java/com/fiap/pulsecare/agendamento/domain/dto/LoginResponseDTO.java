package com.fiap.pulsecare.agendamento.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Resposta do login, contendo o token JWT")
public class LoginResponseDTO {

	@Schema(description = "Token JWT, válido para autenticar requisições em todos os módulos", example = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJtZWRpY28ueC5jb20i...")
	private String token;

}
