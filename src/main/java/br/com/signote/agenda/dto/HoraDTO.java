package br.com.signote.agenda.dto;

import java.io.Serializable;

import javax.persistence.Column;

import br.com.signote.agenda.domain.Hora;

public class HoraDTO implements Serializable {
	private static final long serialVersionUID = 1L;	
	
	private Integer id;
	
	@Column(nullable = false, unique = true)
	private String hora;
	
	public HoraDTO() {
	}
	
	public HoraDTO(Hora obj) {
		id = obj.getId();
		hora = obj.getHora();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}
	
}
