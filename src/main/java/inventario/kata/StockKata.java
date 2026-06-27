package inventario.kata;

import inventario.model.Producto;
import java.util.List;

public class StockKata {
    public String clasificarStock(int stock, int stockMinimo) {
        if (stock < 0 || stockMinimo < 0) {
            throw new IllegalArgumentException("Stock y stock minimo no pueden ser negativos.");
        }
        if (stock == 0) return "SIN_STOCK";
        if (stock <= stockMinimo) return "STOCK_BAJO";
        return "OK";
    }

    public boolean esStockBajo(int stock, int stockMinimo) {
        return "STOCK_BAJO".equals(clasificarStock(stock, stockMinimo));
    }

    public int calcularCantidadReposicion(int stock, int stockMinimo) {
        if (stock < 0 || stockMinimo < 0) {
            throw new IllegalArgumentException("Stock y stock minimo no pueden ser negativos.");
        }
        int objetivo = stockMinimo * 2;
        return Math.max(0, objetivo - stock);
    }

    public double calcularValorTotal(List<Producto> productos) {
        return productos.stream().mapToDouble(Producto::calcularValorInventario).sum();
    }
}
