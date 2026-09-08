package com.fiap.pulsecare.historico.configuration;

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
	public Queue historicoQueue() {
		return new Queue(RabbitConstants.HISTORICO_QUEUE, true);
	}

	@Bean
	public Binding historicoBinding(Queue historicoQueue, TopicExchange consultaExchange) {
		return BindingBuilder.bind(historicoQueue)
				.to(consultaExchange)
				.with(RabbitConstants.CONSULTA_ROUTING_KEY_PATTERN);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new JacksonJsonMessageConverter();
	}

}
