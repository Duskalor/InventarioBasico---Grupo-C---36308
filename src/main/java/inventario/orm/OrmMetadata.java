package inventario.orm;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrmMetadata<T> {
    private final Class<T> type;
    private final String tableName;
    private final Field idField;
    private final List<Field> columns;

    public OrmMetadata(Class<T> type) {
        this.type = type;
        Table table = type.getAnnotation(Table.class);
        if (table == null) {
            throw new IllegalArgumentException("La clase " + type.getName() + " no tiene @Table.");
        }
        this.tableName = table.name();
        this.columns = new ArrayList<>();
        Field foundId = null;
        for (Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                columns.add(field);
            }
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                foundId = field;
            }
        }
        if (foundId == null) {
            throw new IllegalArgumentException("La clase " + type.getName() + " no tiene @Id.");
        }
        this.idField = foundId;
    }

    public Class<T> getType() { return type; }
    public String getTableName() { return tableName; }
    public Field getIdField() { return idField; }
    public List<Field> getColumns() { return Collections.unmodifiableList(columns); }

    public String getColumnName(Field field) {
        Column column = field.getAnnotation(Column.class);
        return column == null ? field.getName() : column.name();
    }

    public Object getIdValue(T entity) {
        try {
            return idField.get(entity);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("No se pudo leer el ID", e);
        }
    }
}
