package supermercado.servidor.modelo;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

// Entidad que representa una categoria de productos del supermercado
@Entity
@Table(name = "categorias")
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nombre;

	private String descripcion;

	// una categoria tiene muchos productos
	@OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Producto> productos;

	public Categoria() {}

	public Categoria(String nombre, String descripcion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	// getters y setters
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }

	public String getDescripcion() { return descripcion; }
	public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

	public List<Producto> getProductos() { return productos; }
	public void setProductos(List<Producto> productos) { this.productos = productos; }

	@Override
	public String toString() {
		return "Categoria{" + "id=" + id + ", nombre='" + nombre + "'}";
	}
}
