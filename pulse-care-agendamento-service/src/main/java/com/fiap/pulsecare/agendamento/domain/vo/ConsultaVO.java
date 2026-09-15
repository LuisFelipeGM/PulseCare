package com.fiap.pulsecare.agendamento.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ConsultaVO {

	@NotNull(message = "Paciente é obrigatório")
	@Schema(description = "ID do paciente", example = "9003")
	private Long pacienteId;

	@NotNull(message = "Médico é obrigatório")
	@Schema(description = "ID do médico", example = "9001")
	private Long medicoId;

	@NotNull(message = "Data e hora são obrigatórias")
	@Future(message = "Data e hora devem estar no futuro")
	@Schema(description = "Data e hora da consulta (deve estar no futuro)", example = "2027-06-15T10:00:00")
	private LocalDateTime dataHora;

	@NotBlank(message = "Status é obrigatório")
	@Schema(description = "Status da consulta", example = "AGENDADA")
	private String status;

}
