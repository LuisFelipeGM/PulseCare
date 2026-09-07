package com.fiap.pulsecare.notificacao.messaging;

import com.fiap.pulsecare.core.messaging.ConsultaEventoDTO;
import com.fiap.pulsecare.core.messaging.RabbitConstants;
import com.fiap.pulsecare.notificacao.service.NotificacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsultaEventListener {

	private final NotificacaoService notificacaoService;

	@RabbitListener(queues = RabbitConstants.NOTIFICACAO_QUEUE)
	public void receber(ConsultaEventoDTO evento) {

		log.info("Evento recebido em {}: consulta {} - tipo {}",
				RabbitConstants.NOTIFICACAO_QUEUE, evento.consultaId(), evento.tipoEvento());

		notificacaoService.enviarLembrete(evento);
	}

}
