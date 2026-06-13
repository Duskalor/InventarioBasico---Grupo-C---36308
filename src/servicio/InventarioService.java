package servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import modelo.MovimientoInventario;
import modelo.Producto;

public class InventarioService {
    private final ProductoService productoService;
    private final List<MovimientoInventario> movimientos = new ArrayList<>();
    private int siguienteIdMovimiento = 1;

    public InventarioService(ProductoService productoService) {
        this.productoService = productoService;
    }

    public boolean registrarEntrada(int productoId, int cantidad, String motivo) {
        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor que cero.");
            return false;
        }

        Optional<Producto> productoEncontrado = productoService.buscarPorId(productoId);
        if (productoEncontrado.isEmpty()) {
            return false;
        }

        Producto producto = productoEncontrado.get();
        producto.setStock(producto.getStock() + cantidad);
        movimientos.add(new MovimientoInventario(siguienteIdMovimiento++, productoId, "ENTRADA", cantidad, motivo));
        return true;
    }

    public boolean registrarSalida(int productoId, int cantidad, String motivo) {
        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor que cero.");
            return false;
        }

        Optional<Producto> productoEncontrado = productoService.buscarPorId(productoId);
        if (productoEncontrado.isEmpty()) {
            return false;
        }

        Producto producto = productoEncontrado.get();
        if (producto.getStock() < cantidad) {
            System.out.println("Stock insuficiente. Stock actual: " + producto.getStock());
            return false;
        }

        producto.setStock(producto.getStock() - cantidad);
        movimientos.add(new MovimientoInventario(siguienteIdMovimiento++, productoId, "SALIDA", cantidad, motivo));
        return true;
    }

    public List<MovimientoInventario> listarMovimientos() {
        return new ArrayList<>(movimientos);
    }
}
