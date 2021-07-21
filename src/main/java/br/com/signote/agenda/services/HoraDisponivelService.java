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

import br.com.signote.agenda.domain.HoraDisponivel;
import br.com.signote.agenda.dto.HoraDisponivelDTO;
import br.com.signote.agenda.repositories.HoraDisponivelRepository;
import br.com.signote.agenda.services.exceptions.DataIntegrityException;
import br.com.signote.agenda.services.exceptions.ObjectNotFoundException;

@Service
public class HoraDisponivelService {
	
	@Autowired
	private HoraDisponivelRepository repo;

	public HoraDisponivel find(Integer id) {
		Optional<HoraDisponivel> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: " + HoraDisponivel.class.getName()));
	}
	
	public HoraDisponivel insert(HoraDisponivel obj) {
		obj.setId(null); // Para garantir que seraá uma obj novo caso contrário será uma atualização
		return repo.save(obj);
	}

	public HoraDisponivel update(HoraDisponivel obj) {
		HoraDisponivel newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	private void updateData(HoraDisponivel newObj, HoraDisponivel obj) {
		newObj.setHoraMinuto(obj.getHoraMinuto());		
	}

	public HoraDisponivel fromDTO(@Valid HoraDisponivelDTO objDto) {
		return new HoraDisponivel(objDto.getId(), objDto.getHoraMinuto());
	}
	
	public void delete(Integer id) {
		find(id);
		try {
		repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma HoraDisponivel que possui Médico");
		}
	}
	
	public List<HoraDisponivel> findAll(){
		return repo.findAll();
	}
	
	
	public Page<HoraDisponivel> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy); 
		return repo.findAll(pageRequest);
	}
}
