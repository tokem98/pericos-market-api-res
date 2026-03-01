package supermercado.cliente.servicio;

import org.json.JSONArray;
import org.json.JSONObject;
import supermercado.cliente.modelo.Categoria;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

// esto lo saque de los apuntes de clase y lo adapte para categorias
public class CategoriaServicio {

    private static final String URL_BASE = "http://localhost:12345/api/categorias";
    private HttpClient cliente;

    public CategoriaServicio() {
        this.cliente = HttpClient.newHttpClient();
    }

    public List<Categoria> listarTodas() {
        try {
            HttpRequest peticion = HttpRequest.newBuilder()
                    .uri(URI.create(URL_BASE))
                    .GET()
                    .build();

            HttpResponse<String> resp = cliente.send(peticion, HttpResponse.BodyHandlers.ofString());
            // System.out.println(resp.body());

            JSONArray array = new JSONArray(resp.body());
            List<Categoria> lista = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Categoria cat = new Categoria();
                cat.setId(obj.getLong("id"));
                cat.setNombre(obj.getString("nombre"));
                // puede que no tenga descripcion
                if (obj.has("descripcion") && !obj.isNull("descripcion")) {
                    cat.setDescripcion(obj.getString("descripcion"));
                }
                lista.add(cat);
            }
            return lista;
        } catch (Exception e) {
            System.out.println("Error al conectar: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Categoria crear(Categoria categoria) {
        try {
            JSONObject json = new JSONObject();
            json.put("nombre", categoria.getNombre());
            json.put("descripcion", categoria.getDescripcion());

            HttpRequest peticion = HttpRequest.newBuilder()
                    .uri(URI.create(URL_BASE))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                    .build();

            HttpResponse<String> resp = cliente.send(peticion, HttpResponse.BodyHandlers.ofString());
            // asumo que si no peta es que ha ido bien

            JSONObject obj = new JSONObject(resp.body());
            Categoria resultado = new Categoria();
            resultado.setId(obj.getLong("id"));
            resultado.setNombre(obj.getString("nombre"));
            resultado.setDescripcion(obj.optString("descripcion", ""));
            return resultado;
        } catch (Exception e) {
            System.out.println("Error al crear: " + e.getMessage());
            return null;
        }
    }

    public boolean modificar(Long id, Categoria categoria) {
        try {
            JSONObject json = new JSONObject();
            json.put("nombre", categoria.getNombre());
            json.put("descripcion", categoria.getDescripcion());

            HttpRequest peticion = HttpRequest.newBuilder()
                    .uri(URI.create(URL_BASE + "/" + id))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json.toString()))
                    .build();

            HttpResponse<String> resp = cliente.send(peticion, HttpResponse.BodyHandlers.ofString());
            return resp.statusCode() == 200;
        } catch (Exception e) {
            System.out.println("Error al conectar: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(Long id) {
        try {
            HttpRequest peticion = HttpRequest.newBuilder()
                    .uri(URI.create(URL_BASE + "/" + id))
                    .DELETE()
                    .build();

            HttpResponse<String> resp = cliente.send(peticion, HttpResponse.BodyHandlers.ofString());
            return resp.statusCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
