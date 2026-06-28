package inventario;

        import inventario.repository.InMemoryProductoRepository;
        import inventario.repository.OrmProductoRepository;
import java.nio.file.Path;
import inventario.service.AlertaService;
        import inventario.service.DashboardService;
        import inventario.service.InventarioService;
        import inventario.service.ProductoService;
        import inventario.service.ReporteService;

        public class Main {
            public static void main(String[] args) {
                ProductoService productoService = new ProductoService(new OrmProductoRepository(Path.of("data")));
                InventarioService inventarioService = new InventarioService(productoService);
                ReporteService reporteService = new ReporteService(productoService, inventarioService);
                AlertaService alertaService = new AlertaService(productoService);
                DashboardService dashboardService = new DashboardService(productoService, reporteService, alertaService);

                productoService.cargarDatosIniciales();
                inventarioService.registrarEntrada(1, 2, "Compra inicial");
                inventarioService.registrarSalida(3, 4, "Venta inicial");

                System.out.println("Sistema de Control de Inventarios - v3.2 ORM e Integracion Continua");
                System.out.println(dashboardService.generarResumenDashboard());
                System.out.println("Ejecute scripts/test.sh para validar las pruebas unitarias.");
            }
        }
