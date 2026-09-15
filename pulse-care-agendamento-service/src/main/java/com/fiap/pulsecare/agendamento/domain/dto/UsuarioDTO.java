package com.fiap.pulsecare.agendamento.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Dados de um usuário cadastrado")
public class UsuarioDTO {

	@Schema(description = "Identificador do usuário", example = "9001")
	private Long id;

	@Schema(description = "Nome completo do usuário", example = "João da Silva")
	private String nome;

	@Schema(description = "Email do usuário", example = "joao.silva@pulsecare.com")
	private String email;

	@Schema(description = "Tipo de usuário (papel/role)")
	private TipoUsuarioDTO tipoUsuario;

	@Schema(description = "Data em que o usuário foi cadastrado", example = "2026-09-14T10:30:00")
	private LocalDateTime dataCriacao;

	public UsuarioDTO(Long id, String nome, String email, Long tipoUsuarioId, String tipoUsuarioDescricao, LocalDateTime dataCriacao) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.tipoUsuario = new TipoUsuarioDTO(tipoUsuarioId, tipoUsuarioDescricao);
		this.dataCriacao = dataCriacao;
	}

}
