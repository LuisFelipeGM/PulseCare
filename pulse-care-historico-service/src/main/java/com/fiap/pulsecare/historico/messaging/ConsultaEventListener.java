package com.fiap.pulsecare.historico.messaging;

import com.fiap.pulsecare.core.messaging.ConsultaEventoDTO;
import com.fiap.pulsecare.core.messaging.RabbitConstants;
import com.fiap.pulsecare.historico.service.HistoricoConsultaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsultaEventListener {

	private final HistoricoConsultaService historicoConsultaService;

	@RabbitListener(queues = RabbitConstants.HISTORICO_QUEUE)
	public void receber(ConsultaEventoDTO evento) {

		log.info("Evento recebido em {}: consulta {} - tipo {}",
				RabbitConstants.HISTORICO_QUEUE, evento.consultaId(), evento.tipoEvento());

		historicoConsultaService.registrarOuAtualizar(evento);
	}

}
