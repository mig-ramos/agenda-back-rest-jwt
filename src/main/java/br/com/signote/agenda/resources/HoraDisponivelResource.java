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

import br.com.signote.agenda.domain.Hora;
import br.com.signote.agenda.dto.HoraDTO;
import br.com.signote.agenda.services.HoraDisponivelService;

@RestController
@RequestMapping(value="/horas_disponiveis")
public class HoraDisponivelResource {
	
	@Autowired
	private HoraDisponivelService service;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<Hora> find(@PathVariable Integer id) {
		Hora obj = service.find(id);		
		return ResponseEntity.ok().body(obj);
	}
	
	/*
	 *  Para que este método salve a partir da url - usa-se @RequestBody
	 *  convertendo o objeto Json e Java automaticamente
	 */
//	@RequestMapping(method=RequestMethod.POST)
//	public ResponseEntity<Void> insert(@RequestBody HoraDisponivel obj){		
//		obj = service.insert(obj);
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
//				.path("/{id}").buildAndExpand(obj.getId()).toUri();
//		return ResponseEntity.created(uri).build();
//	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody HoraDTO objDTO){	
		Hora obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody HoraDTO objDto, @PathVariable Integer id) {
		Hora obj = service.fromDTO(objDto);
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
	public ResponseEntity<List<HoraDTO>> findAll() {
		List<Hora> list = service.findAll();
		List<HoraDTO> listDTO = list.stream().map(obj -> new HoraDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	/*
	 * No Postman:
	 * http://localhost:8080/horas_disponiveis/page
	 * http://localhost:8080/horas_disponiveis/page?linesPerPage=3
	 * http://localhost:8080/horas_disponiveis/page?linesPerPage=3&page=1
	 * http://localhost:8080/horas_disponiveis/page?linesPerPage=3&page=1&direction=DESC
	 */
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<HoraDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="horaMinuto") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Hora> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<HoraDTO> listDTO = list.map(obj -> new HoraDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}
}
