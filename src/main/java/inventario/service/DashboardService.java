package inventario.service;

        import java.util.Map;

        public class DashboardService {
            private final ProductoService productoService;
            private final ReporteService reporteService;
            private final AlertaService alertaService;

            public DashboardService(ProductoService productoService, ReporteService reporteService, AlertaService alertaService) {
                this.productoService = productoService;
                this.reporteService = reporteService;
                this.alertaService = alertaService;
            }

            public String generarResumenDashboard() {
                int totalProductos = productoService.listarProductos().size();
                double valorTotal = reporteService.calcularValorTotalInventario();
                int alertasActivas = alertaService.generarAlertas().get(0).startsWith("No existen") ? 0 : alertaService.generarAlertas().size();
                Map<String, Integer> stockPorCategoria = reporteService.sumarStockPorCategoria();

                StringBuilder resumen = new StringBuilder();
                resumen.append("=== DASHBOARD INVENTARIO ===\n");
                resumen.append("Total de productos: ").append(totalProductos).append("\n");
                resumen.append(String.format("Valor total: S/ %.2f%n", valorTotal));
                resumen.append("Alertas activas: ").append(alertasActivas).append("\n");
                resumen.append("Stock por categoria: ").append(stockPorCategoria).append("\n");
                return resumen.toString();
            }
        }
