package inventario;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DashboardService {
    private final ProductoService productoService;
    private final ReporteService reporteService;
    private final AlertaService alertaService;

    public DashboardService(ProductoService productoService, ReporteService reporteService, AlertaService alertaService) {
        this.productoService = productoService;
        this.reporteService = reporteService;
        this.alertaService = alertaService;
    }

    public void mostrarDashboard() {
        List<Producto> productos = productoService.listarProductos();
        int totalProductos = productos.size();
        int totalUnidades = productos.stream().mapToInt(Producto::getStock).sum();
        double valorTotal = reporteService.calcularValorTotalInventario();
        int stockBajo = reporteService.listarProductosConStockBajo().size();
        int sinStock = reporteService.listarProductosSinStock().size();
        Optional<Producto> productoMayorValor = productos.stream()
                .max(Comparator.comparingDouble(Producto::calcularValorInventario));

        System.out.println("\n=================================================");
        System.out.println("       DASHBOARD GENERAL DEL INVENTARIO          ");
        System.out.println("=================================================");
        System.out.printf("Total de productos registrados : %d%n", totalProductos);
        System.out.printf("Total de unidades en almacén   : %d%n", totalUnidades);
        System.out.printf("Valor total del inventario     : S/ %.2f%n", valorTotal);
        System.out.printf("Productos con stock bajo       : %d%n", stockBajo);
        System.out.printf("Productos sin stock            : %d%n", sinStock);

        productoMayorValor.ifPresent(producto -> System.out.printf(
                "Producto con mayor valor       : %s (S/ %.2f)%n",
                producto.getNombre(), producto.calcularValorInventario()
        ));

        System.out.println("\n--- Stock por categoría ---");
        mostrarGraficoBarras(reporteService.sumarStockPorCategoria());

        System.out.println("\n--- Últimos movimientos ---");
        List<MovimientoInventario> ultimosMovimientos = reporteService.obtenerUltimosMovimientos(5);
        if (ultimosMovimientos.isEmpty()) {
            System.out.println("No hay movimientos registrados.");
        } else {
            ultimosMovimientos.forEach(System.out::println);
        }

        System.out.println("\n--- Alertas principales ---");
        alertaService.generarAlertas().forEach(System.out::println);
        System.out.println("=================================================\n");
    }

    private void mostrarGraficoBarras(Map<String, Integer> stockPorCategoria) {
        if (stockPorCategoria.isEmpty()) {
            System.out.println("No hay datos para graficar.");
            return;
        }

        for (Map.Entry<String, Integer> entry : stockPorCategoria.entrySet()) {
            String categoria = entry.getKey();
            int stock = entry.getValue();
            String barra = "#".repeat(Math.max(1, Math.min(stock, 40)));
            System.out.printf("%-18s | %-40s %d%n", categoria, barra, stock);
        }
    }
}
