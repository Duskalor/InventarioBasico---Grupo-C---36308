package inventario.repository;

import inventario.model.Producto;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryProductoRepository implements ProductoRepository {
    private final Map<Integer, Producto> productos = new LinkedHashMap<>();

    @Override
    public Producto save(Producto producto) {
        productos.put(producto.getId(), producto);
        return producto;
    }

    @Override
    public Optional<Producto> findById(int id) {
        return Optional.ofNullable(productos.get(id));
    }

    @Override
    public List<Producto> findAll() {
        return new ArrayList<>(productos.values());
    }

    @Override
    public boolean deleteById(int id) {
        return productos.remove(id) != null;
    }
}
