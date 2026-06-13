import java.util.List;
import java.util.Scanner;

import excepciones.DatoInvalidoException;
import excepciones.ProductoDuplicadoException;
import excepciones.ProductoNoEncontradoException;
import excepciones.StockInsuficienteException;
import modelo.MovimientoInventario;
import modelo.Producto;
import servicio.InventarioService;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final InventarioService inventarioService = new InventarioService();

    public static void main(String[] args) {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    registrarProducto();
                    break;
                case 2:
                    listarProductos();
                    break;
                case 3:
                    buscarProducto();
                    break;
                case 4:
                    aumentarStock();
                    break;
                case 5:
                    disminuirStock();
                    break;
                case 6:
                    eliminarProducto();
                    break;
                case 7:
                    verMovimientos();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }

        } while (opcion != 0);

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n===== SISTEMA DE CONTROL DE INVENTARIO =====");
        System.out.println("1. Registrar producto");
        System.out.println("2. Listar productos");
        System.out.println("3. Buscar producto por código");
        System.out.println("4. Aumentar stock");
        System.out.println("5. Disminuir stock");
        System.out.println("6. Eliminar producto");
        System.out.println("7. Ver movimientos de inventario");
        System.out.println("0. Salir");
    }

    private static void registrarProducto() {
        try {
            System.out.println("\n--- Registrar producto ---");

            String codigo = leerTexto("Código: ");
            String nombre = leerTexto("Nombre: ");
            double precio = leerDecimal("Precio: ");
            int stock = leerEntero("Stock inicial: ");

            Producto producto = new Producto(codigo, nombre, precio, stock);
            inventarioService.registrarProducto(producto);

            System.out.println("Producto registrado correctamente.");

        } catch (ProductoDuplicadoException | DatoInvalidoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarProductos() {
        System.out.println("\n--- Lista de productos ---");

        List<Producto> productos = inventarioService.listarProductos();

        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }

        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }

    private static void buscarProducto() {
        try {
            System.out.println("\n--- Buscar producto ---");

            String codigo = leerTexto("Ingrese el código del producto: ");
            Producto producto = inventarioService.buscarProductoPorCodigo(codigo);

            System.out.println("Producto encontrado:");
            System.out.println(producto);

        } catch (ProductoNoEncontradoException | DatoInvalidoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void aumentarStock() {
        try {
            System.out.println("\n--- Aumentar stock ---");

            String codigo = leerTexto("Código del producto: ");
            int cantidad = leerEntero("Cantidad a aumentar: ");
            String motivo = leerTexto("Motivo: ");

            inventarioService.aumentarStock(codigo, cantidad, motivo);

            System.out.println("Stock actualizado correctamente.");

        } catch (ProductoNoEncontradoException | DatoInvalidoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void disminuirStock() {
        try {
            System.out.println("\n--- Disminuir stock ---");

            String codigo = leerTexto("Código del producto: ");
            int cantidad = leerEntero("Cantidad a disminuir: ");
            String motivo = leerTexto("Motivo: ");

            inventarioService.disminuirStock(codigo, cantidad, motivo);

            System.out.println("Stock actualizado correctamente.");

        } catch (ProductoNoEncontradoException | StockInsuficienteException | DatoInvalidoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void verMovimientos() {
        System.out.println("\n--- Movimientos de inventario ---");

        List<MovimientoInventario> movimientos = inventarioService.listarMovimientos();

        if (movimientos.isEmpty()) {
            System.out.println("No hay movimientos registrados.");
            return;
        }

        for (MovimientoInventario movimiento : movimientos) {
            System.out.println(movimiento);
        }
    }

    private static void eliminarProducto() {
        try {
            System.out.println("\n--- Eliminar producto ---");

            String codigo = leerTexto("Código del producto: ");
            inventarioService.eliminarProducto(codigo);

            System.out.println("Producto eliminado correctamente.");

        } catch (ProductoNoEncontradoException | DatoInvalidoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                int valor = Integer.parseInt(scanner.nextLine());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número entero válido.");
            }
        }
    }

    private static double leerDecimal(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                double valor = Double.parseDouble(scanner.nextLine());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número decimal válido.");
            }
        }
    }
}
