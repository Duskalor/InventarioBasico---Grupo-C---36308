package inventario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

class ReporteServiceTest {

    private ProductoService productoService;
    private InventarioService inventarioService;
    private ReporteService reporteService;

    @BeforeEach
    void setUp() {
        productoService = new ProductoService();
        inventarioService = new InventarioService(productoService);
        reporteService = new ReporteService(productoService, inventarioService);
    }

    @Test
    void calcularValorTotalInventario_debeSumarCorrectamente() {
        productoService.crearProducto("A", "Cat", 100.0, 5, 2);
        productoService.crearProducto("B", "Cat", 200.0, 3, 1);
        assertEquals(100.0 * 5 + 200.0 * 3, reporteService.calcularValorTotalInventario());
    }

    @Test
    void listarProductosConStockBajo_debeIncluirSoloStockPositivoYMenorIgualMinimo() {
        productoService.crearProducto("A", "Cat", 10.0, 0, 2); // sin stock -> no
        productoService.crearProducto("B", "Cat", 10.0, 1, 2); // bajo -> si
        productoService.crearProducto("C", "Cat", 10.0, 3, 2); // normal -> no
        List<Producto> bajos = reporteService.listarProductosConStockBajo();
        assertEquals(1, bajos.size());
        assertEquals("B", bajos.get(0).getNombre());
    }

    @Test
    void listarProductosSinStock_debeIncluirSoloStockCero() {
        productoService.crearProducto("A", "Cat", 10.0, 0, 2);
        productoService.crearProducto("B", "Cat", 10.0, 5, 2);
        List<Producto> sinStock = reporteService.listarProductosSinStock();
        assertEquals(1, sinStock.size());
        assertEquals("A", sinStock.get(0).getNombre());
    }

    @Test
    void contarProductosPorCategoria_debeAgruparCorrectamente() {
        productoService.crearProducto("A", "Redes", 10.0, 5, 2);
        productoService.crearProducto("B", "Redes", 10.0, 3, 2);
        productoService.crearProducto("C", "Computo", 10.0, 1, 1);
        Map<String, Long> conteo = reporteService.contarProductosPorCategoria();
        assertEquals(2L, conteo.get("Redes"));
        assertEquals(1L, conteo.get("Computo"));
    }

    @Test
    void sumarStockPorCategoria_debeSumarCorrectamente() {
        productoService.crearProducto("A", "Redes", 10.0, 5, 2);
        productoService.crearProducto("B", "Redes", 10.0, 3, 2);
        productoService.crearProducto("C", "Computo", 10.0, 1, 1);
        Map<String, Integer> stock = reporteService.sumarStockPorCategoria();
        assertEquals(8, stock.get("Redes"));
        assertEquals(1, stock.get("Computo"));
    }

    @Test
    void obtenerUltimosMovimientos_debeDevolverLosNUltimos() {
        Producto p = productoService.crearProducto("A", "Cat", 10.0, 10, 2);
        inventarioService.registrarEntrada(p.getId(), 1, "C1");
        inventarioService.registrarEntrada(p.getId(), 2, "C2");
        inventarioService.registrarEntrada(p.getId(), 3, "C3");
        List<MovimientoInventario> ultimos = reporteService.obtenerUltimosMovimientos(2);
        assertEquals(2, ultimos.size());
        assertEquals(2, ultimos.get(0).getCantidad());
        assertEquals(3, ultimos.get(1).getCantidad());
    }

    @Test
    void obtenerUltimosMovimientos_debeDevolverTodosSiHayMenosQueLimite() {
        Producto p = productoService.crearProducto("A", "Cat", 10.0, 10, 2);
        inventarioService.registrarEntrada(p.getId(), 1, "C1");
        List<MovimientoInventario> ultimos = reporteService.obtenerUltimosMovimientos(5);
        assertEquals(1, ultimos.size());
    }
}
