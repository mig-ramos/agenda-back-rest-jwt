package br.com.signote.agenda.services.exceptions;

public class DataIntegrityException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DataIntegrityException(String msg) {
		super(msg); // mandando a mensagem de volta para a super classe Runtime Exception
	}
	
	public DataIntegrityException(String msg, Throwable cause) {
		super(msg,cause); 
		/* mandando a mensagem de volta para a super classe Runtime Exception
		 * Geração padrão de excessão padronizada mas, com o meu tipo de excessão do meu pacote
		 */
	}
}
