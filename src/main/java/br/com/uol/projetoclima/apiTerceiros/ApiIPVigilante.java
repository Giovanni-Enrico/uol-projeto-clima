package br.com.uol.projetoclima.apiTerceiros;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.uol.projetoclima.dto.IPVigilante;

@Component
public class ApiIPVigilante {
	
	private final String IPVIGILANTE_URL = "https://ipvigilante.com/";
  
    private RestTemplate restTemplate = new RestTemplate();
    
	private static final Logger logger = LoggerFactory.getLogger(ApiIPVigilante.class);
	
	public IPVigilante buscaLocalizacao (HttpServletRequest request) {
		
		String ip = request.getRemoteAddr();
		try {
			logger.info("[IPVigilante] Buscando localização do IP pela API {}",IPVIGILANTE_URL.concat(ip));
			IPVigilante ipVigilante = restTemplate.getForObject(IPVIGILANTE_URL.concat(ip), IPVigilante.class);
			return ipVigilante;
		} catch (Exception e) {
			throw new java.lang.RuntimeException("O IP utilizado é invalido!");
		}

	}

}
