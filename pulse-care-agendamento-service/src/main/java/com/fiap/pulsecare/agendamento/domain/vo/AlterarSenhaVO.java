package com.fiap.pulsecare.agendamento.domain.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class AlterarSenhaVO {

	@NotBlank(message = "Senha atual é obrigatória")
	@Size(min = 8, max = 255, message = "A senha atual deve conter entre 8 e 255 caracteres")
	private String senhaAtual;

	@NotBlank(message = "Nova senha é obrigatória")
	@Size(min = 8, max = 255, message = "A nova senha deve conter entre 8 e 255 caracteres")
	private String novaSenha;

	@NotBlank(message = "Confirmação de senha é obrigatória")
	@Size(min = 8, max = 255, message = "A confirmação de senha deve conter entre 8 e 255 caracteres")
	private String confirmacaoSenha;

}
