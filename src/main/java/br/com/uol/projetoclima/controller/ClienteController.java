package br.com.uol.projetoclima.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uol.projetoclima.apiTerceiros.ApiIPVigilante;
import br.com.uol.projetoclima.apiTerceiros.ApiMetaWeather;
import br.com.uol.projetoclima.dto.IPVigilante;
import br.com.uol.projetoclima.entity.Cliente;
import br.com.uol.projetoclima.json.RespostaAPI;
import br.com.uol.projetoclima.service.ClienteService;
import br.com.uol.projetoclima.service.ClimaService;
import br.com.uol.projetoclima.util.ValidaCliente;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@RestController
@RequestMapping("/cliente")
@Api(tags = {"REST Cliente"})
@SwaggerDefinition(tags = {
    @Tag(name = "REST Cliente", description = "REST Cliente")
})
public class ClienteController {
	
	@Autowired
	private ValidaCliente validaCliente;

	@Autowired
	private ClienteService serviceCliente;
	
	@Autowired
	private ClimaService serviceClima;
	
	@Autowired
	private ApiIPVigilante apiIpVigilante;
	
	@Autowired
	private ApiMetaWeather apiMetaWeather;
	
	private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

	@GetMapping
	@ApiOperation(value = "Busca todos os Clientes cadastrados")
	public ResponseEntity<List<Cliente>> listaTodos() {
		logger.info("metodo:GET Listando todos os Clientes");
		return ResponseEntity.ok(serviceCliente.listaTodos());
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Busca Cliente por id")
	public ResponseEntity<Optional<Cliente>> buscaCliente(@PathVariable long id) {
		
		logger.info("metodo:GET Buscando cliente por id");
		Optional<Cliente> clienteBuscado = serviceCliente.buscaPorId(id);

		if (!clienteBuscado.isPresent()) {
			logger.info("Cliente com id:{} não encontrado",id);
			return ResponseEntity.notFound().build();
		}
		
		logger.info("Retornando cliente com id:{}",id);
		return ResponseEntity.ok(clienteBuscado);
	}

	@PostMapping
	@ApiOperation(value = "Cadastra um novo Cliente")
	public ResponseEntity<RespostaAPI> criaCliente(@RequestBody Cliente cliente, HttpServletRequest request) {
		
		RespostaAPI resposta = new RespostaAPI();
		
		logger.info("metodo:POST Criando novo cliente");
		try {
		
			Cliente clienteSalvo = serviceCliente.salvar(cliente);
			
			IPVigilante ipVigilante = apiIpVigilante.buscaLocalizacao(request);
			
			if(ObjectUtils.isEmpty(ipVigilante)) {
				
				resposta.setMensagem("Cliente criado, sem Clima devido ao IP inválido!" );
				resposta.setId(clienteSalvo.getId());
				return ResponseEntity.ok(resposta);
			}
			
			apiMetaWeather.cadastraClima(ipVigilante, clienteSalvo, serviceClima);
			
			resposta.setMensagem("Cliente criado com sucesso!");
			resposta.setId(clienteSalvo.getId());
			logger.info("Cliente criado com id: {}", clienteSalvo.getId());
			
		} catch (Exception e) {
			resposta.setMensagem("Falha ao criar Cliente! \n Erro: " + e.getMessage());
			logger.error("[Falha ao criar Cliente!] Erro: {}", e.getMessage());
			
			return ResponseEntity.badRequest().body(resposta);
		}
		
		return ResponseEntity.ok(resposta);
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Atualiza os dados de um Cliente")
	public ResponseEntity<RespostaAPI> atualizaCliente(@PathVariable long id, @RequestBody Cliente cliente) {

		RespostaAPI resposta = new RespostaAPI();
		logger.info("metodo:PUT Alterando cliente");
		
		try {
			Optional<Cliente> clienteBuscado = serviceCliente.buscaPorId(id);
			if (!clienteBuscado.isPresent()) {
				logger.info("Cliente com id:{} não encontrado",id);
				return ResponseEntity.notFound().build();
			}
			
			String nomeCliente = validaCliente.validaNomeCliente(clienteBuscado, cliente);

			cliente.setId(id);
			cliente.setNome(nomeCliente);

			serviceCliente.salvar(cliente);
			
			resposta.setMensagem("Cliente alterado com sucesso!");
			resposta.setId(id);
			logger.info("Cliente com id: {} alterado com sucesso",id);
			
		} catch (Exception e) {
			resposta.setMensagem("Falha ao alterar o cliente! \n Erro:" + e.getMessage());
			resposta.setId(id);
			logger.error("[Falha ao alterar cliente com id: {}] Erro: {}",id, e.getMessage());
			
			
			return ResponseEntity.badRequest().body(resposta);
		}
		
		return ResponseEntity.ok(resposta);		
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deleta os dados de um Cliente")
	public ResponseEntity<RespostaAPI> deletarCliente(@PathVariable long id){
		
		RespostaAPI resposta = new RespostaAPI();
		logger.info("metodo:DELETE Deletando cliente");
		try {
			
			Optional<Cliente> clienteBuscado = serviceCliente.buscaPorId(id);
			
			if (!clienteBuscado.isPresent()) {
				logger.info("Cliente com id:{} não encontrado",id);
				return ResponseEntity.notFound().build();
			}
			
			serviceCliente.deletar(id);
			
			resposta.setMensagem("Cliente deletado com sucesso!");
			resposta.setId(id);
			logger.info("Cliente com id: {} deletado com sucesso",id);
			
		} catch (Exception e) {
			resposta.setMensagem("Falha ao deletar o cliente! \n Erro: " + e.getMessage());
			resposta.setId(id);
			logger.error("[Falha ao deletar cliente com id: {}] Erro: {}",id, e.getMessage());
			
			return ResponseEntity.badRequest().body(resposta);
		}
		
		return ResponseEntity.ok(resposta);
		
	}

}
