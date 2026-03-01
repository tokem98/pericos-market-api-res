package supermercado.servidor.repositorio;

import supermercado.servidor.modelo.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

// acceso a datos de categorias
public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {
}
