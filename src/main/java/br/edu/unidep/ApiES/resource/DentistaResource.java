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

import br.edu.unidep.ApiES.model.Dentista;
import br.edu.unidep.ApiES.repository.DentistaRepository;
import br.edu.unidep.ApiES.service.DentistaService;
import error.ResourceNotFoundExeption;

@RestController
@RequestMapping("/dentistas")
public class DentistaResource {
	@Autowired
	private DentistaRepository repositorio;
	
	@Autowired
	private DentistaService dentistaService;
	
	@GetMapping
	public ResponseEntity<?> listar() {
		List<Dentista> dentistas = repositorio.findAll();
		if (dentistas == null) {
			throw new ResourceNotFoundExeption("Not Found");
		}
		return !dentistas.isEmpty() ? ResponseEntity.ok(dentistas) :
			ResponseEntity.noContent().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Dentista> salvar(@Valid @RequestBody Dentista dentista, HttpServletResponse reponse){
		Dentista dentistaSalva = repositorio.save(dentista);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}").buildAndExpand(dentistaSalva.getId()).toUri();
		reponse.setHeader("Location", uri.toASCIIString());
		return ResponseEntity.created(uri).body(dentistaSalva);
	}
	
	@GetMapping("/{codigo_dentista}")
	public ResponseEntity<Optional<Dentista>> buscarPeloCodigo(@PathVariable Long codigo_dentista) {
		Optional<Dentista> dentista = repositorio.findById(codigo_dentista);
		if (dentista.isPresent()) {
			return ResponseEntity.ok(dentista);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo_dentista}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo_dentista) {
		repositorio.deleteById(codigo_dentista);
	}
	
	@PutMapping("/{codigo_dentista}")
	public ResponseEntity<Dentista> atualizar(@PathVariable Long codigo_dentista, @Valid @RequestBody Dentista dentista) {
		Dentista dentistaSalva = dentistaService.atualizar(codigo_dentista, dentista);
		return ResponseEntity.ok(dentistaSalva);				
	}
	
	
}
