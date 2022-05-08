package br.com.phdigitalcode.emailservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.phdigitalcode.domain.dto.EmailDto;
import br.com.phdigitalcode.emailservice.service.EmailService;

@RestController
@RequestMapping("send")
public class EmailController {
	@Autowired
	private EmailService emailService;
	@PostMapping
	public void send(@RequestBody EmailDto emailDto ) {
		emailService.send(emailDto);
	}
	@GetMapping
	public EmailDto get() {
		return new EmailDto();
	}
}
