package br.com.signote.agenda.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.signote.agenda.domain.Perfil;
import br.com.signote.agenda.dto.PerfilDTO;
import br.com.signote.agenda.repositories.PerfilRepository;
import br.com.signote.agenda.services.exceptions.DataIntegrityException;
import br.com.signote.agenda.services.exceptions.ObjectNotFoundException;

@Service
public class PerfilService {
	
	@Autowired
	private PerfilRepository repo;

	public Perfil find(Integer id) {
		Optional<Perfil> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: " + Perfil.class.getName()));
	}
	
	public Perfil insert(Perfil obj) {
		obj.setId(null); // Para garantir que seraá uma obj novo caso contrário será uma atualização
		return repo.save(obj);
	}
	
	public Perfil update(Perfil obj) {
		find(obj.getId());
		return repo.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
		repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma Perfil que possui Médico");
		}
	}
	
	public List<Perfil> findAll(){
		return repo.findAll();
	}

	public Perfil fromDTO(@Valid PerfilDTO objDTO) {
		return new Perfil(objDTO.getId(), objDTO.getNome());
	}
}
