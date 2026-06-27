package inventario.tests;

import inventario.kata.SkuKata;
import inventario.kata.StockKata;
import inventario.model.Producto;
import inventario.testutil.Assertions;
import java.util.List;

public class KataTddTest {
    public void stockKata_clasificaSinStockStockBajoYOk() {
        StockKata kata = new StockKata();

        Assertions.assertEquals("SIN_STOCK", kata.clasificarStock(0, 5), "Stock cero debe ser critico.");
        Assertions.assertEquals("STOCK_BAJO", kata.clasificarStock(3, 5), "Stock menor o igual al minimo debe ser bajo.");
        Assertions.assertEquals("OK", kata.clasificarStock(10, 5), "Stock mayor al minimo debe estar OK.");
    }

    public void stockKata_calculaReposicionHastaDobleDelMinimo() {
        StockKata kata = new StockKata();

        Assertions.assertEquals(7, kata.calcularCantidadReposicion(3, 5), "Debe sugerir hasta doble del minimo.");
        Assertions.assertEquals(0, kata.calcularCantidadReposicion(12, 5), "No debe sugerir reposicion si supera el objetivo.");
    }

    public void stockKata_calculaValorTotalDeInventario() {
        StockKata kata = new StockKata();
        List<Producto> productos = List.of(
                new Producto(1, "Laptop", "Computo", 3000, 2, 1),
                new Producto(2, "Mouse", "Perifericos", 50, 4, 2));

        Assertions.assertEquals(6200.0, kata.calcularValorTotal(productos), 0.001, "Debe calcular el valor total.");
    }

    public void skuKata_generaCodigoEstandarizado() {
        SkuKata kata = new SkuKata();

        String sku = kata.generarSku("Redes", "Switch Cisco", 7);

        Assertions.assertEquals("RED-SWI-0007", sku, "Debe generar SKU con categoria, nombre e ID.");
    }
}
