package inventario.kata;

public class SkuKata {
    public String generarSku(String categoria, String nombre, int id) {
        if (categoria == null || categoria.isBlank() || nombre == null || nombre.isBlank() || id <= 0) {
            throw new IllegalArgumentException("Categoria, nombre e ID valido son obligatorios.");
        }
        String prefijoCategoria = limpiar(categoria).substring(0, Math.min(3, limpiar(categoria).length())).toUpperCase();
        String prefijoNombre = limpiar(nombre).substring(0, Math.min(3, limpiar(nombre).length())).toUpperCase();
        return prefijoCategoria + "-" + prefijoNombre + "-" + String.format("%04d", id);
    }

    private String limpiar(String texto) {
        return texto.replaceAll("[^A-Za-z0-9]", "");
    }
}
