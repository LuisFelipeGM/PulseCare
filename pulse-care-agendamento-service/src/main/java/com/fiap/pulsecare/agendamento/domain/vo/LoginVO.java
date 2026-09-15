package com.fiap.pulsecare.agendamento.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
public class LoginVO {

	@NotBlank(message = "Email é obrigatório")
	@Schema(description = "Email cadastrado do usuário", example = "medico.seed@pulsecare.com")
	private String email;

	@NotBlank(message = "Senha é obrigatória")
	@Schema(description = "Senha do usuário", example = "Senha123!")
	private String senha;

}
