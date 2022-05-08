package br.com.phdigitalcode.emailservice.configs;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ErrorHandler;

import br.com.phdigitalcode.emailservice.consumer.CustomErrorStrategy;

@Configuration
public class RabbitMqConfig {
	@Value("${spring.rabbitmq.queue}")
	private String queue;
	@Value("${spring.rabbitmq.queueErros}")
	private String queueErros;
	@Value("${spring.rabbitmq.exchange}")
	private String exchange;
	@Autowired
	private AmqpAdmin amqpAdmin;

	private Queue getQueue(String nomeFila) {
		return new Queue(nomeFila, true, false, false);
	}

	private DirectExchange getExchange() {
		this.exchange = this.exchange.isBlank() ? "email_exchange" : this.exchange;
		return new DirectExchange(exchange);
	}

	private Binding relacionamento(Queue queue, DirectExchange exchange) {
		return new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange.getName(), queue.getName(), null);
	}

	@PostConstruct
	private void adicionar() {
		queue = queue.isBlank() ? "email_fila" : this.queue;
		queueErros = queueErros.isBlank() ? "email_fila_erro" : this.queueErros;
		Queue fila = this.getQueue(queue);
		Queue filaErro = this.getQueue(queueErros);
		DirectExchange directExchange = getExchange();
		Binding relacionamento = relacionamento(fila, directExchange);
		Binding relacionamentoErro = relacionamento(fila, directExchange);
		amqpAdmin.declareQueue(fila);
		amqpAdmin.declareQueue(filaErro);
		amqpAdmin.declareExchange(directExchange);
		amqpAdmin.declareBinding(relacionamento);
		amqpAdmin.declareBinding(relacionamentoErro);

	}

	public RabbitListenerContainerFactory<DirectMessageListenerContainer> rabbitListenerContainerFactory(
			ConnectionFactory connectionFactory) {
		DirectRabbitListenerContainerFactory factory = new DirectRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
		factory.setErrorHandler(errorHandler());
		factory.setPrefetchCount(1);
		return factory;
	}

	@Bean
	public FatalExceptionStrategy customErroStrategy() {
		return new CustomErrorStrategy();
	}

	public ErrorHandler errorHandler() {
		return new ConditionalRejectingErrorHandler(customErroStrategy());
	}

}
