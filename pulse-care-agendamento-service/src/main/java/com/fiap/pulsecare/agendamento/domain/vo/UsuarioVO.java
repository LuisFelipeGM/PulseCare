package com.fiap.pulsecare.agendamento.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
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
	@Schema(description = "Nome completo do usuário", example = "João da Silva")
	private String nome;

	@NotNull(message = "Email é obrigatório")
	@Email(message = "Email deve ser válido")
	@Size(max = 255, message = "O email deve ter no máximo 255 caracteres")
	@Schema(description = "Email do usuário (único)", example = "joao.silva@pulsecare.com")
	private String email;

	@NotNull(message = "Senha é obrigatória")
	@Size(min = 8, max = 255, message = "A senha deve conter entre 8 e 255 caracteres")
	@Schema(description = "Senha do usuário (mín. 8 caracteres, com maiúscula, minúscula, número e caractere especial)", example = "SenhaForte123!")
	private String senha;

	@NotNull(message = "Tipo de usuário é obrigatório")
	@Schema(description = "ID do tipo de usuário (1=MEDICO, 2=ENFERMEIRO, 3=PACIENTE)", example = "3")
	private Long tipoUsuarioId;

}
