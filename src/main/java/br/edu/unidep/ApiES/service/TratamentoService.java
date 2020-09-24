package br.edu.unidep.ApiES.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unidep.ApiES.model.Tratamento;
import br.edu.unidep.ApiES.repository.TratamentoRepository;


@Service
public class TratamentoService {

	@Autowired
	private TratamentoRepository repositorio;
	
	public Tratamento atualizar(Long codigo, Tratamento tratamentos) {
		Optional<Tratamento> tratamentosSalvo = repositorio.findById(codigo);
		BeanUtils.copyProperties(tratamentos, tratamentosSalvo.get(), "codigo");
		return repositorio.save(tratamentosSalvo.get());
	}
}
