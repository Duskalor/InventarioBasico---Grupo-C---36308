package inventario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

class ProductoServiceTest {

    private ProductoService service;

    @BeforeEach
    void setUp() {
        service = new ProductoService();
    }

    @Test
    void crearProducto_debeAsignarIdIncremental() {
        Producto p1 = service.crearProducto("A", "Cat", 10.0, 1, 1);
        Producto p2 = service.crearProducto("B", "Cat", 20.0, 2, 2);
        assertEquals(1, p1.getId());
        assertEquals(2, p2.getId());
    }

    @Test
    void listarProductos_debeDevolverCopiaVaciaInicialmente() {
        List<Producto> productos = service.listarProductos();
        assertTrue(productos.isEmpty());
    }

    @Test
    void buscarPorId_debeEncontrarProductoExistente() {
        Producto p = service.crearProducto("A", "Cat", 10.0, 1, 1);
        Optional<Producto> resultado = service.buscarPorId(p.getId());
        assertTrue(resultado.isPresent());
        assertEquals("A", resultado.get().getNombre());
    }

    @Test
    void buscarPorId_debeDevolverVacioSiNoExiste() {
        Optional<Producto> resultado = service.buscarPorId(999);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void actualizarProducto_debeModificarProductoExistente() {
        Producto p = service.crearProducto("A", "Cat", 10.0, 1, 1);
        boolean actualizado = service.actualizarProducto(p.getId(), "B", "Dog", 20.0, 5);
        assertTrue(actualizado);
        Producto modificado = service.buscarPorId(p.getId()).get();
        assertEquals("B", modificado.getNombre());
        assertEquals("Dog", modificado.getCategoria());
        assertEquals(20.0, modificado.getPrecio());
        assertEquals(5, modificado.getStockMinimo());
    }

    @Test
    void actualizarProducto_debeDevolverFalseSiNoExiste() {
        boolean actualizado = service.actualizarProducto(999, "B", "Dog", 20.0, 5);
        assertFalse(actualizado);
    }

    @Test
    void eliminarProducto_debeBorrarProductoExistente() {
        Producto p = service.crearProducto("A", "Cat", 10.0, 1, 1);
        boolean eliminado = service.eliminarProducto(p.getId());
        assertTrue(eliminado);
        assertTrue(service.buscarPorId(p.getId()).isEmpty());
    }

    @Test
    void eliminarProducto_debeDevolverFalseSiNoExiste() {
        boolean eliminado = service.eliminarProducto(999);
        assertFalse(eliminado);
    }

    @Test
    void cargarDatosIniciales_debeCrearCincoProductos() {
        service.cargarDatosIniciales();
        assertEquals(5, service.listarProductos().size());
    }
}
