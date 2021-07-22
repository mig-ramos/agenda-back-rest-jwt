package br.com.signote.agenda.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MedicoNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message="Preenchimento Obrigatório")
	@Email(message="Email inválido")
	private String email;
	
	@NotEmpty(message="Preenchimento Obrigatório")
	private String senha;
	
	private String codigo;
	private Date instante;
	private Boolean ativo;
	
	@NotEmpty(message="Preenchimento Obrigatório")
	@Length(min=3, max=255, message="O tamanho deve ser entre 4 e 255 caracteres")
	private String nome;
	
	@Column(nullable = false)
	private Integer crm;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(nullable = false)
	private Date data_inscricao;
	
	private Integer especialidade_id;
	
	public MedicoNewDTO() {
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCrm() {
		return crm;
	}

	public void setCrm(Integer crm) {
		this.crm = crm;
	}

	public Date getData_inscricao() {
		return data_inscricao;
	}

	public void setData_inscricao(Date data_inscricao) {
		this.data_inscricao = data_inscricao;
	}

	public Integer getEspecialidade_id() {
		return especialidade_id;
	}

	public void setEspecialidade_id(Integer especialidade_id) {
		this.especialidade_id = especialidade_id;
	}
}
