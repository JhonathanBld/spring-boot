package br.edu.unidep.ApiES.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.unidep.ApiES.model.Consulta;
import br.edu.unidep.ApiES.repository.ConsultaRepository;
import br.edu.unidep.ApiES.service.ConsultaService;
import error.ResourceNotFoundExeption;



@RestController
@RequestMapping("/consultas")
public class ConsultaResource {
	
	@Autowired
	private ConsultaRepository repositorio;
	
	@Autowired
	private ConsultaService consultaService;
	

	@GetMapping
	public ResponseEntity<?> listar() {
		List<Consulta> consultas = repositorio.findAll();
		if (consultas == null) {
			throw new ResourceNotFoundExeption("Not Found");
		}
		return !consultas.isEmpty() ? ResponseEntity.ok(consultas) :
			ResponseEntity.noContent().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Consulta> salvar(@Valid @RequestBody Consulta consulta, HttpServletResponse response) {
		Consulta consultaSalva = repositorio.save(consulta);		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(consultaSalva.getId()).toUri();		
		response.setHeader("Location", uri.toASCIIString());		
		return ResponseEntity.created(uri).body(consultaSalva);
		
	}

	@GetMapping("/{id_consulta}")
	public ResponseEntity<Optional<Consulta>> buscarPeloCodigo(@PathVariable Long id_consulta) {
		Optional<Consulta> consulta = repositorio.findById(id_consulta);
		if ( consulta.isPresent()) { 
			return ResponseEntity.ok(consulta);
		} 
		return ResponseEntity.notFound().build();	
	}
	
	@DeleteMapping("/{id_consulta}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id_consulta) {
		repositorio.deleteById(id_consulta);
	}
	
	@PutMapping("/{id_consulta}")
	public ResponseEntity<Consulta> atualizar(@PathVariable Long id_consulta, @Valid @RequestBody Consulta consulta) {		
		Consulta consultaSalva = consultaService.atualizar(id_consulta, consulta);		
		return ResponseEntity.ok(consultaSalva);
	}
	
	
}
