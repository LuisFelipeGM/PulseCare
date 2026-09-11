package com.fiap.pulsecare.agendamento.domain.vo;

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
	private String email;

	@NotBlank(message = "Senha é obrigatória")
	private String senha;

}
