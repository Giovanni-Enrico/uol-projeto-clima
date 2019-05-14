package br.com.uol.projetoclima.util;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import br.com.uol.projetoclima.entity.Cliente;

@Component
public class ValidaCliente {
	
	public String validaNomeCliente(Optional<Cliente> clienteBuscado, Cliente clientePut) {
		
		Cliente clienteExistente = clienteBuscado.get();
		
		if(ObjectUtils.isEmpty(clientePut.getNome())) clientePut.setNome(clienteExistente.getNome());
		
		return clientePut.getNome();
		
	}

}
