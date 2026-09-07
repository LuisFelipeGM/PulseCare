package com.fiap.pulsecare.notificacao.service;

import com.fiap.pulsecare.core.messaging.ConsultaEventoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Simula o envio do lembrete ao paciente. Sem integração real de e-mail/SMS -
 * o log abaixo é a evidência de que o lembrete "foi enviado", conforme combinado.
 */
@Slf4j
@Service
public class NotificacaoService {

	public void enviarLembrete(ConsultaEventoDTO evento) {
		log.info("Lembrete enviado ao paciente {} (id {}) sobre a consulta {} com Dr(a). {} em {} - status: {} - Tipo de evento: {}",
				evento.pacienteNome(),
				evento.pacienteId(),
				evento.consultaId(),
				evento.medicoNome(),
				evento.dataHora(),
				evento.status(),
				evento.tipoEvento());
	}

}
