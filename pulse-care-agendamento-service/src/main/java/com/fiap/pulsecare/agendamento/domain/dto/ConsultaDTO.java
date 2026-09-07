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
public class ConsultaDTO {

	private Long id;

	private Long pacienteId;

	private String pacienteNome;

	private Long medicoId;

	private String medicoNome;

	private LocalDateTime dataHora;

	private String status;

	private LocalDateTime dataCriacao;

	private LocalDateTime dataAtualizacao;

}
