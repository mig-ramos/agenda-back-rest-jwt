package br.com.signote.agenda.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.signote.agenda.domain.enums.Perfil;

@Entity
public class Paciente extends Usuario{
	private static final long serialVersionUID = 1L;
	
	@Column(length = 255, nullable = false)
	private String nome;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date data_nascimento;
	
	@JsonIgnore
	@OneToMany(mappedBy = "paciente")
	private List<Agenda> agendas = new ArrayList<>();
	
	public Paciente() {
		addPerfil(Perfil.PACIENTE);
	}		
	
	public Paciente(Integer id, String email, String senha, String codigo, Date instante, Boolean ativo, String nome, Date data_nascimento) {
		super(id, email, senha, codigo, instante, ativo);
		this.nome = nome;
		this.data_nascimento = data_nascimento;
		addPerfil(Perfil.PACIENTE);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(Date data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	public List<Agenda> getAgendas() {
		return agendas;
	}

	public void setAgendas(List<Agenda> agendas) {
		this.agendas = agendas;
	}
}
