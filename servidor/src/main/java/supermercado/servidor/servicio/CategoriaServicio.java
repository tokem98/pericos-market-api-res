package supermercado.servidor.servicio;

import supermercado.servidor.modelo.Categoria;
import supermercado.servidor.repositorio.CategoriaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServicio {

	@Autowired
	private CategoriaRepositorio repo;

	public List<Categoria> listarTodas() {
		return repo.findAll();
	}

	public Optional<Categoria> buscarPorId(Long id) {
		return repo.findById(id);
	}

	public Categoria guardar(Categoria cat) {
		return repo.save(cat);
	}

	// no se si deberia controlar algo mas aqui, preguntar al profesor
	public void eliminar(Long id) {
		repo.deleteById(id);
	}

	public boolean existe(Long id) {
		return repo.existsById(id);
	}
}
