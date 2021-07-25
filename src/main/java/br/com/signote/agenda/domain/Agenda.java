package br.com.signote.agenda.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Agenda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="especialidade_id")
	private Especialidade especialidade;
	
	@ManyToOne
	@JoinColumn(name="medico_id")
	private Medico medico;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataDisponivel;
	
	@ManyToOne
	@JoinColumn(name="horaDisponivel_id")
	private HoraDisponivel horaDisponivel;
	
	@ManyToOne
	@JoinColumn(name="tipoConsulta_id")
	private TipoConsulta tipoConsulta;
	
	@ManyToOne
	@JoinColumn(name="paciente_id")
	private Paciente paciente;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@ColumnDefault(value = "CURRENT_TIMESTAMP")
	private Date dataCadastro;

	private String Observacao;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@ColumnDefault(value = "CURRENT_TIMESTAMP")
	private Date ultimaAlteracao;
	
	public Agenda() {
	}

	public Agenda(Integer id, Especialidade especialidade, Medico medico,LocalDate dataDisponivel, HoraDisponivel horaDisponivel, 
			TipoConsulta tipoConsulta, Paciente paciente, Date dataCadastro, String observacao, Date ultimaAlteracao) {
		super();
		this.id = id;
		this.especialidade = especialidade;
		this.medico = medico;
		this.dataDisponivel = dataDisponivel;
		this.horaDisponivel = horaDisponivel;
		this.tipoConsulta = tipoConsulta;
		this.paciente = paciente;
		this.dataCadastro = dataCadastro;
		this.Observacao = observacao;
		this.ultimaAlteracao = ultimaAlteracao;
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
		return Observacao;
	}

	public void setObservacao(String observacao) {
		Observacao = observacao;
	}	

	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao) {
		this.ultimaAlteracao = ultimaAlteracao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agenda other = (Agenda) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:MM:ss"); 
		StringBuilder builder = new StringBuilder();
		builder.append("Agendamento: ");
		builder.append(id);
		builder.append("\n");
		builder.append("Especialidade: ");
		builder.append(getEspecialidade().getNome());
		builder.append("\n");
		builder.append("Medico: ");
		builder.append(getMedico().getNome());
		builder.append("\n");
		builder.append("Data programada: ");
		builder.append(getDataDisponivel());
		builder.append("Hora agendada= ");
		builder.append(sdf.format(getHoraDisponivel()));
		builder.append("\n");
		builder.append("Tipo da Consulta: ");
		builder.append(getTipoConsulta().getTipoConsulta());
		builder.append("\n");
		builder.append("Paciente: ");
		builder.append(getPaciente().getNome());
		builder.append("Cadastro: ");
		builder.append(sdf.format(getDataCadastro()));
	
		return builder.toString();
	}	
	
	
}
