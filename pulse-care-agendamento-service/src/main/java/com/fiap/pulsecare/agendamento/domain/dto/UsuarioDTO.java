package com.fiap.pulsecare.agendamento.domain.dto;

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
public class UsuarioDTO {

	private Long id;

	private String nome;

	private String email;

	private TipoUsuarioDTO tipoUsuario;

	private LocalDateTime dataCriacao;

	public UsuarioDTO(Long id, String nome, String email, Long tipoUsuarioId, String tipoUsuarioDescricao, LocalDateTime dataCriacao) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.tipoUsuario = new TipoUsuarioDTO(tipoUsuarioId, tipoUsuarioDescricao);
		this.dataCriacao = dataCriacao;
	}

}
