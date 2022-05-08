package br.com.phdigitalcode.emailservice.configs.params;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.mail")
@Data
public class ParamsConfigEmail {
	public List<Params> configs;

	@Data
	public static class Params {
		private String alias;
		private String host;
		private String username;
		private String password;
		private int port;
		private String protocol;
		private boolean smtpAuth;
		private boolean startTlsEnable;
		private boolean startTlsRequered;
		private boolean sslEnable;
	}
}
