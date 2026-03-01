package supermercado.cliente.modelo;

// Modelo que representa un producto del supermercado en el lado del cliente
public class Producto {

    private Long id;
    private String nombre;
    private double precio;
    private Categoria categoria;

    public Producto() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    @Override
    public String toString() {
        String cat = (categoria != null) ? categoria.getNombre() : "sin categoria";
        return id + ". " + nombre + " - " + precio + "eur (" + cat + ")";
    }
}
