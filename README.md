# Sistema de Inventario Basico

Sistema de inventario en Java puro, construido de forma incremental con pruebas unitarias, Katas TDD y un mini ORM educativo.

## Arquitectura

El proyecto sigue una estructura por capas con layout estilo Maven:

```
src/main/java/inventario/
├── Main.java              # Punto de entrada
├── model/                 # Entidades (Producto, MovimientoInventario)
├── repository/            # Persistencia (InMemory y ORM sobre CSV)
├── service/               # Logica de negocio (Producto, Inventario, Reporte, Alerta, Dashboard)
├── orm/                   # Mini ORM por anotaciones
└── kata/                  # Katas TDD (StockKata, SkuKata)

src/test/java/inventario/
├── tests/                 # Suite de pruebas + runner AllTests
└── testutil/              # Utilidades de aserciones
```

Los servicios dependen de la interfaz `ProductoRepository`, no de una tecnologia de persistencia concreta. Esto permite cambiar entre almacenamiento en memoria (`InMemoryProductoRepository`) y persistencia con el ORM (`OrmProductoRepository`) sin tocar la logica de negocio.

## Mini ORM (v3.2)

ORM educativo basado en anotaciones Java y persistencia en tablas CSV locales:

- `@Table` — define el nombre de la tabla.
- `@Column` — define el nombre de la columna.
- `@Id` — identifica la clave primaria.
- `OrmMetadata` — lee las anotaciones por reflexion.
- `CsvOrmRepository<T>` — mapea entidades Java a una tabla CSV.
- `OrmProductoRepository` — adapta el ORM al repositorio de productos.

Los datos persistidos se guardan en la carpeta `data/`. Detalle completo en [docs/ORM.md](docs/ORM.md).

## Katas TDD

- `StockKata` — clasificacion de stock, cantidad sugerida de reposicion y valor total del inventario.
- `SkuKata` — generacion de codigos SKU estandarizados.

Documentacion del ciclo en [docs/TDD_RED_GREEN_REFACTOR.md](docs/TDD_RED_GREEN_REFACTOR.md) y [docs/KATAS_TDD.md](docs/KATAS_TDD.md).

## Ejecutar las pruebas

```bash
bash scripts/test.sh
```

En Windows:

```bat
scripts\test.bat
```

La suite compila todo el proyecto y ejecuta el runner `inventario.tests.AllTests` (17 pruebas, sin dependencias externas).

## Ejecutar el sistema

```bash
bash scripts/run.sh
```

## Integracion continua

El workflow [`.github/workflows/ci.yml`](.github/workflows/ci.yml) corre en cada push y pull request sobre `main`: instala JDK 17 y ejecuta la suite completa de pruebas.

## Requisitos

- JDK 11 o superior (el codigo usa unicamente APIs estandar).
