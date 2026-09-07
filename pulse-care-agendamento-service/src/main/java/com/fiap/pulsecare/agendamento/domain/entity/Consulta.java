package com.fiap.pulsecare.agendamento.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "consulta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consulta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "data_hora", nullable = false)
	private LocalDateTime dataHora;

	@Column(nullable = false, length = 20)
	private String status;

	@Column(name = "data_criacao", nullable = false, updatable = false)
	private LocalDateTime dataCriacao;

	@Column(name = "data_atualizacao")
	private LocalDateTime dataAtualizacao;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "paciente_id", nullable = false)
	private Usuario paciente;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "medico_id", nullable = false)
	private Usuario medico;
}
