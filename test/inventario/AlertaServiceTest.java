package inventario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class AlertaServiceTest {

    private ProductoService productoService;
    private AlertaService alertaService;

    @BeforeEach
    void setUp() {
        productoService = new ProductoService();
        alertaService = new AlertaService(productoService);
    }

    @Test
    void generarAlertas_debeDevolverOkSiNoHayProductos() {
        List<String> alertas = alertaService.generarAlertas();
        assertEquals(1, alertas.size());
        assertEquals("OK: No existen alertas de stock bajo o agotado.", alertas.get(0));
    }

    @Test
    void generarAlertas_debeDetectarStockCritico() {
        Producto p = productoService.crearProducto("SSD", "Almacenamiento", 360.0, 0, 5);
        List<String> alertas = alertaService.generarAlertas();
        assertEquals(1, alertas.size());
        assertTrue(alertas.get(0).contains("CRÍTICA"));
        assertTrue(alertas.get(0).contains("SSD"));
        assertTrue(alertas.get(0).contains("5 unidades"));
    }

    @Test
    void generarAlertas_debeDetectarAdvertenciaStockBajo() {
        Producto p = productoService.crearProducto("RAM", "Computo", 420.0, 2, 5);
        List<String> alertas = alertaService.generarAlertas();
        assertEquals(1, alertas.size());
        assertTrue(alertas.get(0).contains("ADVERTENCIA"));
        assertTrue(alertas.get(0).contains("RAM"));
        int cantidadSugerida = (5 * 2) - 2; // 8
        assertTrue(alertas.get(0).contains(cantidadSugerida + " unidades"));
    }

    @Test
    void generarAlertas_debeIgnorarProductosConStockNormal() {
        productoService.crearProducto("Normal", "Cat", 10.0, 10, 5);
        List<String> alertas = alertaService.generarAlertas();
        assertEquals(1, alertas.size());
        assertTrue(alertas.get(0).contains("OK"));
    }

    @Test
    void generarAlertas_debeCombinarCriticaYAdvertencia() {
        productoService.crearProducto("Critico", "Cat", 10.0, 0, 5);
        productoService.crearProducto("Bajo", "Cat", 10.0, 2, 5);
        List<String> alertas = alertaService.generarAlertas();
        assertEquals(2, alertas.size());
        assertTrue(alertas.get(0).contains("CRÍTICA") || alertas.get(1).contains("CRÍTICA"));
        assertTrue(alertas.get(0).contains("ADVERTENCIA") || alertas.get(1).contains("ADVERTENCIA"));
    }
}
