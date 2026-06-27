package inventario.service;

import inventario.kata.StockKata;
import inventario.model.Producto;
import java.util.ArrayList;
import java.util.List;

public class AlertaService {
    private final ProductoService productoService;
    private final StockKata stockKata = new StockKata();

    public AlertaService(ProductoService productoService) {
        this.productoService = productoService;
    }

    public List<String> generarAlertas() {
        List<String> alertas = new ArrayList<>();

        for (Producto producto : productoService.listarProductos()) {

        String estado = stockKata.clasificarStock(producto.getStock(), producto.getStockMinimo());
        if ("SIN_STOCK".equals(estado)) {
            alertas.add("CRITICA: " + producto.getNombre() + " no tiene stock. Reponer como minimo "
                    + producto.getStockMinimo() + " unidades.");
        } else if ("STOCK_BAJO".equals(estado)) {
            int cantidadSugerida = stockKata.calcularCantidadReposicion(producto.getStock(), producto.getStockMinimo());
            alertas.add("ADVERTENCIA: " + producto.getNombre() + " tiene stock bajo. Reposicion sugerida: "
                    + cantidadSugerida + " unidades.");
        }

        }

        if (alertas.isEmpty()) {
            alertas.add("No existen alertas. El inventario se encuentra estable.");
        }
        return alertas;
    }
}
