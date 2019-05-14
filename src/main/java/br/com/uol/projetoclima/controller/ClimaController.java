package br.com.uol.projetoclima.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uol.projetoclima.entity.Clima;
import br.com.uol.projetoclima.service.ClimaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@RestController
@RequestMapping("/clima")
@Api(tags = {"REST Clima"})
@SwaggerDefinition(tags = {
    @Tag(name = "REST Clima", description = "REST Clima")
})
public class ClimaController {
	
	@Autowired
	private ClimaService service;
	
	private static final Logger logger = LoggerFactory.getLogger(ClimaController.class);
	
	@GetMapping
	@ApiOperation(value = "Busca todos os Climas cadastrados")
	public ResponseEntity<List<Clima>> buscaTodos(){
		logger.info("metodo:GET Listando todos os Climas dos Clientes");
		return ResponseEntity.ok(service.buscaTodos());
		
	}

}
