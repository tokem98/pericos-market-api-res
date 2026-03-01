package supermercado.cliente;

import supermercado.cliente.servicio.CategoriaServicio;
import supermercado.cliente.servicio.ProductoServicio;
import supermercado.cliente.modelo.Categoria;
import supermercado.cliente.modelo.Producto;

import java.util.List;
import java.util.Scanner;

// Aplicacion de consola que se conecta al servidor REST del supermercado.
// Usa HttpClient para las peticiones HTTP y org.json para parsear las respuestas.
public class ClienteApp {

    static CategoriaServicio catServicio = new CategoriaServicio();
    static ProductoServicio prodServicio = new ProductoServicio();
    static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("================================");
        System.out.println("   PERICO'S MARKET - Cliente");
        System.out.println("================================");
        System.out.println("El servidor tiene que estar en el puerto 12345\n");

        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Categorias");
            System.out.println("2. Productos");
            System.out.println("0. Salir");
            System.out.print("> ");

            int op = leerInt();
            switch (op) {
                case 1: menuCategorias(); break;
                case 2: menuProductos(); break;
                case 0:
                    salir = true;
                    System.out.println("Hasta luego!");
                    break;
                default:
                    System.out.println("Opcion incorrecta");
            }
        }

        teclado.close();
    }

    // ========== CATEGORIAS ==========

    private static void menuCategorias() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- CATEGORIAS ---");
            System.out.println("1. Ver todas");
            System.out.println("2. Crear");
            System.out.println("3. Modificar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("> ");

            int op = leerInt();
            switch (op) {
                case 1: verCategorias(); break;
                case 2: crearCategoria(); break;
                case 3: modificarCategoria(); break;
                case 4: eliminarCategoria(); break;
                case 0: volver = true; break;
                default: System.out.println("Opcion incorrecta");
            }
        }
    }

    private static void verCategorias() {
        List<Categoria> lista = catServicio.listarTodas();
        System.out.println("\nCategorias:");
        if (lista.isEmpty()) {
            System.out.println("  No hay categorias todavia");
            return;
        }
        for (Categoria c : lista) {
            System.out.println("  " + c);
        }
    }

    private static void crearCategoria() {
        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();
        System.out.print("Descripcion: ");
        String desc = teclado.nextLine();

        Categoria cat = new Categoria();
        cat.setNombre(nombre);
        cat.setDescripcion(desc);

        Categoria res = catServicio.crear(cat);
        if (res != null) {
            System.out.println("Creada con id " + res.getId());
        } else {
            System.out.println("Error al crear la categoria");
        }
    }

    private static void modificarCategoria() {
        verCategorias();
        System.out.print("ID a modificar: ");
        Long id = leerLong();
        System.out.print("Nombre nuevo: ");
        String nombre = teclado.nextLine();
        System.out.print("Descripcion nueva: ");
        String desc = teclado.nextLine();

        Categoria cat = new Categoria();
        cat.setNombre(nombre);
        cat.setDescripcion(desc);

        if (catServicio.modificar(id, cat)) {
            System.out.println("Modificada correctamente");
        } else {
            System.out.println("No se pudo modificar");
        }
    }

    private static void eliminarCategoria() {
        verCategorias();
        System.out.print("ID a eliminar: ");
        Long id = leerLong();

        if (catServicio.eliminar(id)) {
            System.out.println("Eliminada");
        } else {
            System.out.println("No se pudo eliminar, revisa el ID");
        }
    }

    // ========== PRODUCTOS ==========
    // al final añadi buscar por id porque sin eso tenia que listar todos
    // cada vez que queria ver uno solo

    private static void menuProductos() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- PRODUCTOS ---");
            System.out.println("1. Ver todos");
            System.out.println("2. Buscar por ID");
            System.out.println("3. Anadir producto");
            System.out.println("4. Modificar");
            System.out.println("5. Eliminar");
            System.out.println("0. Volver");
            System.out.print("> ");

            int op = leerInt();
            switch (op) {
                case 1: verProductos(); break;
                case 2: buscarProducto(); break;
                case 3: crearProducto(); break;
                case 4: modificarProducto(); break;
                case 5: eliminarProducto(); break;
                case 0: volver = true; break;
                default: System.out.println("No existe esa opcion");
            }
        }
    }

    private static void verProductos() {
        List<Producto> lista = prodServicio.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay productos en la base de datos");
        } else {
            System.out.println("\nProductos (" + lista.size() + "):");
            for (Producto p : lista) {
                System.out.println("  " + p);
            }
        }
    }

    // esto no lo tiene el menu de categorias, lo añadi despues
    private static void buscarProducto() {
        System.out.print("ID del producto: ");
        Long id = leerLong();
        Producto p = prodServicio.buscarPorId(id);
        if (p != null) {
            System.out.println("\n" + p);
        } else {
            System.err.println("Producto no encontrado");
        }
    }

    private static void crearProducto() {
        System.out.println("\nCategorias disponibles:");
        verCategorias();

        System.out.print("\nNombre del producto: ");
        String nombre = teclado.nextLine();
        System.out.print("Precio: ");
        double precio = leerDouble();

        if (precio < 0) {
            System.out.println("El precio no puede ser negativo!");
            return;
        }

        System.out.print("ID de la categoria: ");
        Long catId = leerLong();

        Producto p = new Producto();
        p.setNombre(nombre);
        p.setPrecio(precio);
        Categoria cat = new Categoria();
        cat.setId(catId);
        p.setCategoria(cat);

        Producto resultado = prodServicio.crear(p);
        if (resultado != null) {
            System.out.println("Producto creado! ID: " + resultado.getId());
        } else {
            System.err.println("Error: no se ha podido crear el producto");
        }
    }

    private static void modificarProducto() {
        verProductos();
        System.out.print("\nID del producto a modificar: ");
        Long id = leerLong();
        System.out.print("Nuevo nombre: ");
        String nombre = teclado.nextLine();
        System.out.print("Nuevo precio: ");
        double precio = leerDouble();

        System.out.println("Categorias:");
        verCategorias();
        System.out.print("ID de la categoria: ");
        Long catId = leerLong();

        Producto p = new Producto();
        p.setNombre(nombre);
        p.setPrecio(precio);
        Categoria cat = new Categoria();
        cat.setId(catId);
        p.setCategoria(cat);

        if (prodServicio.modificar(id, p)) {
            System.out.println("Producto modificado OK");
        } else {
            System.err.println("Error: no se ha podido modificar");
        }
    }

    private static void eliminarProducto() {
        verProductos();
        System.out.print("ID a eliminar: ");
        Long id = leerLong();

        if (prodServicio.eliminar(id)) {
            System.out.println("Eliminado!");
        } else {
            System.err.println("Error: no se ha podido borrar el producto");
        }
    }

    // --- auxiliares ---

    private static int leerInt() {
        try {
            return Integer.parseInt(teclado.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static Long leerLong() {
        try {
            return Long.parseLong(teclado.nextLine().trim());
        } catch (NumberFormatException ex) {
            System.out.println("Eso no es un numero, poniendo 0");
            return 0L;
        }
    }

    // creo que esto se podria hacer mejor con Scanner.nextDouble() pero
    // da problemas con el salto de linea, me lo dijo el profesor
    private static double leerDouble() {
        try {
            String linea = teclado.nextLine().trim();
            linea = linea.replace(",", "."); // por la coma decimal
            return Double.parseDouble(linea);
        } catch (NumberFormatException ex) {
            System.out.println("No es un numero valido, usando 0");
            return 0.0;
        }
    }
}
