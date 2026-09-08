package com.fiap.pulsecare.historico.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "historico_consulta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoricoConsulta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "consulta_id", nullable = false, unique = true)
	private Long consultaId;

	@Column(name = "paciente_id", nullable = false)
	private Long pacienteId;

	@Column(name = "paciente_nome")
	private String pacienteNome;

	@Column(name = "medico_id", nullable = false)
	private Long medicoId;

	@Column(name = "medico_nome")
	private String medicoNome;

	@Column(name = "data_hora", nullable = false)
	private LocalDateTime dataHora;

	@Column(nullable = false, length = 20)
	private String status;

	@Column(name = "data_criacao", nullable = false, insertable = false, updatable = false)
	private LocalDateTime dataCriacao;

	@Column(name = "data_atualizacao")
	private LocalDateTime dataAtualizacao;

}
