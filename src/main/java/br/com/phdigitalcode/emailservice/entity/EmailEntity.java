package br.com.phdigitalcode.emailservice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.phdigitalcode.emailservice.enums.StatusEmail;
import lombok.Data;

@Data
@Entity
@Table(name = "email")
public class EmailEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "email_from")
	private String emailFrom;
	@Column(name = "email_to")
	private String emailTo;
	private String subject;
	private String texto;
	@Column(name = "email_mensagem_retorno")
	private String emailMenssageRetorno;
	@Enumerated(EnumType.STRING)
	private StatusEmail status;
	private LocalDateTime send_date;
}
