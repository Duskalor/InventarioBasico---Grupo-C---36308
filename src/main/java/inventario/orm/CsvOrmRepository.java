package inventario.orm;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Mini ORM educativo: mapea una entidad anotada a una tabla CSV local.
 * La clase permite demostrar el concepto de mapeo objeto-relacional sin depender de librerias externas.
 */
public class CsvOrmRepository<T> {
    private final OrmMetadata<T> metadata;
    private final Path tableFile;

    public CsvOrmRepository(Class<T> type, Path dataDirectory) {
        this.metadata = new OrmMetadata<>(type);
        try {
            Files.createDirectories(dataDirectory);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo crear directorio de datos", e);
        }
        this.tableFile = dataDirectory.resolve(metadata.getTableName() + ".csv");
        inicializarTabla();
    }

    public T save(T entity) {
        Map<String, T> rows = findAll().stream()
                .collect(Collectors.toMap(e -> String.valueOf(metadata.getIdValue(e)), e -> e, (a, b) -> b, LinkedHashMap::new));
        rows.put(String.valueOf(metadata.getIdValue(entity)), entity);
        escribir(new ArrayList<>(rows.values()));
        return entity;
    }

    public Optional<T> findById(int id) {
        return findAll().stream()
                .filter(entity -> String.valueOf(id).equals(String.valueOf(metadata.getIdValue(entity))))
                .findFirst();
    }

    public List<T> findAll() {
        try {
            List<String> lines = Files.readAllLines(tableFile, StandardCharsets.UTF_8);
            if (lines.size() <= 1) return new ArrayList<>();
            List<T> result = new ArrayList<>();
            for (int i = 1; i < lines.size(); i++) {
                if (!lines.get(i).isBlank()) {
                    result.add(toEntity(lines.get(i).split(";", -1)));
                }
            }
            return result;
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo leer la tabla " + tableFile, e);
        }
    }

    public boolean deleteById(int id) {
        List<T> current = findAll();
        int initialSize = current.size();
        current.removeIf(entity -> String.valueOf(id).equals(String.valueOf(metadata.getIdValue(entity))));
        escribir(current);
        return current.size() != initialSize;
    }

    public String getTableName() {
        return metadata.getTableName();
    }

    public List<String> getColumnNames() {
        return metadata.getColumns().stream().map(metadata::getColumnName).collect(Collectors.toList());
    }

    private void inicializarTabla() {
        if (!Files.exists(tableFile)) {
            escribir(new ArrayList<>());
        }
    }

    private void escribir(List<T> entities) {
        List<String> lines = new ArrayList<>();
        lines.add(String.join(";", getColumnNames()));
        for (T entity : entities) {
            lines.add(toRow(entity));
        }
        try {
            Files.write(tableFile, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo escribir la tabla " + tableFile, e);
        }
    }

    private String toRow(T entity) {
        List<String> values = new ArrayList<>();
        for (Field field : metadata.getColumns()) {
            try {
                Object value = field.get(entity);
                values.add(escape(value == null ? "" : String.valueOf(value)));
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("No se pudo leer campo " + field.getName(), e);
            }
        }
        return String.join(";", values);
    }

    private T toEntity(String[] values) {
        try {
            Constructor<T> constructor = metadata.getType().getDeclaredConstructor();
            constructor.setAccessible(true);
            T entity = constructor.newInstance();
            List<Field> fields = metadata.getColumns();
            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                String raw = i < values.length ? unescape(values[i]) : "";
                field.set(entity, convert(raw, field.getType()));
            }
            return entity;
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("No se pudo reconstruir la entidad", e);
        }
    }

    private Object convert(String raw, Class<?> targetType) {
        if (targetType == int.class || targetType == Integer.class) return Integer.parseInt(raw);
        if (targetType == double.class || targetType == Double.class) return Double.parseDouble(raw);
        return raw;
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\").replace(";", "\\;");
    }

    private String unescape(String value) {
        return value.replace("\\;", ";").replace("\\\\", "\\");
    }

}
