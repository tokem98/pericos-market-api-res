package supermercado.servidor.controlador;

import supermercado.servidor.modelo.Categoria;
import supermercado.servidor.modelo.Producto;
import supermercado.servidor.repositorio.CategoriaRepositorio;
import supermercado.servidor.repositorio.ProductoRepositorio;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

// Tests del controlador de productos
@SpringBootTest
@AutoConfigureMockMvc
class ProductoControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @Autowired
    private ObjectMapper objectMapper;

    private Categoria categoriaTest;

    // Crea una categoria de prueba antes de cada test porque los productos la necesitan
    @BeforeEach
    void setUp() {
        categoriaTest = new Categoria("Lacteos", "leche queso yogur etc");
        categoriaTest = categoriaRepositorio.save(categoriaTest);
    }

    @Test
    void listarProductosDevuelve200() throws Exception {
        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk());
    }

    @Test
    void crearProducto() throws Exception {
        Producto p = new Producto("Leche entera", 1.20, categoriaTest);

        mockMvc.perform(post("/api/productos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(p)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Leche entera"))
                .andExpect(jsonPath("$.precio").value(1.20));
    }

    @Test
    void obtenerProductoQueNoExiste() throws Exception {
        // Usamos un ID muy alto para asegurar que no existe
        mockMvc.perform(get("/api/productos/77777"))
                .andExpect(status().isNotFound());
    }

    @Test
    void crearYModificarProducto() throws Exception {
        // primero creo el producto
        Producto p = new Producto("Yogur griego", 0.85, categoriaTest);
        p = productoRepositorio.save(p);

        // cambio el precio y el nombre
        p.setNombre("Yogur natural");
        p.setPrecio(0.65);

        mockMvc.perform(put("/api/productos/" + p.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(p)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Yogur natural"))
                .andExpect(jsonPath("$.precio").value(0.65));
    }

    @Test
    void eliminarProductoQueNoExiste() throws Exception {
        // Intentar borrar un producto que no existe debe devolver 404
        mockMvc.perform(delete("/api/productos/88888"))
                .andExpect(status().isNotFound());
    }
}
