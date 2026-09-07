package com.fiap.pulsecare.agendamento.domain.vo;

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
	private Long pacienteId;

	@NotNull(message = "Médico é obrigatório")
	private Long medicoId;

	@NotNull(message = "Data e hora são obrigatórias")
	@Future(message = "Data e hora devem estar no futuro")
	private LocalDateTime dataHora;

	@NotBlank(message = "Status é obrigatório")
	private String status;

}
