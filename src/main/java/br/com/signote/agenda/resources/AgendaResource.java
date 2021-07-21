package br.com.signote.agenda.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.signote.agenda.domain.Agenda;
import br.com.signote.agenda.dto.AgendaDTO;
import br.com.signote.agenda.dto.AgendaNewDTO;
import br.com.signote.agenda.services.AgendaService;

@RestController
@RequestMapping(value="/agendamentos")
public class AgendaResource {
	
	@Autowired
	private AgendaService service;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<Agenda> find(@PathVariable Integer id) {
		Agenda obj = service.find(id);		
		return ResponseEntity.ok().body(obj);
	}
	
	// http://localhost:8080/usuarios/email?value=m.ephemeris@gmail.com
	@RequestMapping(value="/email", method=RequestMethod.GET)
	public ResponseEntity<Agenda> find(@RequestParam(value="value") String email) {		
		Agenda obj = service.findByEmail(email);
		return ResponseEntity.ok().body(obj);
	}
	
	/*
	 *  Para que este método salve a partir da url - usa-se @RequestBody
	 *  convertendo o objeto Json e Java automaticamente
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody AgendaNewDTO objDTO){	
		Agenda obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody AgendaDTO objDTO, @PathVariable Integer id){
		Agenda obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<AgendaDTO>> findAll() {
		List<Agenda> list = service.findAll();
		List<AgendaDTO> listDTO = list.stream().map(obj -> new AgendaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
//	/*
//	 * No Postman:
//	 * http://localhost:8080/especialidades/page
//	 * http://localhost:8080/especialidades/page?linesPerPage=3
//	 * http://localhost:8080/especialidades/page?linesPerPage=3&page=1
//	 * http://localhost:8080/especialidades/page?linesPerPage=3&page=1&direction=DESC
//	 */
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<AgendaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="id") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Agenda> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<AgendaDTO> listDTO = list.map(obj -> new AgendaDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}

}
