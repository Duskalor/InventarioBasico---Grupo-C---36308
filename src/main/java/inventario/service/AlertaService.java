package inventario.service;


import inventario.model.Producto;
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
            alertas.add("CRITICA: " + producto.getNombre() + " no tiene stock. Reponer como minimo "
                    + producto.getStockMinimo() + " unidades.");
        } else if (producto.getStock() <= producto.getStockMinimo()) {
            int cantidadSugerida = (producto.getStockMinimo() * 2) - producto.getStock();
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
