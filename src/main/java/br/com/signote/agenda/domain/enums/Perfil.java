package br.com.signote.agenda.domain.enums;

public enum Perfil {
	// ROLE_ é exigencia do Spring Security
	ADMIN(1, "ROLE_ADMIN"), 
	USUARIO(2, "ROLE_USUARIO"),
	PACIENTE(3, "ROLE_PACIENTE"),
	MEDICO(4, "ROLE_MEDICO");
	
	private int cod;
	private String descricao;
	
	private Perfil(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao () {
		return descricao;
	}
	
	public static Perfil toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		
		for (Perfil x : Perfil.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
}
