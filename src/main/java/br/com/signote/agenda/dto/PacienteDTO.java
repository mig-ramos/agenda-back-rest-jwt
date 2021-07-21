package br.com.signote.agenda.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.signote.agenda.domain.Paciente;


public class PacienteDTO implements Serializable {
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
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date data_nascimento;
	
	public PacienteDTO() {
	}
		
	public PacienteDTO(Paciente obj) {
		id = obj.getId();
		email = obj.getEmail();
		senha = obj.getSenha();
		nome = obj.getNome();
		data_nascimento = obj.getData_nascimento();
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

	public Date getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(Date data_nascimento) {
		this.data_nascimento = data_nascimento;
	}
}
