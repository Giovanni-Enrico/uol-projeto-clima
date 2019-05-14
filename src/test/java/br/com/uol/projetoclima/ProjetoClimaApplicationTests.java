package br.com.uol.projetoclima;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import br.com.uol.projetoclima.entity.Cliente;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjetoClimaApplicationTests {
	  
  	@Autowired
	private WebApplicationContext webAppContext;

	private MockMvc mockMvc;

	ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webAppContext).build();
	}

	@Test
	public void testePostCadastraClienteBadRequest() throws Exception {
		this.mockMvc.perform(post("/cliente"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testePostCadastraClienteOk() throws Exception {
		this.mockMvc.perform(post("/cliente")
				.content(mapper.writeValueAsString(new Cliente("Teste", 25)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("OK"))
				.andExpect(jsonPath("$.mensagem").isString())
				.andExpect(jsonPath("$.id").isNumber());
	}
	
	@Test
	public void testePutAlteraClienteNotFound() throws Exception {
		this.mockMvc.perform(put("/cliente/98")
				.content(mapper.writeValueAsString(new Cliente("Teste Put", 22)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	
	@Test
	public void testePutAlteraClienteOk() throws Exception {
		
		MvcResult mvcResult = this.mockMvc.perform(post("/cliente")
				.content(mapper.writeValueAsString(new Cliente("Teste", 25)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		
		String response = mvcResult.getResponse().getContentAsString();
		Integer idCriado = JsonPath.parse(response).read("$.id");
		
		this.mockMvc.perform(put("/cliente/"+idCriado)
				.content(mapper.writeValueAsString(new Cliente("Teste Put", 22)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("OK"))
				.andExpect(jsonPath("$.mensagem").isString())
				.andExpect(jsonPath("$.id").value(idCriado));
	}
	
	@Test
	public void testeGetBuscaClientesOK() throws Exception {
		MvcResult mvcResult = this.mockMvc.perform(get("/cliente"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		Assert.assertEquals("application/json;charset=UTF-8",
				mvcResult.getResponse().getContentType());
	}

	@Test
	public void testeGetBuscaClientePorIdNotFound() throws Exception {
		this.mockMvc.perform(get("/cliente/99"))
				.andDo(print())
				.andExpect(status().isNotFound());

	}

	
	@Test
	public void testeGetBuscaClientePorIdOK() throws Exception {
		
		MvcResult mvcResult = this.mockMvc.perform(post("/cliente")
				.content(mapper.writeValueAsString(new Cliente("Teste Get", 25)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		
		String response = mvcResult.getResponse().getContentAsString();
		Integer idCriado = JsonPath.parse(response).read("$.id");
		
		this.mockMvc.perform(get("/cliente/"+idCriado))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(idCriado))
				.andExpect(jsonPath("$.nome").value("Teste Get"))
				.andExpect(jsonPath("$.idade").value(25));
	}
	
	@Test
	public void testeDeleteApagaClienteNotFound() throws Exception {
		this.mockMvc.perform(delete("/cliente/99"))
				.andDo(print())
				.andExpect(status().isNotFound());
	}


	@Test
	public void testeDeleteApagaClienteOK() throws Exception {
		
		MvcResult mvcResult = this.mockMvc.perform(post("/cliente")
				.content(mapper.writeValueAsString(new Cliente("Teste Delete", 15)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		
		String response = mvcResult.getResponse().getContentAsString();
		Integer idCriado = JsonPath.parse(response).read("$.id");
		
		this.mockMvc.perform(delete("/cliente/"+idCriado))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("OK"))
				.andExpect(jsonPath("$.mensagem").isString())
				.andExpect(jsonPath("$.id").value(idCriado));
	}
	
}
