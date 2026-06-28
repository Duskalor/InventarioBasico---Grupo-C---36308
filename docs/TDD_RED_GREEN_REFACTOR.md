# Evidencia TDD - v3.0

## Iteracion 1: crear producto
- Red: se escribio la prueba `crearProducto_asignaIdYGuardaProducto` antes de completar la logica del repositorio.
- Green: se implemento `ProductoService.crearProducto` con asignacion incremental de ID.
- Refactor: se separo la capa `ProductoRepository` para aislar persistencia y facilitar pruebas.

## Iteracion 2: entradas y salidas
- Red: se definieron pruebas para registrar entrada, salida y salida con stock insuficiente.
- Green: se implemento `InventarioService` con reglas de cantidad positiva y validacion de stock.
- Refactor: se eliminaron mensajes de consola en reglas de negocio y se devolvieron resultados booleanos.

## Iteracion 3: reportes y alertas
- Red: se escribieron pruebas para valor total del inventario y alertas.
- Green: se implementaron `ReporteService`, `AlertaService` y `DashboardService`.
- Refactor: se reutilizaron servicios existentes y se mantuvo una salida de dashboard como texto verificable.


Ver tambien `docs/KATAS_TDD.md`.


## Iteracion 4: ORM
- Red: se agregaron pruebas para guardar y recuperar productos usando repositorio ORM.
- Green: se implemento `CsvOrmRepository` con anotaciones `@Table`, `@Column` e `@Id`.
- Refactor: `ProductoService` trabaja contra la interfaz `ProductoRepository`, manteniendo bajo acoplamiento.
