package br.com.signote.agenda.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.signote.agenda.services.DBService;
import br.com.signote.agenda.services.EmailService;
import br.com.signote.agenda.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.instantiateTestDatabase();
		return true;
	}
	
	@Bean // Todo método com @Bean, vai estar disponível para todo o sistema
	public EmailService emailService() {
		return new MockEmailService();
	}
}
