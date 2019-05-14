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
				.content(mapper.writeValueAsString(new Cliente("Teste", 20)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("OK"))
				.andExpect(jsonPath("$.mensagem").isString());
	}
	
	@Test
	public void testePutAlteraClienteNotFound() throws Exception {
		MvcResult mvcResult = this.mockMvc.perform(put("/cliente/2"))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andReturn();

		Assert.assertEquals("application/json;charset=UTF-8",
				mvcResult.getResponse().getContentType());
	}

	
	@Test
	public void testePutAlteraClienteOk() throws Exception {
		this.mockMvc.perform(put("/cliente/1")
				.content(mapper.writeValueAsString(new Cliente("Teste Put", 22)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("OK"))
				.andExpect(jsonPath("$.mensagem").isString());
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
		this.mockMvc.perform(get("/cliente/2"))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andReturn();

	}

	
	@Test
	public void testeGetBuscaClientePorIdOK() throws Exception {
		MvcResult mvcResult = this.mockMvc.perform(get("/cliente/1"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		Assert.assertEquals("application/json;charset=UTF-8",
				mvcResult.getResponse().getContentType());
	}
	
	
	@Test
	public void testeDeleteApagaClienteNotFound() throws Exception {
		this.mockMvc.perform(delete("/cliente/2"))
				.andDo(print())
				.andExpect(status().isNotFound());
	}
	



	@Test
	public void testeDeleteApagaClienteOK() throws Exception {
		this.mockMvc.perform(delete("/cliente/1"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("OK"))
				.andExpect(jsonPath("$.mensagem").isString());
	}

}
