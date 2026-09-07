package com.fiap.pulsecare.agendamento.messaging;

import com.fiap.pulsecare.agendamento.domain.entity.Consulta;
import com.fiap.pulsecare.core.messaging.ConsultaEventoDTO;
import com.fiap.pulsecare.core.messaging.RabbitConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsultaEventPublisher {

	private final RabbitTemplate rabbitTemplate;

	public void publicarConsultaCriada(Consulta consulta) {
		publicar(consulta, ConsultaEventoDTO.TIPO_CRIADA, RabbitConstants.CONSULTA_CRIADA_ROUTING_KEY);
	}

	public void publicarConsultaEditada(Consulta consulta) {
		publicar(consulta, ConsultaEventoDTO.TIPO_EDITADA, RabbitConstants.CONSULTA_EDITADA_ROUTING_KEY);
	}

	private void publicar(Consulta consulta, String tipoEvento, String routingKey) {

		ConsultaEventoDTO evento = new ConsultaEventoDTO(
				consulta.getId(),
				tipoEvento,
				consulta.getPaciente().getId(),
				consulta.getPaciente().getNome(),
				consulta.getMedico().getId(),
				consulta.getMedico().getNome(),
				consulta.getDataHora(),
				consulta.getStatus()
		);

		log.info("Publicando evento {} da consulta {}", tipoEvento, consulta.getId());

		rabbitTemplate.convertAndSend(RabbitConstants.CONSULTA_EXCHANGE, routingKey, evento);
	}

}
