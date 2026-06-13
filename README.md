# 📦 InventarioBasico

Sistema de control de inventario por consola en **Java puro**, sin dependencias externas, desarrollado como proyecto académico en la **Universidad Continental** (Carrera de Ingeniería de Sistemas e Informática).

El objetivo es aplicar y afianzar los fundamentos de la Programación Orientada a Objetos en Java: clases, herencia, excepciones personalizadas, validación de datos y separación en capas (arquitectura limpia por niveles de responsabilidad). El sistema permite administrar productos de un inventario (alta, baja, búsqueda y control de stock) garantizando la integridad de los datos mediante validaciones de dominio robustas.

## 👥 Equipo de Desarrollo

Proyecto desarrollado por estudiantes de la **Universidad Continental** como parte de actividades académicas y de investigación.

### Integrantes

- Jack Michel Congache Rodriguez
- Israel Ariel Fernandez León
- Ryuka Kusi Qoyllor Caceres Aoki
- Paul Antonio Cruz Conde
- Abigail Escobar Pedraza

### Institución

**Universidad Continental**
Facultad de Ingeniería
Carrera de Ingeniería de Sistemas e Informática

## ✨ Funcionalidades

- **Crear producto** — alta con categoría, precio, stock y stock mínimo
- **Listar productos** — vista completa del inventario con valor por producto
- **Buscar por ID** — búsqueda exacta por ID numérico
- **Actualizar producto** — modificar nombre, categoría, precio y stock mínimo
- **Eliminar producto** — baja por ID
- **Registrar entrada/salida de stock** — control de cantidades con motivo
- **Ver movimientos de inventario** — historial de entradas y salidas con fecha y motivo
- **Alertas de stock** — alertas críticas (stock agotado) y advertencias (stock bajo) con cantidad sugerida de compra
- **Dashboard general** — métricas clave, gráfico de barras ASCII por categoría, últimos movimientos y alertas activas
- **Reportes:**
  - Valor total del inventario
  - Productos con stock bajo (stock > 0 y ≤ stock mínimo)
  - Productos sin stock
  - Cantidad de productos por categoría
  - Stock total por categoría
- **Datos iniciales** — 5 productos precargados al iniciar

## 🧱 Arquitectura

```
src/
└── inventario/
    ├── Main.java                    # UI por consola (menú + entrada de datos)
    ├── Producto.java                # Entidad del dominio (categoría, stock mínimo)
    ├── MovimientoInventario.java    # Registro de entradas/salidas de stock
    ├── ProductoService.java         # CRUD de productos
    ├── InventarioService.java       # Lógica de movimientos de stock
    ├── ReporteService.java          # Reportes y consultas agregadas
    ├── AlertaService.java           # Alertas de stock bajo/agotado
    └── DashboardService.java        # Dashboard con métricas y gráfico ASCII
```

### Capas

| Capa | Responsabilidad |
|------|----------------|
| **Presentación** (`Main.java`) | Menú interactivo, lectura de datos |
| **Servicio** (`ProductoService`, `InventarioService`, `ReporteService`, `AlertaService`, `DashboardService`) | Reglas de negocio, CRUD, reportes, alertas, dashboard |
| **Modelo** (`Producto`, `MovimientoInventario`) | Representación del dominio |

## 🚀 Cómo ejecutar

```bash
# Compilar (Linux/macOS)
javac -d bin src/**/*.java

# Compilar (Windows - PowerShell)
javac -d bin (Get-ChildItem -Recurse src/*.java).FullName

# Ejecutar
java -cp bin inventario.Main
```

> Requiere **Java 14+** (switch con flechas).

## 🧪 Tests Unitarios (JUnit 5)

El proyecto incluye **41 tests unitarios** con **JUnit 5 (Jupiter)** sin Maven ni Gradle.

### Estructura de tests

```
test/
└── inventario/
    ├── ProductoTest.java              (4 tests)
    ├── ProductoServiceTest.java       (9 tests)
    ├── InventarioServiceTest.java   (8 tests)
    ├── MovimientoInventarioTest.java (3 tests)
    ├── ReporteServiceTest.java      (7 tests)
    ├── AlertaServiceTest.java       (5 tests)
    └── DashboardServiceTest.java    (5 tests)
```

