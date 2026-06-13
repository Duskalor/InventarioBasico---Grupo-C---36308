package inventario;

import java.util.ArrayList;
import java.util.List;

public class AlertaService {
    private final ProductoService productoService;

    public AlertaService(ProductoService productoService) {
        this.productoService = productoService;
    }

    public List<String> generarAlertas() {
        List<String> alertas = new ArrayList<>();

        for (Producto producto : productoService.listarProductos()) {
            if (producto.getStock() == 0) {
                alertas.add("CRÍTICA: " + producto.getNombre() + " no tiene stock. Reponer como mínimo "
                        + producto.getStockMinimo() + " unidades.");
            } else if (producto.getStock() <= producto.getStockMinimo()) {
                int cantidadSugerida = (producto.getStockMinimo() * 2) - producto.getStock();
                alertas.add("ADVERTENCIA: " + producto.getNombre() + " tiene stock bajo ("
                        + producto.getStock() + "). Compra sugerida: " + cantidadSugerida + " unidades.");
            }
        }

        if (alertas.isEmpty()) {
            alertas.add("OK: No existen alertas de stock bajo o agotado.");
        }

        return alertas;
    }
}
