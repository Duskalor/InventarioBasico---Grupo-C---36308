package inventario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class InventarioServiceTest {

    private ProductoService productoService;
    private InventarioService inventarioService;

    @BeforeEach
    void setUp() {
        productoService = new ProductoService();
        inventarioService = new InventarioService(productoService);
    }

    @Test
    void registrarEntrada_debeAumentarStockYRegistrarMovimiento() {
        Producto p = productoService.crearProducto("A", "Cat", 10.0, 5, 2);
        boolean resultado = inventarioService.registrarEntrada(p.getId(), 3, "Compra");
        assertTrue(resultado);
        assertEquals(8, p.getStock());
        List<MovimientoInventario> movimientos = inventarioService.listarMovimientos();
        assertEquals(1, movimientos.size());
        assertEquals("ENTRADA", movimientos.get(0).getTipo());
        assertEquals(3, movimientos.get(0).getCantidad());
    }

    @Test
    void registrarEntrada_debeDevolverFalseConCantidadInvalida() {
        Producto p = productoService.crearProducto("A", "Cat", 10.0, 5, 2);
        assertFalse(inventarioService.registrarEntrada(p.getId(), 0, "Compra"));
        assertFalse(inventarioService.registrarEntrada(p.getId(), -1, "Compra"));
    }

    @Test
    void registrarEntrada_debeDevolverFalseSiProductoNoExiste() {
        assertFalse(inventarioService.registrarEntrada(999, 5, "Compra"));
    }

    @Test
    void registrarSalida_debeDisminuirStockYRegistrarMovimiento() {
        Producto p = productoService.crearProducto("A", "Cat", 10.0, 5, 2);
        boolean resultado = inventarioService.registrarSalida(p.getId(), 2, "Venta");
        assertTrue(resultado);
        assertEquals(3, p.getStock());
        List<MovimientoInventario> movimientos = inventarioService.listarMovimientos();
        assertEquals(1, movimientos.size());
        assertEquals("SALIDA", movimientos.get(0).getTipo());
        assertEquals(2, movimientos.get(0).getCantidad());
    }

    @Test
    void registrarSalida_debeDevolverFalseConCantidadInvalida() {
        Producto p = productoService.crearProducto("A", "Cat", 10.0, 5, 2);
        assertFalse(inventarioService.registrarSalida(p.getId(), 0, "Venta"));
        assertFalse(inventarioService.registrarSalida(p.getId(), -1, "Venta"));
    }

    @Test
    void registrarSalida_debeDevolverFalseSiStockInsuficiente() {
        Producto p = productoService.crearProducto("A", "Cat", 10.0, 5, 2);
        assertFalse(inventarioService.registrarSalida(p.getId(), 10, "Venta"));
        assertEquals(5, p.getStock());
    }

    @Test
    void registrarSalida_debeDevolverFalseSiProductoNoExiste() {
        assertFalse(inventarioService.registrarSalida(999, 1, "Venta"));
    }

    @Test
    void listarMovimientos_debeDevolverCopia() {
        Producto p = productoService.crearProducto("A", "Cat", 10.0, 5, 2);
        inventarioService.registrarEntrada(p.getId(), 1, "Compra");
        List<MovimientoInventario> movimientos = inventarioService.listarMovimientos();
        movimientos.clear();
        assertEquals(1, inventarioService.listarMovimientos().size());
    }
}
