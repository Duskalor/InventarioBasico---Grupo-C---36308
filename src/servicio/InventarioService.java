package servicio;

import java.util.ArrayList;
import java.util.List;

import excepciones.DatoInvalidoException;
import excepciones.ProductoDuplicadoException;
import excepciones.ProductoNoEncontradoException;
import excepciones.StockInsuficienteException;
import modelo.MovimientoInventario;
import modelo.Producto;

public class InventarioService {

    private static final String REGEX_CODIGO = "^[a-zA-Z0-9_-]+$";
    private static final int CODIGO_MAX_LENGTH = 20;
    private static final int NOMBRE_MIN_LENGTH = 2;
    private static final int NOMBRE_MAX_LENGTH = 100;
    private static final double PRECIO_MAX = 999999.99;
    private static final int STOCK_MAX = 999999;
    private static final int CANTIDAD_MAX = 999999;

    private List<Producto> productos;
    private List<MovimientoInventario> movimientos;
    private int siguienteIdMovimiento;

    public InventarioService() {
        this.productos = new ArrayList<>();
        this.movimientos = new ArrayList<>();
        this.siguienteIdMovimiento = 1;
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

    public void aumentarStock(String codigo, int cantidad, String motivo)
            throws ProductoNoEncontradoException, DatoInvalidoException {

        validarCantidad(cantidad);
        Producto producto = buscarProductoPorCodigo(codigo);

        if (producto.getStock() + cantidad > STOCK_MAX) {
            throw new DatoInvalidoException(
                    "No se puede aumentar el stock. El máximo permitido es " + STOCK_MAX
                            + ". Stock actual: " + producto.getStock()
                            + ", intentaste agregar: " + cantidad
            );
        }

        producto.aumentarStock(cantidad);
        registrarMovimiento(codigo, "ENTRADA", cantidad, motivo);
    }

    public void disminuirStock(String codigo, int cantidad, String motivo)
            throws ProductoNoEncontradoException, StockInsuficienteException, DatoInvalidoException {

        validarCantidad(cantidad);

        Producto producto = buscarProductoPorCodigo(codigo);

        if (producto.getStock() < cantidad) {
            throw new StockInsuficienteException(
                    "Stock insuficiente. Stock actual: " + producto.getStock()
            );
        }

        producto.disminuirStock(cantidad);
        registrarMovimiento(codigo, "SALIDA", cantidad, motivo);
    }

    public void eliminarProducto(String codigo)
            throws ProductoNoEncontradoException, DatoInvalidoException {

        Producto producto = buscarProductoPorCodigo(codigo);
        productos.remove(producto);
    }

    public List<MovimientoInventario> listarMovimientos() {
        return new ArrayList<>(movimientos);
    }

    private void registrarMovimiento(String productoCodigo, String tipo, int cantidad, String motivo) {
        MovimientoInventario movimiento = new MovimientoInventario(
                siguienteIdMovimiento++, productoCodigo, tipo, cantidad, motivo
        );
        movimientos.add(movimiento);
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

        // ── Validaciones de código ──
        if (!producto.getCodigo().matches(REGEX_CODIGO)) {
            throw new DatoInvalidoException(
                    "El código solo puede contener letras, números, guiones (-) y guiones bajos (_)."
            );
        }

        if (producto.getCodigo().length() > CODIGO_MAX_LENGTH) {
            throw new DatoInvalidoException(
                    "El código no puede tener más de " + CODIGO_MAX_LENGTH + " caracteres."
            );
        }

        // ── Validaciones de nombre ──
        if (producto.getNombre().trim().length() < NOMBRE_MIN_LENGTH) {
            throw new DatoInvalidoException(
                    "El nombre debe tener al menos " + NOMBRE_MIN_LENGTH + " caracteres."
            );
        }

        if (producto.getNombre().length() > NOMBRE_MAX_LENGTH) {
            throw new DatoInvalidoException(
                    "El nombre no puede tener más de " + NOMBRE_MAX_LENGTH + " caracteres."
            );
        }

        // ── Validaciones de precio ──
        if (producto.getPrecio() <= 0) {
            throw new DatoInvalidoException("El precio debe ser mayor que cero.");
        }

        if (producto.getPrecio() > PRECIO_MAX) {
            throw new DatoInvalidoException(
                    "El precio no puede exceder " + PRECIO_MAX + "."
            );
        }

        // Verificar que el precio no tenga más de 2 decimales
        double precioRedondeado = Math.round(producto.getPrecio() * 100.0) / 100.0;
        if (Math.abs(producto.getPrecio() - precioRedondeado) > 0.0001) {
            throw new DatoInvalidoException("El precio no puede tener más de 2 decimales.");
        }

        // ── Validaciones de stock ──
        if (producto.getStock() < 0) {
            throw new DatoInvalidoException("El stock no puede ser negativo.");
        }

        if (producto.getStock() > STOCK_MAX) {
            throw new DatoInvalidoException(
                    "El stock no puede exceder " + STOCK_MAX + " unidades."
            );
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
        if (cantidad > CANTIDAD_MAX) {
            throw new DatoInvalidoException(
                    "La cantidad no puede exceder " + CANTIDAD_MAX + "."
            );
        }
    }
}
