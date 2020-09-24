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

import br.edu.unidep.ApiES.model.Paciente;
import br.edu.unidep.ApiES.model.Pessoa;
import br.edu.unidep.ApiES.repository.PacienteRepository;
import br.edu.unidep.ApiES.service.PacienteService;
import error.ResourceNotFoundExeption;



@RestController
@RequestMapping("/pacientes")
public class PacienteResource {

	@Autowired
	private PacienteRepository repositorio;
	
	@Autowired 
	private PacienteService pacienteService;
	
	@GetMapping
	public ResponseEntity<?> listar() {
		List<Paciente> pacientes = repositorio.findAll();
		if (pacientes == null) {
			throw new ResourceNotFoundExeption("Not Found");
		}
		return !pacientes.isEmpty() ? ResponseEntity.ok(pacientes) :
			ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Paciente> salvar(@Valid @RequestBody Paciente paciente, HttpServletResponse response) {
		Paciente pacienteSalvo = repositorio.save(paciente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pacienteSalvo.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		return ResponseEntity.created(uri).body(pacienteSalvo);
	}
	
	@GetMapping("/{id_paciente}")
	public ResponseEntity<Optional<Paciente>> buscarPeloCodigo(@PathVariable Long id_paciente) {
		Optional<Paciente> paciente = repositorio.findById(id_paciente);
		if (paciente.isPresent()) {
			return ResponseEntity.ok(paciente);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id_paciente}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id_paciente) {
		repositorio.deleteById(id_paciente);
	}

	@PutMapping("/{id_paciente}")
	public ResponseEntity<Paciente> atualizar(@PathVariable Long id_paciente, @Valid @RequestBody Paciente paciente) {
		Paciente pacienteSalvo = pacienteService.atualizar(id_paciente, paciente);
		return ResponseEntity.ok(pacienteSalvo);
	}
}
