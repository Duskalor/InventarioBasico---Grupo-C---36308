package inventario;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteService {
    private final ProductoService productoService;
    private final InventarioService inventarioService;

    public ReporteService(ProductoService productoService, InventarioService inventarioService) {
        this.productoService = productoService;
        this.inventarioService = inventarioService;
    }

    public double calcularValorTotalInventario() {
        return productoService.listarProductos().stream()
                .mapToDouble(Producto::calcularValorInventario)
                .sum();
    }

    public List<Producto> listarProductosConStockBajo() {
        return productoService.listarProductos().stream()
                .filter(producto -> producto.getStock() > 0 && producto.getStock() <= producto.getStockMinimo())
                .sorted(Comparator.comparingInt(Producto::getStock))
                .collect(Collectors.toList());
    }

    public List<Producto> listarProductosSinStock() {
        return productoService.listarProductos().stream()
                .filter(producto -> producto.getStock() == 0)
                .collect(Collectors.toList());
    }

    public Map<String, Long> contarProductosPorCategoria() {
        return productoService.listarProductos().stream()
                .collect(Collectors.groupingBy(Producto::getCategoria, Collectors.counting()));
    }

    public Map<String, Integer> sumarStockPorCategoria() {
        return productoService.listarProductos().stream()
                .collect(Collectors.groupingBy(Producto::getCategoria, Collectors.summingInt(Producto::getStock)));
    }

    public List<MovimientoInventario> obtenerUltimosMovimientos(int limite) {
        List<MovimientoInventario> movimientos = inventarioService.listarMovimientos();
        int inicio = Math.max(0, movimientos.size() - limite);
        return movimientos.subList(inicio, movimientos.size());
    }
}
