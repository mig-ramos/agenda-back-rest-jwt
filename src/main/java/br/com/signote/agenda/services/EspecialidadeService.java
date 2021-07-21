package br.com.signote.agenda.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.signote.agenda.domain.Especialidade;
import br.com.signote.agenda.dto.EspecialidadeDTO;
import br.com.signote.agenda.repositories.EspecialidadeRepository;
import br.com.signote.agenda.services.exceptions.DataIntegrityException;
import br.com.signote.agenda.services.exceptions.ObjectNotFoundException;

@Service
public class EspecialidadeService {
	
	@Autowired
	private EspecialidadeRepository repo;

	public Especialidade find(Integer id) {
		Optional<Especialidade> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: " + Especialidade.class.getName()));
	}
	
	public Especialidade insert(Especialidade obj) {
		obj.setId(null); // Para garantir que seraá uma obj novo caso contrário será uma atualização
		return repo.save(obj);
	}
	
	public Especialidade update(Especialidade obj) {
		Especialidade newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	private void updateData(Especialidade newObj, Especialidade obj) {
		newObj.setNome(obj.getNome());
		newObj.setDescricao(obj.getDescricao());
	}

	public void delete(Integer id) {
		find(id);
		try {
		repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma Especialidade que possui Médico");
		}
	}
	
	public List<Especialidade> findAll(){
		return repo.findAll();
	}
	
	
	public Page<Especialidade> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy); 
		return repo.findAll(pageRequest);
	}
	
	public Especialidade fromDTO(EspecialidadeDTO objDTO) {
		return new Especialidade(objDTO.getId(), objDTO.getNome(), objDTO.getDescricao());
	}
}
