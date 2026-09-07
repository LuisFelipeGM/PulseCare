package com.fiap.pulsecare.agendamento.domain.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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
public class UsuarioVO {

	@NotNull(message = "Nome é obrigatório")
	@Size(min = 2, max = 255, message = "O nome deve conter entre 2 e 255 caracteres")
	private String nome;

	@NotNull(message = "Email é obrigatório")
	@Email(message = "Email deve ser válido")
	@Size(max = 255, message = "O email deve ter no máximo 255 caracteres")
	private String email;

	@NotNull(message = "Senha é obrigatória")
	@Size(min = 8, max = 255, message = "A senha deve conter entre 8 e 255 caracteres")
	private String senha;

	@NotNull(message = "Tipo de usuário é obrigatório")
	private Long tipoUsuarioId;

}
