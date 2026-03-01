package supermercado.servidor.controlador;

import supermercado.servidor.modelo.Categoria;
import supermercado.servidor.servicio.CategoriaServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// controlador de categorias
@RestController
@RequestMapping("/api/categorias")
public class CategoriaControlador {

	private CategoriaServicio categoriaServicio;

	// me dijeron que es mejor inyectar por constructor
	public CategoriaControlador(CategoriaServicio categoriaServicio) {
		this.categoriaServicio = categoriaServicio;
	}

	@GetMapping
	public List<Categoria> listarTodas() {
		return categoriaServicio.listarTodas();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Categoria> obtenerPorId(@PathVariable Long id) {
		Optional<Categoria> categoria = categoriaServicio.buscarPorId(id);
		if (categoria.isPresent()) {
			return ResponseEntity.ok(categoria.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public Categoria crear(@RequestBody Categoria categoria) {
		return categoriaServicio.guardar(categoria);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Categoria> modificar(@PathVariable Long id,
	                                           @RequestBody Categoria datosNuevos) {
		Optional<Categoria> busqueda = categoriaServicio.buscarPorId(id);

		if (busqueda.isPresent()) {
			Categoria cat = busqueda.get();
			cat.setNombre(datosNuevos.getNombre());
			cat.setDescripcion(datosNuevos.getDescripcion());
			return ResponseEntity.ok(categoriaServicio.guardar(cat));
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		if (!categoriaServicio.existe(id)) {
			return ResponseEntity.notFound().build();
		}
		categoriaServicio.eliminar(id);
		return ResponseEntity.ok().build();
	}
}
