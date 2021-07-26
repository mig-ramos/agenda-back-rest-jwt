package br.com.signote.agenda.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.signote.agenda.domain.enums.Perfil;

@Entity
public class Medico extends Usuario{
	private static final long serialVersionUID = 1L;	
	
//	@Column(length = 255, nullable = false)
//	private String nome;
	
	@Column(nullable = false)
	private Integer crm;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(nullable = false)
	private Date data_inscricao;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "medicos", cascade=CascadeType.ALL)
	private List<Especialidade> especialidades = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "medico")
	private List<Agenda> agendas = new ArrayList<>();	
		
	public Medico() {
		addPerfil(Perfil.MEDICO);
	}	
		
	public Medico(Integer id, String nome, String email, String senha, String codigo, Date instante, Boolean ativo, Integer crm, Date data_inscricao) {
		super(id, nome, email, senha, codigo, instante, ativo);
//		this.nome = nome;
		this.crm = crm;
		this.data_inscricao = data_inscricao;
		addPerfil(Perfil.MEDICO);
	}

//	public String getNome() {
//		return nome;
//	}
//
//	public void setNome(String nome) {
//		this.nome = nome;
//	}

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

	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	public List<Agenda> getAgendas() {
		return agendas;
	}

	public void setAgendas(List<Agenda> agendas) {
		this.agendas = agendas;
	}
}
