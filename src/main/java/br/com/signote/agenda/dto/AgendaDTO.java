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
import br.com.signote.agenda.domain.Hora;
import br.com.signote.agenda.domain.Medico;
import br.com.signote.agenda.domain.TipoConsulta;
import br.com.signote.agenda.domain.Usuario;

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
	@JoinColumn(name = "hora_id")
	private Hora hora;

	@ManyToOne
	@JoinColumn(name = "tipoConsulta_id")
	private TipoConsulta tipoConsulta;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

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
		hora = obj.getHora();
		tipoConsulta = obj.getTipoConsulta();
		usuario = obj.getUsuario();
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

	public Hora getHora() {
		return hora;
	}

	public void setHora(Hora hora) {
		this.hora = hora;
	}

	public TipoConsulta getTipoConsulta() {
		return tipoConsulta;
	}

	public void setTipoConsulta(TipoConsulta tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
