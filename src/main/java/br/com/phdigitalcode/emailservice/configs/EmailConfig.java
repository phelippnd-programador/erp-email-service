package br.com.phdigitalcode.emailservice.configs;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import br.com.phdigitalcode.emailservice.configs.params.ParamsConfigEmail;

@Configuration
public class EmailConfig {
	@Autowired
	private ParamsConfigEmail configs;
	private static int aliasIdCount=0;
	@Bean
	public Map<String, JavaMailSenderImpl> getJavaEmails() {
		
		Map<String, JavaMailSenderImpl> retorno = new HashMap<String, JavaMailSenderImpl>();
		configs.getConfigs().forEach(config -> {
			JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
			Properties prop = new Properties();
			mailSenderImpl.setHost(config.getHost());
			mailSenderImpl.setPort(config.getPort());
			mailSenderImpl.setUsername(config.getUsername());
			mailSenderImpl.setPassword(config.getPassword());
			mailSenderImpl.setProtocol(config.getProtocol());
			prop.put("mail.smtp.auth", String.valueOf(config.isSmtpAuth()));
			prop.put("mail.smtp.auth.tarttls.enable", String.valueOf(config.isStartTlsEnable()));
			prop.put("mail.smtp.auth.tarttls.required", String.valueOf(config.isStartTlsRequered()));
			prop.put("mail.smtp.ssl.enable", String.valueOf(config.isSslEnable()));
			mailSenderImpl.setJavaMailProperties(prop);
			String alias = config.getAlias();
			if(alias.isBlank()) {
				aliasIdCount=aliasIdCount+1;
				alias = String.valueOf(aliasIdCount);
			}
			
			retorno.put(alias, mailSenderImpl);

		});

		return retorno;
	}

}
