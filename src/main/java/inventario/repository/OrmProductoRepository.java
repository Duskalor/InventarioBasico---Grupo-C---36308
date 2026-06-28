package inventario.repository;

import inventario.model.Producto;
import inventario.orm.CsvOrmRepository;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class OrmProductoRepository implements ProductoRepository {
    private final CsvOrmRepository<Producto> ormRepository;

    public OrmProductoRepository(Path dataDirectory) {
        this.ormRepository = new CsvOrmRepository<>(Producto.class, dataDirectory);
    }

    @Override
    public Producto save(Producto producto) {
        return ormRepository.save(producto);
    }

    @Override
    public Optional<Producto> findById(int id) {
        return ormRepository.findById(id);
    }

    @Override
    public List<Producto> findAll() {
        return ormRepository.findAll();
    }

    @Override
    public boolean deleteById(int id) {
        return ormRepository.deleteById(id);
    }
}
