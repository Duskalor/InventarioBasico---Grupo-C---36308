package inventario.model;

        import inventario.orm.Column;
import inventario.orm.Id;
import inventario.orm.Table;

@Table(name = "productos")
public class Producto {
            @Id
    @Column(name = "id")
    private int id;
            @Column(name = "nombre")
    private String nombre;
            @Column(name = "categoria")
    private String categoria;
            @Column(name = "precio")
    private double precio;
            @Column(name = "stock")
    private int stock;
            @Column(name = "stock_minimo")
    private int stockMinimo;


    public Producto() {
        // Constructor requerido por el mini ORM.
    }

            public Producto(int id, String nombre, String categoria, double precio, int stock, int stockMinimo) {
                validarTexto(nombre, "nombre");
                validarTexto(categoria, "categoria");
                validarNoNegativo(precio, "precio");
                validarNoNegativo(stock, "stock");
                validarNoNegativo(stockMinimo, "stock minimo");
                this.id = id;
                this.nombre = nombre;
                this.categoria = categoria;
                this.precio = precio;
                this.stock = stock;
                this.stockMinimo = stockMinimo;
            }

            public int getId() { return id; }
    public void setId(int id) { this.id = id; }

            public String getNombre() { return nombre; }
            public void setNombre(String nombre) { validarTexto(nombre, "nombre"); this.nombre = nombre; }
            public String getCategoria() { return categoria; }
            public void setCategoria(String categoria) { validarTexto(categoria, "categoria"); this.categoria = categoria; }
            public double getPrecio() { return precio; }
            public void setPrecio(double precio) { validarNoNegativo(precio, "precio"); this.precio = precio; }
            public int getStock() { return stock; }
            public void setStock(int stock) { validarNoNegativo(stock, "stock"); this.stock = stock; }
            public int getStockMinimo() { return stockMinimo; }
            public void setStockMinimo(int stockMinimo) { validarNoNegativo(stockMinimo, "stock minimo"); this.stockMinimo = stockMinimo; }

            public double calcularValorInventario() {
                return precio * stock;
            }

            private static void validarTexto(String valor, String campo) {
                if (valor == null || valor.trim().isEmpty()) {
                    throw new IllegalArgumentException("El campo " + campo + " es obligatorio.");
                }
            }

            private static void validarNoNegativo(double valor, String campo) {
                if (valor < 0) {
                    throw new IllegalArgumentException("El campo " + campo + " no puede ser negativo.");
                }
            }

            @Override
            public String toString() {
                return String.format("ID: %d | Producto: %s | Categoria: %s | Precio: S/ %.2f | Stock: %d | Stock minimo: %d | Valor: S/ %.2f",
                        id, nombre, categoria, precio, stock, stockMinimo, calcularValorInventario());
            }
        }
