package br.com.uol.projetoclima.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.uol.projetoclima.entity.Clima;

public interface ClimaRepository extends JpaRepository<Clima, Long>{
	
	List<Clima> findAll();
	
	Clima saveAndFlush(Optional<Clima> optional);

}
