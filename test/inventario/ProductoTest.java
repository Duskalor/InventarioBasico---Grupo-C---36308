package inventario;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductoTest {

    @Test
    void constructor_debeAsignarValoresCorrectamente() {
        Producto p = new Producto(1, "Laptop", "Computo", 3500.0, 8, 3);
        assertEquals(1, p.getId());
        assertEquals("Laptop", p.getNombre());
        assertEquals("Computo", p.getCategoria());
        assertEquals(3500.0, p.getPrecio());
        assertEquals(8, p.getStock());
        assertEquals(3, p.getStockMinimo());
    }

    @Test
    void setters_debenActualizarValores() {
        Producto p = new Producto(1, "Laptop", "Computo", 3500.0, 8, 3);
        p.setNombre("Desktop");
        p.setCategoria("Oficina");
        p.setPrecio(2000.0);
        p.setStock(5);
        p.setStockMinimo(2);
        assertEquals("Desktop", p.getNombre());
        assertEquals("Oficina", p.getCategoria());
        assertEquals(2000.0, p.getPrecio());
        assertEquals(5, p.getStock());
        assertEquals(2, p.getStockMinimo());
    }

    @Test
    void calcularValorInventario_debeMultiplicarPrecioPorStock() {
        Producto p = new Producto(1, "Laptop", "Computo", 100.0, 5, 2);
        assertEquals(500.0, p.calcularValorInventario());
    }

    @Test
    void toString_debeContenerInformacionDelProducto() {
        Producto p = new Producto(1, "Laptop", "Computo", 3500.0, 8, 3);
        String texto = p.toString();
        assertTrue(texto.contains("ID: 1"));
        assertTrue(texto.contains("Producto: Laptop"));
        assertTrue(texto.contains("Categoría: Computo"));
        assertTrue(texto.contains("3500"));
        assertTrue(texto.contains("Stock: 8"));
        assertTrue(texto.contains("Stock mínimo: 3"));
        assertTrue(texto.contains("28000"));
    }
}
