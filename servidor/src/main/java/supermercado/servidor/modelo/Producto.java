package supermercado.servidor.modelo;

import jakarta.persistence.*;

/*
 * Entidad Producto - cada producto tiene nombre, precio y pertenece a una categoria
 */
@Entity
@Table(name = "productos")
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nombre;

	// El precio se valida en el servicio para que no sea negativo
	@Column(nullable = false)
	private double precio;

	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;

	public Producto() {
	}

	public Producto(String nombre, double precio, Categoria categoria) {
		this.nombre = nombre;
		this.precio = precio;
		this.categoria = categoria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria cat) {
		this.categoria = cat;
	}

	@Override
	public String toString() {
		return "ID: " + id + " | " + nombre + " | " + precio + "eur";
	}
}
