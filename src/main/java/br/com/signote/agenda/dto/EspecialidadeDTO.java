package br.com.signote.agenda.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.signote.agenda.domain.Especialidade;

public class EspecialidadeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=5, max=100, message= "O tamanho deve ser entre 5 e 100 caracteres")
	private String nome;
	private String descricao;
	
	public EspecialidadeDTO() {
	}
	
	public EspecialidadeDTO(Especialidade obj) {
		id = obj.getId();
		nome = obj.getNome();
		descricao = obj.getDescricao();
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
