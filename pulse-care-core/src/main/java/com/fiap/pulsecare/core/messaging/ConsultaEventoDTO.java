package com.fiap.pulsecare.core.messaging;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ConsultaEventoDTO(
		Long consultaId,
		String tipoEvento,
		Long pacienteId,
		String pacienteNome,
		Long medicoId,
		String medicoNome,
		LocalDateTime dataHora,
		String status
) implements Serializable {

	public static final String TIPO_CRIADA = "CRIADA";
	public static final String TIPO_EDITADA = "EDITADA";
}
