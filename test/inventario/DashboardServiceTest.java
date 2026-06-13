package inventario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class DashboardServiceTest {

    private ProductoService productoService;
    private InventarioService inventarioService;
    private ReporteService reporteService;
    private AlertaService alertaService;
    private DashboardService dashboardService;

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        productoService = new ProductoService();
        inventarioService = new InventarioService(productoService);
        reporteService = new ReporteService(productoService, inventarioService);
        alertaService = new AlertaService(productoService);
        dashboardService = new DashboardService(productoService, reporteService, alertaService);
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void mostrarDashboard_noDebeLanzarExcepcion() {
        assertDoesNotThrow(() -> dashboardService.mostrarDashboard());
    }

    @Test
    void mostrarDashboard_debeMostrarTitulo() {
        dashboardService.mostrarDashboard();
        String salida = outputStream.toString();
        assertTrue(salida.contains("DASHBOARD GENERAL DEL INVENTARIO"));
    }

    @Test
    void mostrarDashboard_debeMostrarMetricasCorrectas() {
        productoService.crearProducto("A", "Cat", 100.0, 5, 2);
        dashboardService.mostrarDashboard();
        String salida = outputStream.toString();
        assertTrue(salida.contains("Total de productos registrados : 1"));
        assertTrue(salida.contains("Total de unidades en almacén   : 5"));
        assertTrue(salida.contains("Producto con mayor valor"));
    }

    @Test
    void mostrarDashboard_debeMostrarGraficoDeBarras() {
        productoService.crearProducto("A", "Redes", 10.0, 3, 2);
        dashboardService.mostrarDashboard();
        String salida = outputStream.toString();
        assertTrue(salida.contains("--- Stock por categoría ---"));
        assertTrue(salida.contains("Redes"));
        assertTrue(salida.contains("###"));
    }

    @Test
    void mostrarDashboard_debeMostrarAlertas() {
        productoService.crearProducto("Critico", "Cat", 10.0, 0, 2);
        dashboardService.mostrarDashboard();
        String salida = outputStream.toString();
        assertTrue(salida.contains("--- Alertas principales ---"));
        assertTrue(salida.contains("CRÍTICA"));
    }
}
