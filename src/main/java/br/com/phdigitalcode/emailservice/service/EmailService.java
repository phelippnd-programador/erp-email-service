package br.com.phdigitalcode.emailservice.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import br.com.phdigitalcode.domain.dto.EmailDto;
import br.com.phdigitalcode.emailservice.configs.RabbitMqConfig;
import br.com.phdigitalcode.emailservice.entity.EmailEntity;
import br.com.phdigitalcode.emailservice.enums.StatusEmail;
import br.com.phdigitalcode.emailservice.exceptions.EmailConfiguracaoException;
import br.com.phdigitalcode.emailservice.repository.EmailRepository;

@Service
public class EmailService {
	@Value("${spring.rabbitmq.queueErros}")
	private String queueErros;
	@Autowired
	private Map<String, JavaMailSenderImpl> javaMailSendersMap;
	@Autowired
	private EmailRepository emailRepository;
	@Autowired
	private RabbitMqService rabbitMqService;

	public void send(EmailDto emailDto) {
		
		validaJavaMail(javaMailSendersMap, emailDto);
		javaMailSendersMap.entrySet().stream().filter(key -> key.getKey().compareTo(emailDto.getAlias()) == 0)
				.forEach(javaMailsMap -> {
					EmailEntity emailEntity = converterToEmailEntity(emailDto);
					emailEntity.setEmailFrom(javaMailsMap.getValue().getUsername());
					try {					
						javaMailsMap.getValue().send(converterToSimpleMailMessage(emailDto));
						emailEntity.setStatus(StatusEmail.ENVIADO);
						emailEntity.setEmailMenssageRetorno("SUCESSO");
					} catch (Exception e) {
						emailEntity.setStatus(StatusEmail.ERRO);
						emailEntity.setEmailMenssageRetorno(e.getMessage());
					}
					finally {
						emailRepository.saveAndFlush(emailEntity);
					}
						
				});

	}

	public void validaJavaMail(Map<String, JavaMailSenderImpl> map, EmailDto emailDto) {
		if (map.size() <= 0) {
			rabbitMqService.enviaMsg(queueErros, emailDto);
			throw new EmailConfiguracaoException("Nenhum usuario configurado !");
		}
		if (!map.containsKey(emailDto.getAlias())) {
			rabbitMqService.enviaMsg(queueErros, emailDto);
			throw new EmailConfiguracaoException("Email de envio não encotrado !");
		}
	}

	public EmailEntity converterToEmailEntity(EmailDto emailDto) {
		ModelMapper mapper = new ModelMapper();
		EmailEntity emailEntity = mapper.map(emailDto, EmailEntity.class);
		emailEntity.setSend_date(LocalDateTime.now());
		return emailEntity;
	}

	public SimpleMailMessage converterToSimpleMailMessage(EmailDto dto) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setText(dto.getTexto());
		mailMessage.setTo(dto.getEmailTo());
		mailMessage.setSubject(dto.getSubject());
		mailMessage.setSentDate(new Date());
		return mailMessage;
	}
}
