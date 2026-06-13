# Tests Unitarios - InventarioBasico

Este proyecto usa **JUnit 5 (Jupiter)** para tests unitarios sin Maven ni Gradle.

## Estructura

```
test/
└── inventario/
    ├── ProductoTest.java
    ├── ProductoServiceTest.java
    ├── InventarioServiceTest.java
    ├── MovimientoInventarioTest.java
    ├── ReporteServiceTest.java
    ├── AlertaServiceTest.java
    └── DashboardServiceTest.java
```

## Requisitos

- Java 14+ (igual que el proyecto principal)
- Conexión a Internet (solo la primera vez para descargar JUnit)

## Ejecutar tests

### Windows (PowerShell / CMD)

```batch
run-tests.bat
```

### Linux / macOS

```bash
chmod +x run-tests.sh
./run-tests.sh
```

### Manual

```bash
# 1. Descargar JUnit Platform Console Standalone (una sola vez)
mkdir -p lib
curl -L -o lib/junit-platform-console-standalone-1.11.4.jar \
  https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.11.4/junit-platform-console-standalone-1.11.4.jar

# 2. Compilar fuentes
javac -d bin -cp lib/junit-platform-console-standalone-1.11.4.jar src/inventario/*.java

# 3. Compilar tests
javac -d bin -cp lib/junit-platform-console-standalone-1.11.4.jar:bin test/inventario/*.java

# 4. Ejecutar
java -jar lib/junit-platform-console-standalone-1.11.4.jar --class-path bin --scan-class-path
```

> En Windows cambia `:` por `;` en el classpath.

## Cobertura de tests

| Clase | Test | Verifica |
|-------|------|----------|
| `Producto` | constructor, getters/setters, calcularValorInventario, toString | Entidad y cálculos |
| `ProductoService` | CRUD, carga de datos iniciales | Lógica de productos |
| `InventarioService` | Entradas, salidas, movimientos, validaciones | Control de stock |
| `MovimientoInventario` | Constructor, getters, fecha, toString | Registro de movimiento |
| `ReporteService` | Valor total, stock bajo, sin stock, categorías, movimientos | Reportes |
| `AlertaService` | Alertas crítica, advertencia, OK | Detección de riesgos |
| `DashboardService` | mostrarDashboard sin excepciones, métricas en salida | Panel general |
