package inventario.tests;

import inventario.model.Producto;
import inventario.repository.InMemoryProductoRepository;
import inventario.service.ProductoService;
import inventario.testutil.Assertions;

public class ProductoServiceTest {
    public void crearProducto_asignaIdYGuardaProducto() {
        ProductoService service = new ProductoService(new InMemoryProductoRepository());

        Producto producto = service.crearProducto("Mouse Logitech", "Perifericos", 85.50, 10, 3);

        Assertions.assertEquals(1, producto.getId(), "El primer producto debe tener ID 1.");
        Assertions.assertEquals(1, service.listarProductos().size(), "El producto debe guardarse en el repositorio.");
        Assertions.assertEquals("Mouse Logitech", service.buscarPorId(1).orElseThrow().getNombre(), "Debe recuperar el producto por ID.");
    }

    public void crearProducto_rechazaPrecioNegativo() {
        ProductoService service = new ProductoService(new InMemoryProductoRepository());

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.crearProducto("Producto invalido", "Prueba", -1, 1, 1),
                "No debe permitir precios negativos.");
    }

    public void actualizarProducto_modificaDatosPrincipales() {
        ProductoService service = new ProductoService(new InMemoryProductoRepository());
        service.crearProducto("Teclado", "Perifericos", 120.0, 4, 2);

        boolean actualizado = service.actualizarProducto(1, "Teclado mecanico", "Perifericos", 190.0, 3);

        Assertions.assertTrue(actualizado, "Debe indicar que el producto fue actualizado.");
        Assertions.assertEquals("Teclado mecanico", service.buscarPorId(1).orElseThrow().getNombre(), "Debe actualizar el nombre.");
        Assertions.assertEquals(190.0, service.buscarPorId(1).orElseThrow().getPrecio(), 0.001, "Debe actualizar el precio.");
    }
}
