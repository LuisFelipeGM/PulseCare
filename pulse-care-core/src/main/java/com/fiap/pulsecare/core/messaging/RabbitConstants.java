package com.fiap.pulsecare.core.messaging;

public final class RabbitConstants {

	private RabbitConstants() {
	}

	public static final String CONSULTA_EXCHANGE = "consulta.exchange";

	public static final String CONSULTA_CRIADA_ROUTING_KEY = "consulta.criada";
	public static final String CONSULTA_EDITADA_ROUTING_KEY = "consulta.editada";
	public static final String CONSULTA_ROUTING_KEY_PATTERN = "consulta.*";

	public static final String NOTIFICACAO_QUEUE = "notificacao.consulta.queue";
	public static final String HISTORICO_QUEUE = "historico.consulta.queue";

}
