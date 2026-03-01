package supermercado.servidor.controlador;

import supermercado.servidor.modelo.Producto;
import supermercado.servidor.servicio.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// endpoints REST de productos
@RestController
@RequestMapping("/api/productos")
public class ProductoControlador {

    @Autowired
    private ProductoServicio productoServicio;

    @GetMapping
    public List<Producto> listarTodos() {
        return productoServicio.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        Optional<Producto> resultado = productoServicio.obtenerPorId(id);
        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get());
        }
        // si no existe devuelvo 404
        return ResponseEntity.notFound().build();
    }

    // Crea un producto nuevo y devuelve error si el precio es negativo
    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        try {
            Producto guardado = productoServicio.guardar(producto);
            return ResponseEntity.ok(guardado);
        } catch (RuntimeException e) {
            // Si el precio es negativo, el servicio lanza una excepcion y devolvemos error 400
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> modificar(@PathVariable Long id,
                                              @RequestBody Producto datosNuevos) {
        Optional<Producto> busq = productoServicio.obtenerPorId(id);
        if (!busq.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Producto prod = busq.get();
        prod.setNombre(datosNuevos.getNombre());
        prod.setPrecio(datosNuevos.getPrecio());
        prod.setCategoria(datosNuevos.getCategoria());
        return ResponseEntity.ok(productoServicio.guardar(prod));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!productoServicio.existe(id)) {
            return ResponseEntity.notFound().build();
        }
        productoServicio.borrar(id);
        return ResponseEntity.ok().build();
    }
}
