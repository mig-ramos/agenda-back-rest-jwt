package br.com.signote.agenda.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.signote.agenda.services.validation.UsuarioInsert;

@UsuarioInsert
public class UsuarioNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Email(message="Email inválido")
	private String email;

	@NotEmpty(message="Preenchimento obrigatório")
	private String senha;

	@Column(length = 6)
	private String codigo;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	@ColumnDefault(value = "CURRENT_TIMESTAMP")
	private Date instante;

	@ColumnDefault(value = "false")
	private Boolean ativo;
	
	public UsuarioNewDTO() {
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
}
