package inventario.tests;

public class AllTests {
    private static int ejecutadas = 0;
    private static int fallidas = 0;

    public static void main(String[] args) {
        run("ProductoServiceTest.crearProducto_asignaIdYGuardaProducto", () -> new ProductoServiceTest().crearProducto_asignaIdYGuardaProducto());
run("ProductoServiceTest.crearProducto_rechazaPrecioNegativo", () -> new ProductoServiceTest().crearProducto_rechazaPrecioNegativo());
run("ProductoServiceTest.actualizarProducto_modificaDatosPrincipales", () -> new ProductoServiceTest().actualizarProducto_modificaDatosPrincipales());
run("InventarioServiceTest.registrarEntrada_incrementaStockYRegistraMovimiento", () -> new InventarioServiceTest().registrarEntrada_incrementaStockYRegistraMovimiento());
run("InventarioServiceTest.registrarSalida_disminuyeStockCuandoHaySaldo", () -> new InventarioServiceTest().registrarSalida_disminuyeStockCuandoHaySaldo());
run("InventarioServiceTest.registrarSalida_noPermiteStockInsuficiente", () -> new InventarioServiceTest().registrarSalida_noPermiteStockInsuficiente());
run("InventarioServiceTest.registrarCantidadCero_lanzaExcepcion", () -> new InventarioServiceTest().registrarCantidadCero_lanzaExcepcion());
run("ReporteAlertaDashboardTest.reporte_calculaValorTotalInventario", () -> new ReporteAlertaDashboardTest().reporte_calculaValorTotalInventario());
run("ReporteAlertaDashboardTest.alerta_detectaStockBajoYSinStock", () -> new ReporteAlertaDashboardTest().alerta_detectaStockBajoYSinStock());
run("ReporteAlertaDashboardTest.dashboard_muestraResumenGeneral", () -> new ReporteAlertaDashboardTest().dashboard_muestraResumenGeneral());
run("KataTddTest.stockKata_clasificaSinStockStockBajoYOk", () -> new KataTddTest().stockKata_clasificaSinStockStockBajoYOk());
run("KataTddTest.stockKata_calculaReposicionHastaDobleDelMinimo", () -> new KataTddTest().stockKata_calculaReposicionHastaDobleDelMinimo());
run("KataTddTest.stockKata_calculaValorTotalDeInventario", () -> new KataTddTest().stockKata_calculaValorTotalDeInventario());
run("KataTddTest.skuKata_generaCodigoEstandarizado", () -> new KataTddTest().skuKata_generaCodigoEstandarizado());
run("OrmRepositoryTest.ormMetadata_detectaTablaYColumnas", () -> new OrmRepositoryTest().ormMetadata_detectaTablaYColumnas());
run("OrmRepositoryTest.ormRepository_guardaYRecuperaProducto", () -> new OrmRepositoryTest().ormRepository_guardaYRecuperaProducto());
run("OrmRepositoryTest.ormRepository_actualizaYEliminaProducto", () -> new OrmRepositoryTest().ormRepository_actualizaYEliminaProducto());
        System.out.println("Pruebas ejecutadas: " + ejecutadas + ", fallidas: " + fallidas);
        if (fallidas > 0) {
            throw new AssertionError("Existen pruebas fallidas. Revise el detalle anterior.");
        }
        System.out.println("Todas las pruebas pasaron correctamente.");
    }

    private static void run(String nombre, Runnable prueba) {
        ejecutadas++;
        try {
            prueba.run();
            System.out.println("[OK] " + nombre);
        } catch (Throwable ex) {
            fallidas++;
            System.out.println("[FALLO] " + nombre + " -> " + ex.getMessage());
        }
    }
}
