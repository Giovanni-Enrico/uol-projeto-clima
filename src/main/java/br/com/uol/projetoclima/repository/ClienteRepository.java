package br.com.uol.projetoclima.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.uol.projetoclima.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	List<Cliente> findAll();
	
	Optional<Cliente> findById(Long id);
	
	Cliente saveAndFlush(Optional<Cliente> optional);
	
	void deleteById(Long id);

}
