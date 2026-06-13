package inventario;

public class Producto {
    private int id;
    private String nombre;
    private String categoria;
    private double precio;
    private int stock;
    private int stockMinimo;

    public Producto(int id, String nombre, String categoria, double precio, int stock, int stockMinimo) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }

    public double calcularValorInventario() {
        return precio * stock;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Producto: %s | Categoría: %s | Precio: S/ %.2f | Stock: %d | Stock mínimo: %d | Valor: S/ %.2f",
                id, nombre, categoria, precio, stock, stockMinimo, calcularValorInventario());
    }
}
