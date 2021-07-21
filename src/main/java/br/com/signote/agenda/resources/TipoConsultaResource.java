package br.com.signote.agenda.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.signote.agenda.domain.TipoConsulta;
import br.com.signote.agenda.dto.TipoConsultaDTO;
import br.com.signote.agenda.services.TipoConsultaService;

@RestController
@RequestMapping(value="/tipos_consultas")
public class TipoConsultaResource {
	
	@Autowired
	private TipoConsultaService service;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<TipoConsulta> find(@PathVariable Integer id) {
		TipoConsulta obj = service.find(id);		
		return ResponseEntity.ok().body(obj);
	}
	
	/*
	 *  Para que este método salve a partir da url - usa-se @RequestBody
	 *  convertendo o objeto Json e Java automaticamente
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody TipoConsultaDTO objDTO){	
		TipoConsulta obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody TipoConsultaDTO objDTO, @PathVariable Integer id){
		TipoConsulta obj = service.fromDTO(objDTO);
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
	public ResponseEntity<List<TipoConsultaDTO>> findAll() {
		List<TipoConsulta> list = service.findAll();
		List<TipoConsultaDTO> listDTO = list.stream().map(obj -> new TipoConsultaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
		
}
