package br.com.phdigitalcode.emailservice.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.phdigitalcode.domain.dto.EmailDto;

@Service
public class RabbitMqService {
	@Autowired
	RabbitTemplate rabbitTemplate;
	public void enviaMsg(String nomeFila,EmailDto mensagem) {
		rabbitTemplate.convertAndSend(nomeFila,mensagem);
	}
	
}
