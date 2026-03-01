package supermercado.cliente.modelo;

// Modelo que representa una categoria del supermercado en el lado del cliente
public class Categoria {

    private Long id;
    private String nombre;
    private String descripcion;

    public Categoria() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String desc) { this.descripcion = desc; }

    @Override
    public String toString() {
        return "[" + id + "] " + nombre + " (" + descripcion + ")";
    }
}
