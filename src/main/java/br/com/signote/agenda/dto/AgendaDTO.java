package br.com.signote.agenda.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.signote.agenda.domain.Agenda;
import br.com.signote.agenda.domain.Especialidade;
import br.com.signote.agenda.domain.HoraDisponivel;
import br.com.signote.agenda.domain.Medico;
import br.com.signote.agenda.domain.Paciente;
import br.com.signote.agenda.domain.TipoConsulta;

public class AgendaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@ManyToOne
	@JoinColumn(name = "especialidade_id")
	private Especialidade especialidade;

	@ManyToOne
	@JoinColumn(name = "medico_id")
	private Medico medico;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataDisponivel;

	@ManyToOne
	@JoinColumn(name = "horaDisponivel_id")
	private HoraDisponivel horaDisponivel;

	@ManyToOne
	@JoinColumn(name = "tipoConsulta_id")
	private TipoConsulta tipoConsulta;

	@ManyToOne
	@JoinColumn(name = "paciente_id")
	private Paciente paciente;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date dataCadastro;

	private String observacao;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@ColumnDefault(value = "CURRENT_TIMESTAMP")
	private Date ultimaAlteracao;
	
	public AgendaDTO() {
	}

	public AgendaDTO(Agenda obj) {
		id = obj.getId();
		especialidade = obj.getEspecialidade();
		medico = obj.getMedico();
		dataDisponivel = obj.getDataDisponivel();
		horaDisponivel = obj.getHoraDisponivel();
		tipoConsulta = obj.getTipoConsulta();
		paciente = obj.getPaciente();
		dataCadastro = obj.getDataCadastro();
		observacao = obj.getObservacao();
		ultimaAlteracao = obj.getUltimaAlteracao();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public LocalDate getDataDisponivel() {
		return dataDisponivel;
	}

	public void setDataDisponivel(LocalDate dataDisponivel) {
		this.dataDisponivel = dataDisponivel;
	}

	public HoraDisponivel getHoraDisponivel() {
		return horaDisponivel;
	}

	public void setHoraDisponivel(HoraDisponivel horaDisponivel) {
		this.horaDisponivel = horaDisponivel;
	}

	public TipoConsulta getTipoConsulta() {
		return tipoConsulta;
	}

	public void setTipoConsulta(TipoConsulta tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao) {
		this.ultimaAlteracao = ultimaAlteracao;
	}	
}
