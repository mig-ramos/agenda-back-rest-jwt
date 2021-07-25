package br.com.signote.agenda.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import br.com.signote.agenda.domain.Agenda;
import br.com.signote.agenda.domain.Paciente;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}") // Vem do application.properties
	private String sender;
	
	@Override
	public void sendAgendaConfirmationEmail(Agenda obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromAgenda(obj);
		sendEmail(sm);
	}
	protected SimpleMailMessage prepareSimpleMailMessageFromAgenda(Agenda obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getPaciente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Agendamento confirmado! Código: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}

	@Override
	public void sendNewPasswordEmail(Paciente paciente, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(paciente, newPass);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareNewPasswordEmail(Paciente paciente, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(paciente.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: " + newPass);
		return sm;
	}
}
