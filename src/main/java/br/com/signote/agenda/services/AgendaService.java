package br.com.signote.agenda.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.signote.agenda.domain.Agenda;
import br.com.signote.agenda.dto.AgendaDTO;
import br.com.signote.agenda.dto.AgendaNewDTO;
import br.com.signote.agenda.repositories.AgendaRepository;
import br.com.signote.agenda.services.exceptions.DataIntegrityException;
import br.com.signote.agenda.services.exceptions.ObjectNotFoundException;

@Service
public class AgendaService {
	
	@Autowired
	private AgendaRepository repo;
	
	public Agenda find(Integer id) {
		Optional<Agenda> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: " + Agenda.class.getName()));
	}

	public Agenda findByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	public Agenda fromDTO(@Valid AgendaNewDTO objDTO) {
		Agenda age = new Agenda(null, objDTO.getEspecialidade(), objDTO.getMedico(), objDTO.getDataDisponivel(), objDTO.getHoraDisponivel(), objDTO.getTipoConsulta(), objDTO.getPaciente(), objDTO.getDataCadastro(), objDTO.getObservacao(), objDTO.getUltimaAlteracao());
		

		age.setDataCadastro(new Date());
		return age;
	}

	public Agenda insert(Agenda obj) {
		obj.setId(null); // Para garantir que seraá uma obj novo caso contrário será uma atualização
		return repo.save(obj);
	}

	public Agenda fromDTO(@Valid AgendaDTO objDTO) {
		return new Agenda(objDTO.getId(), null, null, null, null, null, null, null, objDTO.getObservacao(), null);
	}

	public Agenda update(Agenda obj) {
		Agenda newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	private void updateData(Agenda newObj, Agenda obj) {
		newObj.setUltimaAlteracao(new Date());		
	//	newObj.setTipoConsulta(obj.getTipoConsulta());	
		newObj.setObservacao(obj.getObservacao());
	}

	public void delete(Integer id) {
		find(id);
		try {
		repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir este Agendamento porque há entidades relacionadas");
		}		
	}

	public List<Agenda> findAll() {
		return repo.findAll();
	}

	public Page<Agenda> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy); 
		return repo.findAll(pageRequest);
	}
}
