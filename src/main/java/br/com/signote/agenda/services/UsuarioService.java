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

import br.com.signote.agenda.domain.Usuario;
import br.com.signote.agenda.dto.UsuarioDTO;
import br.com.signote.agenda.dto.UsuarioNewDTO;
import br.com.signote.agenda.repositories.UsuarioRepository;
import br.com.signote.agenda.services.exceptions.DataIntegrityException;
import br.com.signote.agenda.services.exceptions.ObjectNotFoundException;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repo;
	
	public Usuario find(Integer id) {
		Optional<Usuario> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: " + Usuario.class.getName()));
	}
	
	@Transactional
	public Usuario insert(Usuario obj) {
		obj.setId(null); // Para garantir que seraá uma obj novo caso contrário será uma atualização
		return repo.save(obj);
	}

	public Usuario fromDTO(@Valid UsuarioDTO objDTO) {
		return new Usuario(objDTO.getId(), objDTO.getEmail(), objDTO.getSenha(),null,null,null, null);
	}
	public Usuario fromDTO(@Valid UsuarioNewDTO objDTO) {		
		Usuario usu = new Usuario(null, objDTO.getEmail(), objDTO.getSenha(), objDTO.getCodigo(), objDTO.getInstante(), objDTO.getAtivo(), objDTO.getPerfil());
		Boolean ativo = objDTO.getAtivo() == null || !objDTO.getAtivo() ? false : true;
		usu.setAtivo(ativo);
		Date instante = objDTO.getInstante() == null ? new Date():  objDTO.getInstante();
		usu.setInstante(instante);
		return usu;		
	}

	public Usuario update(Usuario obj) {
		Usuario newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	private void updateData(Usuario newObj, Usuario obj) {
		newObj.setEmail(obj.getEmail());
		newObj.setSenha(obj.getSenha());
	}

	public void delete(Integer id) {
		find(id);
		try {
		repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir este Usuário porque há entidades relacionadas");
		}		
	}

	public List<Usuario> findAll() {
		return repo.findAll();
	}
	
	@SuppressWarnings("null")
	public Usuario findByEmail(String email) {
	//	UserSS user = UserService.authenticated();
	//	if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
	//		throw new AuthorizationException("Acesso negado");
	//	}
		
		Usuario obj = repo.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException("Usuário não encontrado! Id: " + obj.getId()
			+ ", Tipo: " + Usuario.class.getName());
		}
		return obj;
	}

	public Page<Usuario> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy); 
		return repo.findAll(pageRequest);
	}
}
