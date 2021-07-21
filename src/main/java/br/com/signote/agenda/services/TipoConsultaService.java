package br.com.signote.agenda.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.signote.agenda.domain.TipoConsulta;
import br.com.signote.agenda.dto.TipoConsultaDTO;
import br.com.signote.agenda.repositories.TipoConsultaRepository;
import br.com.signote.agenda.services.exceptions.DataIntegrityException;
import br.com.signote.agenda.services.exceptions.ObjectNotFoundException;

@Service
public class TipoConsultaService {
	
	@Autowired
	private TipoConsultaRepository repo;

	public TipoConsulta find(Integer id) {
		Optional<TipoConsulta> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: " + TipoConsulta.class.getName()));
	}
	
	public TipoConsulta insert(TipoConsulta obj) {
		obj.setId(null); // Para garantir que seraá uma obj novo caso contrário será uma atualização
		return repo.save(obj);
	}
	
	public TipoConsulta update(TipoConsulta obj) {
		find(obj.getId());
		return repo.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
		repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma TipoConsulta que possui Médico");
		}
	}
	
	public List<TipoConsulta> findAll(){
		return repo.findAll();
	}
	
	public TipoConsulta fromDTO(TipoConsultaDTO objDTO) {
		return new TipoConsulta(objDTO.getId(), objDTO.getTipoConsulta());
	}
}