### Cobertura de tests

| Clase | Tests | Qué verifica |
|-------|-------|-------------|
| `Producto` | Constructor, getters/setters, calcularValorInventario, toString | Entidad y cálculos |
| `ProductoService` | CRUD, IDs incrementales, copia de lista, datos iniciales | Lógica de productos |
| `InventarioService` | Entradas, salidas, movimientos, stock insuficiente, cantidad inválida | Control de stock |
| `MovimientoInventario` | Constructor, getters, fecha, toString | Registro de movimiento |
| `ReporteService` | Valor total, stock bajo, sin stock, categorías, últimos movimientos | Reportes |
| `AlertaService` | Alertas crítica, advertencia, OK, combinaciones | Detección de riesgos |
| `DashboardService` | mostrarDashboard sin excepciones, métricas, gráfico ASCII, alertas | Panel general |

### Cómo ejecutar tests

#### Opción 1: Script automático (Windows)
```batch
.\run-tests.bat
```
**Primera vez:** Descarga automáticamente JUnit 5 en `lib/`. Después solo compila y ejecuta.

#### Opción 2: Script automático (Linux/macOS)
```bash
chmod +x run-tests.sh
./run-tests.sh
```

#### Opción 3: Manual
```batch
# 1. Descargar JUnit (una sola vez)
mkdir lib
powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.11.4/junit-platform-console-standalone-1.11.4.jar' -OutFile 'lib\junit-platform-console-standalone-1.11.4.jar'"

# 2. Compilar fuentes
javac -d bin -cp lib\junit-platform-console-standalone-1.11.4.jar src\inventario\*.java

# 3. Compilar tests
javac -d bin -cp lib\junit-platform-console-standalone-1.11.4.jar;bin test\inventario\*.java

# 4. Ejecutar
java -jar lib\junit-platform-console-standalone-1.11.4.jar --class-path bin --scan-class-path
```

---

## 📋 Historial de versiones

| Versión | Cambios |
|---------|---------|
| **v1.0** | Versión inicial: CRUD básico con validaciones, excepciones checked, código alfanumérico |
| **v1.1** | Movimientos de inventario: registro de entradas/salidas con fecha, hora y motivo; aumentar/disminuir stock |
| **v1.2** | Reportes, categorías y stock mínimo: ID numérico autoincremental, separación de servicios (`ProductoService` + `InventarioService`), `ReporteService` con 5 reportes, actualización de productos, datos iniciales precargados, `Optional` en lugar de excepciones |
| **v1.3** | Alertas de stock: `AlertaService` con alertas críticas (stock agotado) y advertencias (stock bajo con cantidad sugerida de compra), opción 10 en menú principal. Reestructuración a paquete `inventario` plano |
| **v2.0** | Dashboard general: `DashboardService` con métricas clave (total productos, unidades en almacén, valor total, stock bajo/sin stock, producto de mayor valor), gráfico de barras ASCII por categoría, últimos 5 movimientos y alertas activas |
| **v2.1** | Tests unitarios: 41 tests JUnit 5 para todas las clases del dominio |

## 🧠 Decisiones técnicas

- **Sin frameworks** — Java SE puro, sin Maven, Gradle ni librerías externas
- **Datos en memoria** — `ArrayList<Producto>` sin persistencia (ideal para empezar)
- **Optional** — búsquedas con `Optional` para evitar null pointer exceptions
- **Separación de servicios** — `ProductoService` para CRUD, `InventarioService` para movimientos, `ReporteService` para consultas, `AlertaService` para alertas, `DashboardService` para dashboard
- **Switch con flechas** — sintaxis moderna de Java 14+ para mayor legibilidad
- **Tests con JUnit 5** — JUnit Jupiter sin dependencias de build tools, descarga manual del standalone

## 📌 Pendiente / Próximos pasos

- [ ] Persistencia a archivo (CSV, JSON, o serialización)
- [x] Tests unitarios (JUnit) ✅
- [ ] Interfaz gráfica (Swing/JavaFX)
- [ ] Migración a Maven/Gradle
- [ ] Conexión a base de datos
