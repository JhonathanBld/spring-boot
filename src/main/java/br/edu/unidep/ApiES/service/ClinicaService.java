package br.edu.unidep.ApiES.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unidep.ApiES.model.Clinica;
import br.edu.unidep.ApiES.repository.ClinicaRepository;


@Service
public class ClinicaService {
	
	@Autowired
	private ClinicaRepository repositorio;

	public Clinica atualizar(Long codigo, Clinica clinica) {		
		Optional<Clinica> clinicaSalva = repositorio.findById(codigo);		
		BeanUtils.copyProperties(clinica, clinicaSalva.get(), "codigo");
		return repositorio.save(clinicaSalva.get());
	}
}
