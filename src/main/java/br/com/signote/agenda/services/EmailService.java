package br.com.signote.agenda.services;

import org.springframework.mail.SimpleMailMessage;

import br.com.signote.agenda.domain.Agenda;
import br.com.signote.agenda.domain.Paciente;

public interface EmailService {

	void sendAgendaConfirmationEmail(Agenda obj);

	void sendEmail(SimpleMailMessage msg);

	void sendNewPasswordEmail(Paciente paciente, String newPass);
}
