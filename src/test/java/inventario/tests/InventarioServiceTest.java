package inventario.tests;

import inventario.repository.InMemoryProductoRepository;
import inventario.service.InventarioService;
import inventario.service.ProductoService;
import inventario.testutil.Assertions;

public class InventarioServiceTest {
    public void registrarEntrada_incrementaStockYRegistraMovimiento() {
        ProductoService productoService = new ProductoService(new InMemoryProductoRepository());
        productoService.crearProducto("SSD 1TB", "Almacenamiento", 350, 5, 2);
        InventarioService inventarioService = new InventarioService(productoService);

        boolean registrado = inventarioService.registrarEntrada(1, 7, "Compra");

        Assertions.assertTrue(registrado, "La entrada debe registrarse.");
        Assertions.assertEquals(12, productoService.buscarPorId(1).orElseThrow().getStock(), "El stock debe incrementarse.");
        Assertions.assertEquals(1, inventarioService.listarMovimientos().size(), "Debe existir un movimiento.");
    }

    public void registrarSalida_disminuyeStockCuandoHaySaldo() {
        ProductoService productoService = new ProductoService(new InMemoryProductoRepository());
        productoService.crearProducto("RAM 32GB", "Computo", 420, 10, 3);
        InventarioService inventarioService = new InventarioService(productoService);

        boolean registrado = inventarioService.registrarSalida(1, 4, "Venta");

        Assertions.assertTrue(registrado, "La salida debe registrarse.");
        Assertions.assertEquals(6, productoService.buscarPorId(1).orElseThrow().getStock(), "El stock debe disminuir.");
    }

    public void registrarSalida_noPermiteStockInsuficiente() {
        ProductoService productoService = new ProductoService(new InMemoryProductoRepository());
        productoService.crearProducto("Router", "Redes", 300, 2, 1);
        InventarioService inventarioService = new InventarioService(productoService);

        boolean registrado = inventarioService.registrarSalida(1, 5, "Venta");

        Assertions.assertFalse(registrado, "No debe permitir salida mayor al stock.");
        Assertions.assertEquals(2, productoService.buscarPorId(1).orElseThrow().getStock(), "El stock no debe alterarse.");
    }

    public void registrarCantidadCero_lanzaExcepcion() {
        ProductoService productoService = new ProductoService(new InMemoryProductoRepository());
        productoService.crearProducto("Switch", "Redes", 2100, 5, 2);
        InventarioService inventarioService = new InventarioService(productoService);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> inventarioService.registrarEntrada(1, 0, "Compra"),
                "La cantidad debe ser mayor que cero.");
    }
}
