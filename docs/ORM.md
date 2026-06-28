# Implementacion ORM - v3.2

El sistema implementa un mini ORM educativo para demostrar el mapeo Objeto-Relacional solicitado en el PA3.

## Entidad mapeada

La clase `Producto` contiene anotaciones:

- `@Table(name = "productos")`
- `@Id`
- `@Column(name = "id")`
- `@Column(name = "nombre")`
- `@Column(name = "categoria")`
- `@Column(name = "precio")`
- `@Column(name = "stock")`
- `@Column(name = "stock_minimo")`

## Repositorio

`OrmProductoRepository` utiliza `CsvOrmRepository<Producto>` para guardar, buscar, listar y eliminar productos. De esta manera, los servicios del sistema no dependen de la tecnologia de persistencia.

## Pruebas ORM

Las pruebas `OrmRepositoryTest` verifican:

1. Deteccion de tabla y columnas por anotaciones.
2. Guardado y recuperacion de productos.
3. Actualizacion y eliminacion usando el repositorio ORM.
