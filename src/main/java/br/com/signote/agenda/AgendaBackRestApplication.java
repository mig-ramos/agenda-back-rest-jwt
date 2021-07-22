package br.com.signote.agenda;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * A implementação da classe CommandLineRunner, possibilita a instanciação de objetos na base de dados.
 */

@SpringBootApplication
public class AgendaBackRestApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AgendaBackRestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		
	}
}
