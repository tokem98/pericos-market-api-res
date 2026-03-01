package supermercado.cliente.servicio;

import org.json.JSONArray;
import org.json.JSONObject;
import supermercado.cliente.modelo.Categoria;
import supermercado.cliente.modelo.Producto;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

// Servicio que se encarga de hacer las peticiones HTTP al servidor para gestionar productos
public class ProductoServicio {

    private final String URL_BASE = "http://localhost:12345/api/productos";
    private HttpClient httpClient;

    public ProductoServicio() {
        httpClient = HttpClient.newHttpClient();
    }

    // Convierte un JSONObject a un objeto Producto para no repetir codigo
    private Producto jsonAProducto(JSONObject obj) {
        Producto p = new Producto();
        p.setId(obj.getLong("id"));
        p.setNombre(obj.getString("nombre"));
        p.setPrecio(obj.getDouble("precio"));
        if (obj.has("categoria") && !obj.isNull("categoria")) {
            JSONObject catJson = obj.getJSONObject("categoria");
            Categoria c = new Categoria();
            c.setId(catJson.getLong("id"));
            c.setNombre(catJson.getString("nombre"));
            p.setCategoria(c);
        }
        return p;
    }

    public List<Producto> listarTodos() {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(URL_BASE))
                    .GET()
                    .build();

            HttpResponse<String> res = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
            JSONArray arr = new JSONArray(res.body());
            List<Producto> productos = new ArrayList<>();

            for (int i = 0; i < arr.length(); i++) {
                productos.add(jsonAProducto(arr.getJSONObject(i)));
            }
            return productos;
        } catch (Exception e) {
            System.err.println("No se pudo conectar: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Producto buscarPorId(Long id) {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(URL_BASE + "/" + id))
                    .GET()
                    .build();

            HttpResponse<String> res = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() != 200) {
                return null;
            }
            return jsonAProducto(new JSONObject(res.body()));
        } catch (Exception e) {
            System.err.println("Error buscando producto: " + e.getMessage());
            return null;
        }
    }

    public Producto crear(Producto producto) {
        try {
            JSONObject json = new JSONObject();
            json.put("nombre", producto.getNombre());
            json.put("precio", producto.getPrecio());
            if (producto.getCategoria() != null) {
                JSONObject catJson = new JSONObject();
                catJson.put("id", producto.getCategoria().getId());
                json.put("categoria", catJson);
            }

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(URL_BASE))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                    .build();

            HttpResponse<String> res = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() != 200) {
                System.err.println("El servidor devolvio error: " + res.statusCode());
                return null;
            }
            return jsonAProducto(new JSONObject(res.body()));
        } catch (Exception e) {
            System.err.println("Error creando producto: " + e.getMessage());
            return null;
        }
    }

    public boolean modificar(Long id, Producto producto) {
        try {
            JSONObject json = new JSONObject();
            json.put("nombre", producto.getNombre());
            json.put("precio", producto.getPrecio());
            if (producto.getCategoria() != null) {
                JSONObject catJson = new JSONObject();
                catJson.put("id", producto.getCategoria().getId());
                json.put("categoria", catJson);
            }

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(URL_BASE + "/" + id))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json.toString()))
                    .build();

            HttpResponse<String> res = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
            return res.statusCode() == 200;
        } catch (Exception e) {
            System.err.println("Error modificando: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(Long id) {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(URL_BASE + "/" + id))
                    .DELETE()
                    .build();

            HttpResponse<String> res = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
            return res.statusCode() == 200;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
