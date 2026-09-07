package com.fiap.pulsecare.notificacao.configuration;

import com.fiap.pulsecare.core.messaging.RabbitConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	@Bean
	public TopicExchange consultaExchange() {
		return new TopicExchange(RabbitConstants.CONSULTA_EXCHANGE);
	}

	@Bean
	public Queue notificacaoQueue() {
		return new Queue(RabbitConstants.NOTIFICACAO_QUEUE, true);
	}

	@Bean
	public Binding notificacaoBinding(Queue notificacaoQueue, TopicExchange consultaExchange) {
		return BindingBuilder.bind(notificacaoQueue)
				.to(consultaExchange)
				.with(RabbitConstants.CONSULTA_ROUTING_KEY_PATTERN);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new JacksonJsonMessageConverter();
	}

}
