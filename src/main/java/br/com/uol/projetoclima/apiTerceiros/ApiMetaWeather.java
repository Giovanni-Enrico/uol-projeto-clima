package br.com.uol.projetoclima.apiTerceiros;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.uol.projetoclima.dto.Cidade;
import br.com.uol.projetoclima.dto.ClimaConsolidado;
import br.com.uol.projetoclima.dto.IPVigilante;
import br.com.uol.projetoclima.entity.Cliente;
import br.com.uol.projetoclima.entity.Clima;
import br.com.uol.projetoclima.service.ClimaService;

@Component
public class ApiMetaWeather {
	
	private final DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("yyyy/MM/dd"); 
	
    private RestTemplate restTemplate = new RestTemplate();
    private final String METAWEATHER_URL = "https://www.metaweather.com/api/location/";
	private static final Logger logger = LoggerFactory.getLogger(ApiMetaWeather.class);
	
	public void cadastraClima (IPVigilante ipVigilante, Cliente cliente, ClimaService serviceClima) {
		
		String urlBuscaCidades = montaUrlBuscaCidades(ipVigilante);
		Cidade cidade = buscaCidade(urlBuscaCidades);
		
		String urlBuscaClimaConsolidado = montaUrlBuscaClimaConsolidado(cidade);
		ClimaConsolidado climaConsolidado = buscaClimaConsolidado(urlBuscaClimaConsolidado);
		
		Clima clima = geraCLima(cidade, climaConsolidado, cliente);
		
		serviceClima.salvar(clima);
		
		logger.info("Salvando clima da cidade: {}", clima.getCidade());
	}
	
	private Clima geraCLima(Cidade cidade, ClimaConsolidado climaConsolidado, Cliente cliente) {
		
		String data = LocalDate.now().format(formatoData);
		
		Clima clima = new Clima(data,
								cidade.getTitle(),
								climaConsolidado.getMax_temp(),
								climaConsolidado.getMin_temp(),
								cliente);
		
		return clima;
	}
	
	
	private Cidade buscaCidade(String urlBuscaCidades){
		
		logger.info("[MetaWather] Buscando cidades na API: {}", urlBuscaCidades);
		
		Cidade[] cidades = restTemplate.getForObject(urlBuscaCidades, Cidade[].class);
		
		return 	cidades[0];
	}
	
	private ClimaConsolidado buscaClimaConsolidado(String urlBuscaClimaConsolidado) {
		
		logger.info("[MetaWather] Buscando clima na API: {}", urlBuscaClimaConsolidado);
		
		ClimaConsolidado[] climaConsolidados = restTemplate.getForObject(urlBuscaClimaConsolidado,
																		   ClimaConsolidado[].class);
		ClimaConsolidado climaConsolidado = climaConsolidados[0];
		
		return climaConsolidado;
	}
	
	private String montaUrlBuscaCidades (IPVigilante ipVigilante) {
		
		StringBuilder url = new StringBuilder();
		url.append(METAWEATHER_URL);
		url.append("search/?lattlong=");
		url.append(ipVigilante.getData().getLatitude());
		url.append(",");
		url.append(ipVigilante.getData().getLongitude());
		
		return url.toString();
	}
	
	private String montaUrlBuscaClimaConsolidado (Cidade cidade) {
		
		StringBuilder url = new StringBuilder();
		url.append(METAWEATHER_URL);
		url.append(cidade.getWoeid());
		url.append("/");
		url.append(LocalDate.now().format(formatoData));

		return url.toString();
	}
	
}
