package br.com.uol.projetoclima.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.uol.projetoclima.entity.Cliente;
import br.com.uol.projetoclima.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	public List<Cliente> listaTodos(){
		return repository.findAll();
	}
	
	public Optional<Cliente> buscaPorId(Long id) {
		return repository.findById(id);
	}
	
    
	public Cliente salvar(Cliente cliente) {
		return repository.save(cliente);
	}
	
	
	public void deletar(Long id) {
		if(repository.findById(id).isPresent())
			repository.deleteById(id);
	}
	
}
