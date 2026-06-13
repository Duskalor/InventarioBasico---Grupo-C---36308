package inventario;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ProductoService productoService = new ProductoService();
    private static final InventarioService inventarioService = new InventarioService(productoService);
    private static final ReporteService reporteService = new ReporteService(productoService, inventarioService);
    private static final AlertaService alertaService = new AlertaService(productoService);
    private static final DashboardService dashboardService = new DashboardService(productoService, reporteService, alertaService);

    public static void main(String[] args) {
        productoService.cargarDatosIniciales();
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> crearProducto();
                case 2 -> listarProductos();
                case 3 -> buscarProducto();
                case 4 -> actualizarProducto();
                case 5 -> eliminarProducto();
                case 6 -> registrarEntrada();
                case 7 -> registrarSalida();
                case 8 -> listarMovimientos();
                case 9 -> menuReportes();
                case 10 -> mostrarAlertas();
                case 11 -> dashboardService.mostrarDashboard();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n=== Sistema de Control de Inventarios v2.0 - Dashboard ===");
        System.out.println("1. Crear producto");
        System.out.println("2. Listar productos");
        System.out.println("3. Buscar producto por ID");
        System.out.println("4. Actualizar producto");
        System.out.println("5. Eliminar producto");
        System.out.println("6. Registrar entrada de stock");
        System.out.println("7. Registrar salida de stock");
        System.out.println("8. Ver movimientos de inventario");
        System.out.println("9. Reportes");
        System.out.println("10. Ver alertas de stock");
        System.out.println("11. Dashboard general");
        System.out.println("0. Salir");
    }

    private static void crearProducto() {
        System.out.println("\n--- Crear producto ---");
        String nombre = leerTexto("Nombre: ");
        String categoria = leerTexto("Categoría: ");
        double precio = leerDecimal("Precio: ");
        int stock = leerEntero("Stock inicial: ");
        int stockMinimo = leerEntero("Stock mínimo: ");

        Producto producto = productoService.crearProducto(nombre, categoria, precio, stock, stockMinimo);
        System.out.println("Producto creado: " + producto);
    }

    private static void listarProductos() {
        System.out.println("\n--- Lista de productos ---");
        List<Producto> productos = productoService.listarProductos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        productos.forEach(System.out::println);
    }

    private static void buscarProducto() {
        int id = leerEntero("Ingrese ID del producto: ");
        productoService.buscarPorId(id)
                .ifPresentOrElse(System.out::println, () -> System.out.println("Producto no encontrado."));
    }

    private static void actualizarProducto() {
        System.out.println("\n--- Actualizar producto ---");
        int id = leerEntero("ID del producto: ");
        String nombre = leerTexto("Nuevo nombre: ");
        String categoria = leerTexto("Nueva categoría: ");
        double precio = leerDecimal("Nuevo precio: ");
        int stockMinimo = leerEntero("Nuevo stock mínimo: ");

        boolean actualizado = productoService.actualizarProducto(id, nombre, categoria, precio, stockMinimo);
        System.out.println(actualizado ? "Producto actualizado correctamente." : "Producto no encontrado.");
    }

    private static void eliminarProducto() {
        int id = leerEntero("ID del producto a eliminar: ");
        boolean eliminado = productoService.eliminarProducto(id);
        System.out.println(eliminado ? "Producto eliminado correctamente." : "Producto no encontrado.");
    }

    private static void registrarEntrada() {
        int productoId = leerEntero("ID del producto: ");
        int cantidad = leerEntero("Cantidad de entrada: ");
        String motivo = leerTexto("Motivo: ");
        boolean registrado = inventarioService.registrarEntrada(productoId, cantidad, motivo);
        System.out.println(registrado ? "Entrada registrada correctamente." : "No se pudo registrar la entrada.");
    }

    private static void registrarSalida() {
        int productoId = leerEntero("ID del producto: ");
        int cantidad = leerEntero("Cantidad de salida: ");
        String motivo = leerTexto("Motivo: ");
        boolean registrado = inventarioService.registrarSalida(productoId, cantidad, motivo);
        System.out.println(registrado ? "Salida registrada correctamente." : "No se pudo registrar la salida.");
    }

    private static void listarMovimientos() {
        System.out.println("\n--- Movimientos de inventario ---");
        List<MovimientoInventario> movimientos = inventarioService.listarMovimientos();
        if (movimientos.isEmpty()) {
            System.out.println("No hay movimientos registrados.");
            return;
        }
        movimientos.forEach(System.out::println);
    }

    private static void menuReportes() {
        int opcion;
        do {
            System.out.println("\n--- Menú de reportes ---");
            System.out.println("1. Valor total del inventario");
            System.out.println("2. Productos con stock bajo");
            System.out.println("3. Productos sin stock");
            System.out.println("4. Cantidad de productos por categoría");
            System.out.println("5. Stock total por categoría");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> System.out.printf("Valor total del inventario: S/ %.2f%n", reporteService.calcularValorTotalInventario());
                case 2 -> imprimirListaProductos(reporteService.listarProductosConStockBajo(), "No hay productos con stock bajo.");
                case 3 -> imprimirListaProductos(reporteService.listarProductosSinStock(), "No hay productos sin stock.");
                case 4 -> imprimirMapaLong(reporteService.contarProductosPorCategoria());
                case 5 -> imprimirMapaInteger(reporteService.sumarStockPorCategoria());
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void mostrarAlertas() {
        System.out.println("\n--- Alertas del sistema ---");
        alertaService.generarAlertas().forEach(System.out::println);
    }

    private static void imprimirListaProductos(List<Producto> productos, String mensajeVacio) {
        if (productos.isEmpty()) {
            System.out.println(mensajeVacio);
            return;
        }
        productos.forEach(System.out::println);
    }

    private static void imprimirMapaLong(Map<String, Long> mapa) {
        mapa.forEach((categoria, cantidad) -> System.out.println(categoria + ": " + cantidad + " producto(s)"));
    }

    private static void imprimirMapaInteger(Map<String, Integer> mapa) {
        mapa.forEach((categoria, cantidad) -> System.out.println(categoria + ": " + cantidad + " unidad(es)"));
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    private static double leerDecimal(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número decimal válido.");
            }
        }
    }
}
