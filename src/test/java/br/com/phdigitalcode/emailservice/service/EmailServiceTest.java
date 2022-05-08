package br.com.phdigitalcode.emailservice.service;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import br.com.phdigitalcode.domain.dto.EmailDto;

class EmailServiceTest {
	@InjectMocks
	private EmailService emailService;
	private EmailDto emailDto = new EmailDto();
	@Mock
	private Map<String, JavaMailSenderImpl> javaMailSendersMap;
	@Mock
	private JavaMailSenderImpl javaMailSenderImpl;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}


}
