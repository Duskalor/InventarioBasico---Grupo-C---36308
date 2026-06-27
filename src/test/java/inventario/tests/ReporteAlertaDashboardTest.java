package inventario.tests;

import inventario.repository.InMemoryProductoRepository;
import inventario.service.AlertaService;
import inventario.service.DashboardService;
import inventario.service.InventarioService;
import inventario.service.ProductoService;
import inventario.service.ReporteService;
import inventario.testutil.Assertions;

public class ReporteAlertaDashboardTest {
    public void reporte_calculaValorTotalInventario() {
        ProductoService productoService = new ProductoService(new InMemoryProductoRepository());
        productoService.crearProducto("Laptop", "Computo", 3000, 2, 1);
        productoService.crearProducto("Mouse", "Perifericos", 50, 4, 2);
        InventarioService inventarioService = new InventarioService(productoService);
        ReporteService reporteService = new ReporteService(productoService, inventarioService);

        Assertions.assertEquals(6200.0, reporteService.calcularValorTotalInventario(), 0.001,
                "Debe sumar precio por stock de todos los productos.");
    }

    public void alerta_detectaStockBajoYSinStock() {
        ProductoService productoService = new ProductoService(new InMemoryProductoRepository());
        productoService.crearProducto("AP WiFi", "Redes", 780, 2, 5);
        productoService.crearProducto("Memoria", "Computo", 420, 0, 6);
        AlertaService alertaService = new AlertaService(productoService);

        String alertas = String.join(" | ", alertaService.generarAlertas());

        Assertions.assertContains(alertas, "ADVERTENCIA", "Debe generar advertencia para stock bajo.");
        Assertions.assertContains(alertas, "CRITICA", "Debe generar alerta critica para sin stock.");
    }

    public void dashboard_muestraResumenGeneral() {
        ProductoService productoService = new ProductoService(new InMemoryProductoRepository());
        productoService.crearProducto("Switch", "Redes", 2100, 5, 4);
        InventarioService inventarioService = new InventarioService(productoService);
        ReporteService reporteService = new ReporteService(productoService, inventarioService);
        AlertaService alertaService = new AlertaService(productoService);
        DashboardService dashboardService = new DashboardService(productoService, reporteService, alertaService);

        String resumen = dashboardService.generarResumenDashboard();

        Assertions.assertContains(resumen, "Total de productos: 1", "Debe mostrar cantidad de productos.");
        Assertions.assertContains(resumen, "Valor total", "Debe mostrar valor total.");
    }
}
