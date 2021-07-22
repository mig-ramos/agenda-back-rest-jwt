package br.com.signote.agenda.services;

import java.text.ParseException;
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
import org.springframework.transaction.annotation.Transactional;

import br.com.signote.agenda.domain.Paciente;
import br.com.signote.agenda.dto.PacienteDTO;
import br.com.signote.agenda.dto.PacienteNewDTO;
import br.com.signote.agenda.repositories.PacienteRepository;
import br.com.signote.agenda.services.exceptions.DataIntegrityException;
import br.com.signote.agenda.services.exceptions.ObjectNotFoundException;

@Service
public class PacienteService {
	
	@Autowired
	private PacienteRepository repo;

	public Paciente find(Integer id) {
		Optional<Paciente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: " + Paciente.class.getName()));
	}
	
	@Transactional  //import org.springframework.transaction.annotation.Transactional;
	public Paciente insert(Paciente obj) {
		obj.setId(null); // Para garantir que seraá uma obj novo caso contrário será uma atualização
		return repo.save(obj);
	}

	public Paciente fromDTO(@Valid PacienteDTO objDTO) {
		return new Paciente(objDTO.getId(), objDTO.getEmail(), objDTO.getSenha(), null, null, null, objDTO.getNome(), objDTO.getData_nascimento());
	}
	
	public Paciente fromDTO(@Valid PacienteNewDTO objDTO) throws ParseException {

		Paciente paci = new Paciente(null, objDTO.getEmail(), objDTO.getSenha(), objDTO.getCodigo(), objDTO.getInstante(), objDTO.getAtivo(), objDTO.getNome(), objDTO.getData_nascimento());
		Boolean ativo = objDTO.getAtivo() == null || !objDTO.getAtivo() ? false : true;
		paci.setAtivo(ativo);
		Date instante = objDTO.getInstante() == null ? new Date():  objDTO.getInstante();
		paci.setInstante(instante);
		return paci;
		
	}

	public Paciente update(Paciente obj) {
		Paciente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	private void updateData(Paciente newObj, Paciente obj) {		
		newObj.setEmail(obj.getEmail());
		newObj.setSenha(obj.getSenha());
		newObj.setNome(obj.getNome());
		newObj.setData_nascimento(obj.getData_nascimento());		
	}

	public void delete(Integer id) {
		find(id);
		try {
		repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir Paciente porque há entidades relacionadas");
		}
	}

	public List<Paciente> findAll() {
		return repo.findAll();
	}

	public Page<Paciente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy); 
		return repo.findAll(pageRequest);
	}
}
