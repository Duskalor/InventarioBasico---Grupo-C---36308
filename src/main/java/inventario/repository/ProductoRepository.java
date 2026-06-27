package inventario.repository;

import inventario.model.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository {
    Producto save(Producto producto);
    Optional<Producto> findById(int id);
    List<Producto> findAll();
    boolean deleteById(int id);
}
