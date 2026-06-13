package inventario;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class MovimientoInventarioTest {

    @Test
    void constructor_debeAsignarValoresCorrectamente() {
        MovimientoInventario m = new MovimientoInventario(1, 10, "ENTRADA", 5, "Compra");
        assertEquals(1, m.getId());
        assertEquals(10, m.getProductoId());
        assertEquals("ENTRADA", m.getTipo());
        assertEquals(5, m.getCantidad());
        assertEquals("Compra", m.getMotivo());
    }

    @Test
    void getFecha_debeSerNoNuloYAproximadamenteAhora() {
        MovimientoInventario m = new MovimientoInventario(1, 10, "ENTRADA", 5, "Compra");
        assertNotNull(m.getFecha());
        LocalDateTime ahora = LocalDateTime.now();
        assertTrue(m.getFecha().isBefore(ahora.plusSeconds(1)) && m.getFecha().isAfter(ahora.minusSeconds(1)));
    }

    @Test
    void toString_debeContenerInformacionDelMovimiento() {
        MovimientoInventario m = new MovimientoInventario(1, 10, "ENTRADA", 5, "Compra");
        String texto = m.toString();
        assertTrue(texto.contains("Movimiento 1"));
        assertTrue(texto.contains("Producto ID: 10"));
        assertTrue(texto.contains("Tipo: ENTRADA"));
        assertTrue(texto.contains("Cantidad: 5"));
        assertTrue(texto.contains("Motivo: Compra"));
    }
}
