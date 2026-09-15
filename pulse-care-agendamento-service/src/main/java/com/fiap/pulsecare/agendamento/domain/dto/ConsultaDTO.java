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
@Schema(description = "Dados de uma consulta médica")
public class ConsultaDTO {

	@Schema(description = "Identificador da consulta", example = "9001")
	private Long id;

	@Schema(description = "Identificador do paciente", example = "9003")
	private Long pacienteId;

	@Schema(description = "Nome do paciente", example = "Paciente Teste")
	private String pacienteNome;

	@Schema(description = "Identificador do médico", example = "9001")
	private Long medicoId;

	@Schema(description = "Nome do médico", example = "Medico Teste")
	private String medicoNome;

	@Schema(description = "Data e hora da consulta", example = "2027-06-15T10:00:00")
	private LocalDateTime dataHora;

	@Schema(description = "Status da consulta", example = "AGENDADA")
	private String status;

	@Schema(description = "Data em que a consulta foi cadastrada", example = "2026-09-14T10:30:00")
	private LocalDateTime dataCriacao;

	@Schema(description = "Data da última atualização da consulta", example = "2026-09-14T11:00:00")
	private LocalDateTime dataAtualizacao;

}
