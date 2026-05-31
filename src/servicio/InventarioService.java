package servicio;

import java.util.ArrayList;
import java.util.List;

import excepciones.DatoInvalidoException;
import excepciones.ProductoDuplicadoException;
import excepciones.ProductoNoEncontradoException;
import excepciones.StockInsuficienteException;
import modelo.Producto;

public class InventarioService {

    private List<Producto> productos;

    public InventarioService() {
        this.productos = new ArrayList<>();
    }

    public void registrarProducto(Producto producto)
            throws ProductoDuplicadoException, DatoInvalidoException {

        validarProducto(producto);

        if (existeProducto(producto.getCodigo())) {
            throw new ProductoDuplicadoException(
                    "Ya existe un producto registrado con el código: " + producto.getCodigo()
            );
        }

        productos.add(producto);
    }

    public List<Producto> listarProductos() {
        return productos;
    }

    public Producto buscarProductoPorCodigo(String codigo)
            throws ProductoNoEncontradoException, DatoInvalidoException {

        validarTexto(codigo, "El código no puede estar vacío.");

        for (Producto producto : productos) {
            if (producto.getCodigo().equalsIgnoreCase(codigo)) {
                return producto;
            }
        }

        throw new ProductoNoEncontradoException(
                "No se encontró ningún producto con el código: " + codigo
        );
    }

    public void aumentarStock(String codigo, int cantidad)
            throws ProductoNoEncontradoException, DatoInvalidoException {

        validarCantidad(cantidad);

        Producto producto = buscarProductoPorCodigo(codigo);
        producto.aumentarStock(cantidad);
    }

    public void disminuirStock(String codigo, int cantidad)
            throws ProductoNoEncontradoException, StockInsuficienteException, DatoInvalidoException {

        validarCantidad(cantidad);

        Producto producto = buscarProductoPorCodigo(codigo);

        if (producto.getStock() < cantidad) {
            throw new StockInsuficienteException(
                    "Stock insuficiente. Stock actual: " + producto.getStock()
            );
        }

        producto.disminuirStock(cantidad);
    }

    public void eliminarProducto(String codigo)
            throws ProductoNoEncontradoException, DatoInvalidoException {

        Producto producto = buscarProductoPorCodigo(codigo);
        productos.remove(producto);
    }

    private boolean existeProducto(String codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo().equalsIgnoreCase(codigo)) {
                return true;
            }
        }
        return false;
    }

    private void validarProducto(Producto producto) throws DatoInvalidoException {
        if (producto == null) {
            throw new DatoInvalidoException("El producto no puede ser nulo.");
        }

        validarTexto(producto.getCodigo(), "El código del producto no puede estar vacío.");
        validarTexto(producto.getNombre(), "El nombre del producto no puede estar vacío.");

        if (producto.getPrecio() <= 0) {
            throw new DatoInvalidoException("El precio debe ser mayor que cero.");
        }

        if (producto.getStock() < 0) {
            throw new DatoInvalidoException("El stock no puede ser negativo.");
        }
    }

    private void validarTexto(String texto, String mensajeError) throws DatoInvalidoException {
        if (texto == null || texto.trim().isEmpty()) {
            throw new DatoInvalidoException(mensajeError);
        }
    }

    private void validarCantidad(int cantidad) throws DatoInvalidoException {
        if (cantidad <= 0) {
            throw new DatoInvalidoException("La cantidad debe ser mayor que cero.");
        }
    }
}
