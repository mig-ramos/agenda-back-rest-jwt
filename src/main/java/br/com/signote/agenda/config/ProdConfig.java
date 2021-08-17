package br.com.signote.agenda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.signote.agenda.services.EmailService;
import br.com.signote.agenda.services.SmtpEmailService;

@Configuration
@Profile("prod")
public class ProdConfig {

//	@Autowired
//	private DBService dbService;
//	
//	@Value("${spring.jpa.hibernate.ddl-auto}")
//	private String strategy;
//	
//	@Bean
//	public boolean instantiateDatabase() throws ParseException {
//		
//		if (!"create".equals(strategy)) {
//			return true; // Não recria o banco de dados
//		}
//		
//		dbService.instantiateTestDatabase();
//		return true;
//	}
	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}
