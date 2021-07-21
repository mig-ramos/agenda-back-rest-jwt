package br.com.signote.agenda.dto;

import java.io.Serializable;
import java.time.LocalTime;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.signote.agenda.domain.HoraDisponivel;

public class HoraDisponivelDTO implements Serializable {
	private static final long serialVersionUID = 1L;	
	
	private Integer id;
	
	@JsonFormat(pattern = "HH:mm")
	@Column(nullable = false, unique = true)
	private LocalTime horaMinuto;
	
	public HoraDisponivelDTO() {
	}
	
	public HoraDisponivelDTO(HoraDisponivel obj) {
		id = obj.getId();
		horaMinuto = obj.getHoraMinuto();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalTime getHoraMinuto() {
		return horaMinuto;
	}

	public void setHoraMinuto(LocalTime horaMinuto) {
		this.horaMinuto = horaMinuto;
	}
}
