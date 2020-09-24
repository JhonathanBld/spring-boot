package br.edu.unidep.ApiES.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unidep.ApiES.model.Consulta;
import br.edu.unidep.ApiES.repository.ConsultaRepository;


@Service
public class ConsultaService {
	
	@Autowired
	private ConsultaRepository repositorio;

	public Consulta atualizar(Long codigo, Consulta consulta) {		
		Optional<Consulta> consultaSalva = repositorio.findById(codigo);		
		BeanUtils.copyProperties(consulta, consultaSalva.get(), "codigo");
		return repositorio.save(consultaSalva.get());
	}
}
