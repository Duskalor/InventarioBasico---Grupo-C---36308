# 📦 InventarioBasico

Sistema de control de inventario por consola en **Java puro**, sin dependencias externas. Ideal para aprender los fundamentos de POO en Java: clases, herencia, excepciones personalizadas, validación de datos y separación en capas.

## ✨ Funcionalidades

- **Registrar producto** — alta con validación de datos (código único, nombre, precio, stock)
- **Listar productos** — vista completa del inventario
- **Buscar por código** — búsqueda exacta case-insensitive
- **Aumentar/Disminuir stock** — control de cantidades y stock mínimo
- **Eliminar producto** — baja por código

## 🧱 Arquitectura

```
src/
├── Main.java                        # UI por consola (menú + entrada de datos)
├── modelo/
│   └── Producto.java                # Entidad del dominio
├── servicio/
│   └── InventarioService.java       # Lógica de negocio + validaciones
└── excepciones/
    ├── DatoInvalidoException.java   # Campos vacíos, valores inválidos
    ├── ProductoDuplicadoException.java
    ├── ProductoNoEncontradoException.java
    └── StockInsuficienteException.java
```

### Capas

| Capa | Responsabilidad |
|------|----------------|
| **Presentación** (`Main.java`) | Menú interactivo, lectura de datos, manejo de excepciones |
| **Servicio** (`InventarioService`) | Reglas de negocio, validaciones, CRUD |
| **Modelo** (`Producto`) | Representación del dominio |
| **Excepciones** | Separación clara de errores de dominio |

## 🚀 Cómo ejecutar

```bash
# Compilar
javac -d bin src/**/*.java

# Ejecutar
java -cp bin Main
```

> Requiere **Java 8+**.

## 🔐 Validaciones incluidas

- Código y nombre obligatorios
- Precio debe ser mayor a cero
- Stock no puede ser negativo
- Cantidad a aumentar/disminuir debe ser mayor a cero
- No se permiten códigos duplicados
- No se puede disminuir stock por debajo de 0
- Protección contra entrada inválida en números (letras no rompen el programa)

## 🧠 Decisiones técnicas

- **Excepciones checked** (`extends Exception`) — fuerzan el manejo en la capa de presentación
- **Sin frameworks** — Java SE puro, sin Maven, Gradle ni librerías externas
- **Datos en memoria** — `ArrayList<Producto>` sin persistencia (ideal para empezar)
- **equalsIgnoreCase** — búsqueda flexible sin importar mayúsculas/minúsculas

## 📌 Pendiente / Próximos pasos

- [ ] Persistencia a archivo (CSV, JSON, o serialización)
- [ ] Tests unitarios (JUnit)
- [ ] Interfaz gráfica (Swing/JavaFX)
- [ ] Migración a Maven/Gradle
- [ ] Conexión a base de datos
