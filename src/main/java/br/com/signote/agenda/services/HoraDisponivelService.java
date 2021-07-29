package br.com.signote.agenda.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.signote.agenda.domain.Hora;
import br.com.signote.agenda.dto.HoraDTO;
import br.com.signote.agenda.repositories.HoraDisponivelRepository;
import br.com.signote.agenda.services.exceptions.DataIntegrityException;
import br.com.signote.agenda.services.exceptions.ObjectNotFoundException;

@Service
public class HoraDisponivelService {
	
	@Autowired
	private HoraDisponivelRepository repo;

	public Hora find(Integer id) {
		Optional<Hora> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: " + Hora.class.getName()));
	}
	
	public Hora insert(Hora obj) {
		obj.setId(null); // Para garantir que seraá uma obj novo caso contrário será uma atualização
		return repo.save(obj);
	}

	public Hora update(Hora obj) {
		Hora newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	private void updateData(Hora newObj, Hora obj) {
		newObj.setHora(obj.getHora());		
	}

	public Hora fromDTO(@Valid HoraDTO objDto) {
		return new Hora(objDto.getId(), objDto.getHoraMinuto());
	}
	
	public void delete(Integer id) {
		find(id);
		try {
		repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma HoraDisponivel que possui Médico");
		}
	}
	
	public List<Hora> findAll(){
		return repo.findAll();
	}
	
	
	public Page<Hora> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy); 
		return repo.findAll(pageRequest);
	}
}
