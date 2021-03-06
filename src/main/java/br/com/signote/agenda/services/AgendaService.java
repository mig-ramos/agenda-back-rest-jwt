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
import br.com.signote.agenda.domain.Medico;
import br.com.signote.agenda.domain.Usuario;
import br.com.signote.agenda.dto.AgendaDTO;
import br.com.signote.agenda.dto.AgendaNewDTO;
import br.com.signote.agenda.repositories.AgendaRepository;
import br.com.signote.agenda.security.UserSS;
import br.com.signote.agenda.services.exceptions.AuthorizationException;
import br.com.signote.agenda.services.exceptions.DataIntegrityException;
import br.com.signote.agenda.services.exceptions.ObjectNotFoundException;

@Service
public class AgendaService {
	
	@Autowired
	private AgendaRepository repo;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private MedicoService medicoService;
	
	@Autowired
	private EmailService emailService;
	
	public Agenda find(Integer id) {
		Optional<Agenda> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: " + Agenda.class.getName()));
	}

	public Agenda fromDTO(@Valid AgendaNewDTO objDTO) {
		Agenda age = new Agenda(null, objDTO.getEspecialidade(), objDTO.getMedico(), objDTO.getDataDisponivel(), objDTO.getHora(), objDTO.getTipoConsulta(), objDTO.getUsuario(), objDTO.getDataCadastro(), objDTO.getObservacao(), objDTO.getUltimaAlteracao());
		age.setDataCadastro(new Date());
		return age;
	}

//	@Transactional  //import org.springframework.transaction.annotation.Transactional;
	public Agenda insert(Agenda obj) {
		obj.setId(null); // Para garantir que será uma obj novo caso contrário será uma atualização
		repo.save(obj);
//		emailService.sendAgendaConfirmationEmail(obj);  // Substitui para HTML
		emailService.sendAgendaConfirmationHtmlEmail(obj);
		return obj;
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
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		} 
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy); 
		return repo.findAll(pageRequest);	
	}

	public Page<Agenda> findPagePaciente(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		} 
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Usuario usuario = usuarioService.find(user.getId()); 
		return repo.findByUsuario(usuario, pageRequest);	
	}

	public Page<Agenda> findPageMedico(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		} 
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Medico medico = medicoService.find(user.getId()); 
		return repo.findByMedico(medico, pageRequest);	
	}
}
