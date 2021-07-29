package br.com.signote.agenda.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import br.com.signote.agenda.domain.Agenda;
import br.com.signote.agenda.domain.Usuario;

public interface EmailService {

	void sendAgendaConfirmationEmail(Agenda obj);

	// Versao de texto plano
	void sendEmail(SimpleMailMessage msg);

	void sendNewPasswordEmail(Usuario usuario, String newPass);
	
	// Versao de HTML
	void sendAgendaConfirmationHtmlEmail(Agenda obj);
	
	void sendHtmlEmail(MimeMessage msg);
}
