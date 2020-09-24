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

import br.edu.unidep.ApiES.model.Tratamento;
import br.edu.unidep.ApiES.repository.TratamentoRepository;
import br.edu.unidep.ApiES.service.TratamentoService;
import error.ResourceNotFoundExeption;

@RestController
@RequestMapping("/tratamentos")
public class TratamentoResource {
	
	@Autowired
	private TratamentoRepository repositorio;
	
	@Autowired
	private TratamentoService tratamentosService;
	
	
	@GetMapping
	public ResponseEntity<?> buscar() {
		List<Tratamento> tratamentos = repositorio.findAll();
		if (tratamentos == null) {
			throw new ResourceNotFoundExeption("Not Found");
		}
		return !tratamentos.isEmpty() ? ResponseEntity.ok(tratamentos) :
			ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Tratamento> salvar(@Valid @RequestBody Tratamento tratamento, HttpServletResponse response) {
		Tratamento tratamentosSalvo = repositorio.save(tratamento);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}").buildAndExpand(tratamentosSalvo.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		return ResponseEntity.created(uri).body(tratamentosSalvo);
	}
	
	@GetMapping("/{codigo_tratamento}")
	public ResponseEntity<Optional<Tratamento>> buscarPeloCodigo(@PathVariable Long codigo_tratamento){
		Optional<Tratamento> tratamentos = repositorio.findById(codigo_tratamento);
		if (tratamentos.isPresent()) {
			return ResponseEntity.ok(tratamentos);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo_tratamento}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo_tratamento) {
		repositorio.deleteById(codigo_tratamento);
	}
	
	@PutMapping("/{codigo_tratamento}")
	public ResponseEntity<Tratamento> alterar(@PathVariable Long codigo_tratamento, @Valid @RequestBody Tratamento tratamento) {
		Tratamento tratamentosSalvo = tratamentosService.atualizar(codigo_tratamento, tratamento);
		return ResponseEntity.ok(tratamentosSalvo);
	}
		
}
