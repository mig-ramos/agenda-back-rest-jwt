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

import br.com.signote.agenda.domain.Especialidade;
import br.com.signote.agenda.dto.EspecialidadeDTO;
import br.com.signote.agenda.services.EspecialidadeService;

@RestController
@RequestMapping(value="/especialidades")
public class EspecialidadeResource {
	
	@Autowired
	private EspecialidadeService service;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<Especialidade> find(@PathVariable Integer id) {
		Especialidade obj = service.find(id);		
		return ResponseEntity.ok().body(obj);
	}
	
	/*
	 *  Para que este método salve a partir da url - usa-se @RequestBody
	 *  convertendo o objeto Json e Java automaticamente
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody EspecialidadeDTO objDTO){	
		Especialidade obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody EspecialidadeDTO objDTO, @PathVariable Integer id){
		Especialidade obj = service.fromDTO(objDTO);
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
	public ResponseEntity<List<EspecialidadeDTO>> findAll() {
		List<Especialidade> list = service.findAll();
		List<EspecialidadeDTO> listDTO = list.stream().map(obj -> new EspecialidadeDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	/*
	 * No Postman:
	 * http://localhost:8080/especialidades/page
	 * http://localhost:8080/especialidades/page?linesPerPage=3
	 * http://localhost:8080/especialidades/page?linesPerPage=3&page=1
	 * http://localhost:8080/especialidades/page?linesPerPage=3&page=1&direction=DESC
	 */
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<EspecialidadeDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Especialidade> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<EspecialidadeDTO> listDTO = list.map(obj -> new EspecialidadeDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}
	
}
