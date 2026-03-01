package supermercado.servidor.servicio;

import supermercado.servidor.modelo.Producto;
import supermercado.servidor.repositorio.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServicio {

    @Autowired
    private ProductoRepositorio productoRepo;

    public List<Producto> obtenerTodos() {
        return productoRepo.findAll();
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepo.findById(id);
    }

    // Comprueba que el precio no sea negativo antes de guardar
    public Producto guardar(Producto producto) {
        if (producto.getPrecio() < 0) {
            throw new RuntimeException("El precio no puede ser negativo");
        }
        return productoRepo.save(producto);
    }

    public void borrar(Long id) {
        productoRepo.deleteById(id);
    }

    public boolean existe(Long id) {
        return productoRepo.existsById(id);
    }
}
