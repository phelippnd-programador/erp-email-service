package br.com.phdigitalcode.emailservice.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import br.com.phdigitalcode.domain.dto.EmailDto;
import br.com.phdigitalcode.emailservice.service.EmailService;
import br.com.phdigitalcode.emailservice.service.RabbitMqService;

@Component
public class EmailConsumer {
	@Autowired
	private EmailService emailService;
	private RabbitMqService mqService;

	@RabbitListener(queues = { "${spring.rabbitmq.queue}" })
	private void consumer(EmailDto emailDto) {
		emailService.send(emailDto);
	}

}
