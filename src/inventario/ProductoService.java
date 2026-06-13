package inventario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoService {
    private final List<Producto> productos = new ArrayList<>();
    private int siguienteId = 1;

    public Producto crearProducto(String nombre, String categoria, double precio, int stockInicial, int stockMinimo) {
        Producto producto = new Producto(siguienteId++, nombre, categoria, precio, stockInicial, stockMinimo);
        productos.add(producto);
        return producto;
    }

    public List<Producto> listarProductos() {
        return new ArrayList<>(productos);
    }

    public Optional<Producto> buscarPorId(int id) {
        return productos.stream()
                .filter(producto -> producto.getId() == id)
                .findFirst();
    }

    public boolean actualizarProducto(int id, String nombre, String categoria, double precio, int stockMinimo) {
        Optional<Producto> productoEncontrado = buscarPorId(id);
        if (productoEncontrado.isEmpty()) {
            return false;
        }

        Producto producto = productoEncontrado.get();
        producto.setNombre(nombre);
        producto.setCategoria(categoria);
        producto.setPrecio(precio);
        producto.setStockMinimo(stockMinimo);
        return true;
    }

    public boolean eliminarProducto(int id) {
        return productos.removeIf(producto -> producto.getId() == id);
    }

    public void cargarDatosIniciales() {
        crearProducto("Laptop Lenovo ThinkPad", "Computo", 3500.00, 8, 3);
        crearProducto("Switch Cisco 24 puertos", "Redes", 2100.00, 5, 4);
        crearProducto("Disco SSD 1TB", "Almacenamiento", 360.00, 25, 10);
        crearProducto("Access Point WiFi 6", "Redes", 780.00, 2, 5);
        crearProducto("Memoria RAM 32GB", "Computo", 420.00, 0, 6);
    }
}
