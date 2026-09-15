package com.fiap.pulsecare.agendamento.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
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
	@Schema(description = "Senha atual do usuário", example = "Senha123!")
	private String senhaAtual;

	@NotBlank(message = "Nova senha é obrigatória")
	@Size(min = 8, max = 255, message = "A nova senha deve conter entre 8 e 255 caracteres")
	@Schema(description = "Nova senha desejada (mín. 8 caracteres, com maiúscula, minúscula, número e caractere especial)", example = "SenhaNova456!")
	private String novaSenha;

	@NotBlank(message = "Confirmação de senha é obrigatória")
	@Size(min = 8, max = 255, message = "A confirmação de senha deve conter entre 8 e 255 caracteres")
	@Schema(description = "Confirmação da nova senha (deve ser igual a novaSenha)", example = "SenhaNova456!")
	private String confirmacaoSenha;

}
