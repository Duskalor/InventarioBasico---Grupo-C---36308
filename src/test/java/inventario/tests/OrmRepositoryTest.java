package inventario.tests;

import inventario.model.Producto;
import inventario.orm.CsvOrmRepository;
import inventario.repository.OrmProductoRepository;
import inventario.service.ProductoService;
import inventario.testutil.Assertions;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.Path;

public class OrmRepositoryTest {
    public void ormMetadata_detectaTablaYColumnas() {
        Path dir = tempDir();
        CsvOrmRepository<Producto> orm = new CsvOrmRepository<>(Producto.class, dir);

        Assertions.assertEquals("productos", orm.getTableName(), "Debe mapear la entidad a la tabla productos.");
        Assertions.assertTrue(orm.getColumnNames().contains("stock_minimo"), "Debe mapear el campo stockMinimo a stock_minimo.");
    }

    public void ormRepository_guardaYRecuperaProducto() {
        Path dir = tempDir();
        ProductoService service = new ProductoService(new OrmProductoRepository(dir));

        service.crearProducto("Firewall Fortinet", "Seguridad", 2500, 3, 1);
        Producto recuperado = service.buscarPorId(1).orElseThrow();

        Assertions.assertEquals("Firewall Fortinet", recuperado.getNombre(), "Debe recuperar el producto persistido.");
        Assertions.assertEquals(2500.0, recuperado.getPrecio(), 0.001, "Debe conservar el precio.");
    }

    public void ormRepository_actualizaYEliminaProducto() {
        Path dir = tempDir();
        ProductoService service = new ProductoService(new OrmProductoRepository(dir));
        service.crearProducto("AP", "Redes", 700, 5, 2);

        boolean actualizado = service.actualizarProducto(1, "Access Point WiFi 6", "Redes", 780, 3);
        boolean eliminado = service.eliminarProducto(1);

        Assertions.assertTrue(actualizado, "Debe actualizar usando el repositorio ORM.");
        Assertions.assertTrue(eliminado, "Debe eliminar usando el repositorio ORM.");
        Assertions.assertFalse(service.buscarPorId(1).isPresent(), "No debe existir el producto eliminado.");
    }

    private Path tempDir() {
        try {
            return Files.createTempDirectory("inventario-orm-test");
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo crear directorio temporal", e);
        }
    }
}

