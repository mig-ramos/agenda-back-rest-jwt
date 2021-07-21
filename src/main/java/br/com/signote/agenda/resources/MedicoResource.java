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

import br.com.signote.agenda.domain.Medico;
import br.com.signote.agenda.dto.MedicoDTO;
import br.com.signote.agenda.dto.MedicoNewDTO;
import br.com.signote.agenda.services.MedicoService;

@RestController
@RequestMapping(value="/medicos")
public class MedicoResource {
	
	@Autowired
	private MedicoService service;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<Medico> find(@PathVariable Integer id) {
		Medico obj = service.find(id);		
		return ResponseEntity.ok().body(obj);
	}
	
	/*
	 *  Para que este método salve a partir da url - usa-se @RequestBody
	 *  convertendo o objeto Json e Java automaticamente
	 *  Antes do objeto DTO seja passado pra frente, ele tem ser validado com o @Valid
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody MedicoNewDTO objDTO){		
		Medico obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody MedicoDTO objDTO, @PathVariable Integer id){
		Medico obj = service.fromDTO(objDTO);
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
	public ResponseEntity<List<MedicoDTO>> findAll() {
		List<Medico> list = service.findAll();
		List<MedicoDTO> listDTO = list.stream().map(obj -> new MedicoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<MedicoDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Medico> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<MedicoDTO> listDTO = list.map(obj -> new MedicoDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}


}
