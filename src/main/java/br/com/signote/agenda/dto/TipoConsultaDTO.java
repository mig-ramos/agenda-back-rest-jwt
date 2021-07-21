package br.com.signote.agenda.dto;

import java.io.Serializable;

import javax.persistence.Column;

import br.com.signote.agenda.domain.TipoConsulta;

public class TipoConsultaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@Column(unique = true, length = 120, nullable = false)
	private String tipoConsulta;
	
	public TipoConsultaDTO() {
	}
	
	public TipoConsultaDTO(TipoConsulta obj) {
		this.id = obj.getId();
		this.tipoConsulta = obj.getTipoConsulta();
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipoConsulta() {
		return tipoConsulta;
	}

	public void setTipoConsulta(String tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}
}
