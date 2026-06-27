package inventario.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MovimientoInventario {
    private final int id;
    private final int productoId;
    private final String tipo;
    private final int cantidad;
    private final String motivo;
    private final LocalDateTime fecha;

    public MovimientoInventario(int id, int productoId, String tipo, int cantidad, String motivo) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        this.id = id;
        this.productoId = productoId;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.motivo = motivo == null ? "Sin motivo" : motivo;
        this.fecha = LocalDateTime.now();
    }

    public int getId() { return id; }
    public int getProductoId() { return productoId; }
    public String getTipo() { return tipo; }
    public int getCantidad() { return cantidad; }
    public String getMotivo() { return motivo; }
    public LocalDateTime getFecha() { return fecha; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("Movimiento %d | Producto ID: %d | Tipo: %s | Cantidad: %d | Motivo: %s | Fecha: %s",
                id, productoId, tipo, cantidad, motivo, fecha.format(formatter));
    }
}
