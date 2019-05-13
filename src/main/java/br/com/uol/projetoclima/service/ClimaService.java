package br.com.uol.projetoclima.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.uol.projetoclima.entity.Clima;
import br.com.uol.projetoclima.repository.ClimaRepository;

@Service
public class ClimaService {
	
	@Autowired
	private ClimaRepository repository;

	public List<Clima> buscaTodos() {
		return repository.findAll();
	}

	public Clima salvar(Clima clima) {
		return repository.saveAndFlush(clima);
	}

}
