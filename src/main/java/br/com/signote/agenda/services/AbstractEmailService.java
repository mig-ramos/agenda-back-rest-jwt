package br.com.signote.agenda.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.signote.agenda.domain.Agenda;
import br.com.signote.agenda.domain.Usuario;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}") // Vem do application.properties
	private String sender;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendAgendaConfirmationEmail(Agenda obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromAgenda(obj);
		sendEmail(sm);
	}
	protected SimpleMailMessage prepareSimpleMailMessageFromAgenda(Agenda obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getUsuario().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Agendamento confirmado! Código: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}
	
	protected String htmlFromTemplateAgenda(Agenda obj) {
		Context context = new Context();
		context.setVariable("agenda", obj);
		return templateEngine.process("email/confirmacaoAgenda", context);		
	}
	
	@Override
	public void sendAgendaConfirmationHtmlEmail(Agenda obj) {
		try {
			MimeMessage mm = prepareMimeMessageFromAgenda(obj);
			sendHtmlEmail(mm);
		}
		catch (MessagingException e) {
			sendAgendaConfirmationEmail(obj);
		}
	}
	
	protected MimeMessage prepareMimeMessageFromAgenda(Agenda obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getUsuario().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Agendamento confirmado! Código: " + obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplateAgenda(obj), true);
		return mimeMessage;
	}
	@Override
	public void sendNewPasswordEmail(Usuario usuario, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(usuario, newPass);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareNewPasswordEmail(Usuario usuario, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(usuario.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: " + newPass);
		return sm;
	}
	
}
