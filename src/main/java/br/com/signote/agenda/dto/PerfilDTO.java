package br.com.signote.agenda.dto;

import java.io.Serializable;

import javax.persistence.Column;

import br.com.signote.agenda.domain.Perfil;

public class PerfilDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@Column(unique=true, length = 50, nullable = false)
	private String nome;
	
	public PerfilDTO() {
	}
	
	public PerfilDTO(Perfil obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
