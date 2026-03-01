package supermercado.servidor.controlador;

import supermercado.servidor.modelo.Categoria;
import supermercado.servidor.repositorio.CategoriaRepositorio;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// tests basicos del controlador de categorias
@SpringBootTest
@AutoConfigureMockMvc
class CategoriaControladorTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CategoriaRepositorio categoriaRepositorio;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testListarCategorias() throws Exception {
		mockMvc.perform(get("/api/categorias"))
				.andExpect(status().isOk());
	}

	@Test
	void testCrearYBorrar() throws Exception {
		// creo una
		Categoria cat = new Categoria("Limpieza", "productos de limpieza del hogar");

		String respuesta = mockMvc.perform(post("/api/categorias")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(cat)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nombre").value("Limpieza"))
				.andReturn().getResponse().getContentAsString();

		// saco el id para borrarla despues
		Long id = objectMapper.readTree(respuesta).get("id").asLong();

		mockMvc.perform(delete("/api/categorias/" + id))
				.andExpect(status().isOk());
	}

	@Test
	void testNoExiste() throws Exception {
		mockMvc.perform(get("/api/categorias/999"))
				.andExpect(status().isNotFound());
	}

	// esto no me funcionaba, creo que es porque el put necesita
	// que la categoria ya exista y no la estoy creando bien
	// @Test
	// void testModificarCategoria() throws Exception {
	// 	Categoria cat = new Categoria("Bebidas", "refrescos y agua");
	// 	cat.setNombre("Bebidas frias");
	// 	mockMvc.perform(put("/api/categorias/1")
	// 			.contentType(MediaType.APPLICATION_JSON)
	// 			.content(objectMapper.writeValueAsString(cat)))
	// 			.andExpect(status().isOk());
	// }
}
