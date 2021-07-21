package br.com.signote.agenda.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.signote.agenda.domain.Medico;

public class MedicoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message="Preenchimento Obrigatório")
	@Email(message="Email inválido")
	private String email;

	@NotEmpty(message="Preenchimento Obrigatório")
	private String senha;
	
	@NotEmpty(message="Preenchimento Obrigatório")
	@Length(min=3, max=255, message="O tamanho deve ser entre 4 e 255 caracteres")
	private String nome;

	public MedicoDTO() {
	}
	
	public MedicoDTO(Medico obj) {
		id = obj.getId();
		email = obj.getEmail();
		senha = obj.getSenha();
		nome = obj.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	
}
