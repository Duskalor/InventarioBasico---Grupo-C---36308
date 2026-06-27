package inventario.service;

import inventario.model.Producto;
import inventario.repository.ProductoRepository;
import java.util.List;
import java.util.Optional;

public class ProductoService {
    private final ProductoRepository repository;
    private int siguienteId;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
        this.siguienteId = repository.findAll().stream().mapToInt(Producto::getId).max().orElse(0) + 1;
    }

    public Producto crearProducto(String nombre, String categoria, double precio, int stockInicial, int stockMinimo) {
        Producto producto = new Producto(siguienteId++, nombre, categoria, precio, stockInicial, stockMinimo);
        return repository.save(producto);
    }

    public List<Producto> listarProductos() {
        return repository.findAll();
    }

    public Optional<Producto> buscarPorId(int id) {
        return repository.findById(id);
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
        repository.save(producto);
        return true;
    }

    public boolean eliminarProducto(int id) {
        return repository.deleteById(id);
    }

    public void cargarDatosIniciales() {
        if (!listarProductos().isEmpty()) {
            return;
        }
        crearProducto("Laptop Lenovo ThinkPad", "Computo", 3500.00, 8, 3);
        crearProducto("Switch Cisco 24 puertos", "Redes", 2100.00, 5, 4);
        crearProducto("Disco SSD 1TB", "Almacenamiento", 360.00, 25, 10);
        crearProducto("Access Point WiFi 6", "Redes", 780.00, 2, 5);
        crearProducto("Memoria RAM 32GB", "Computo", 420.00, 0, 6);
    }
}
