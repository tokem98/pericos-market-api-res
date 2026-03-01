package supermercado.servidor.repositorio;

import supermercado.servidor.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Long> {
	// los metodos basicos ya los hereda de JpaRepository
}
