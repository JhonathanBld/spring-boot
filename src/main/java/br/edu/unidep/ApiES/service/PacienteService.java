package br.edu.unidep.ApiES.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unidep.ApiES.model.Paciente;
import br.edu.unidep.ApiES.repository.PacienteRepository;

@Service
public class PacienteService {

	@Autowired
	private PacienteRepository repositorio;
	
	public Paciente atualizar(Long codigo, Paciente paciente) {
		Optional<Paciente> pacienteSalvo = repositorio.findById(codigo);
		BeanUtils.copyProperties(paciente, pacienteSalvo.get(), "codigo");
		return repositorio.save(pacienteSalvo.get());
	}
}
