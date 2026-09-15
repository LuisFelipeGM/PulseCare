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
public class ConsultaUpdateVO {

	@NotNull(message = "Data e hora são obrigatórias")
	@Future(message = "Data e hora devem estar no futuro")
	@Schema(description = "Nova data e hora da consulta (deve estar no futuro)", example = "2027-06-20T14:30:00")
	private LocalDateTime dataHora;

	@NotBlank(message = "Status é obrigatório")
	@Schema(description = "Novo status da consulta", example = "CONFIRMADA")
	private String status;

}
