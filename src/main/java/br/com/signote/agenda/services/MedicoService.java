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
import org.springframework.transaction.annotation.Transactional;

import br.com.signote.agenda.domain.Especialidade;
import br.com.signote.agenda.domain.Medico;
import br.com.signote.agenda.dto.MedicoDTO;
import br.com.signote.agenda.dto.MedicoNewDTO;
import br.com.signote.agenda.repositories.EspecialidadeRepository;
import br.com.signote.agenda.repositories.MedicoRepository;
import br.com.signote.agenda.services.exceptions.DataIntegrityException;
import br.com.signote.agenda.services.exceptions.ObjectNotFoundException;

@Service
public class MedicoService {
	
	@Autowired
	private MedicoRepository repo;
	
	@Autowired
	private EspecialidadeRepository especialidadeRepository;
	
	public Medico find(Integer id) {
		Optional<Medico> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: " + Medico.class.getName()));
	}
	
	@Transactional  //import org.springframework.transaction.annotation.Transactional;
	public Medico insert(Medico obj) {
		obj.setId(null); // Para garantir que seraá uma obj novo caso contrário será uma atualização
		return repo.save(obj);
	}

	public Medico fromDTO(@Valid MedicoDTO objDTO) {
		return new Medico(objDTO.getId(), objDTO.getEmail(), objDTO.getSenha(), null, null, null, null, objDTO.getNome(),null, null);
	}
	
	public Medico fromDTO(@Valid MedicoNewDTO objDTO) {
		
		Medico med = new Medico(null, objDTO.getEmail(), objDTO.getSenha(), objDTO.getCodigo(), objDTO.getInstante(), objDTO.getAtivo(), objDTO.getPerfil(), objDTO.getNome(),  objDTO.getCrm(), objDTO.getData_inscricao());
		Boolean ativo = objDTO.getAtivo() == null || !objDTO.getAtivo() ? false : true;
		med.setAtivo(ativo);
		Date instante = objDTO.getInstante() == null ? new Date():  objDTO.getInstante();
		med.setInstante(instante);
		
		Especialidade esp = findEsp(objDTO.getEspecialidade_id());
		med.getEspecialidades().add(esp);
	
		esp.getMedicos().add(med);
		
		return med;		
	}
	
	public Especialidade findEsp(Integer id) {		
		Optional<Especialidade> obj = especialidadeRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Especialidade.class.getName()));
	}
	
	public Medico update(Medico obj) {
		Medico newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	private void updateData(Medico newObj, Medico obj) {
		newObj.setEmail(obj.getEmail());
		newObj.setSenha(obj.getSenha());
		newObj.setNome(obj.getNome());
	}

	public void delete(Integer id) {
		find(id);
		try {
		repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir Médico porque há entidades relacionadas");
		}
	}

	public List<Medico> findAll() {
		return repo.findAll();
	}

	public Page<Medico> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy); 
		return repo.findAll(pageRequest);
	}
}
