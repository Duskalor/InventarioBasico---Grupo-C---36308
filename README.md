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
# Compilar (Linux/macOS)
javac -d bin src/**/*.java

# Compilar (Windows - PowerShell)
javac -d bin (Get-ChildItem -Recurse src/*.java).FullName

# Ejecutar
java -cp bin Main
```

> Requiere **Java 8+**.

## 🔐 Validaciones incluidas

### Campos obligatorios
- Código y nombre no pueden estar vacíos ni ser solo espacios

### Código
- Solo permite letras, números, guiones (`-`) y guiones bajos (`_`)
- Longitud máxima: 20 caracteres
- No se permiten códigos duplicados (búsqueda case-insensitive)
- Búsqueda flexible con `equalsIgnoreCase`

### Nombre
- Longitud mínima: 2 caracteres
- Longitud máxima: 100 caracteres

### Precio
- Debe ser mayor que cero
- No puede exceder 999,999.99
- No puede tener más de 2 decimales

### Stock
- No puede ser negativo
- No puede exceder 999,999 unidades
- Control de overflow al aumentar (no permite superar el máximo)

### Cantidad (aumentar/disminuir)
- Debe ser mayor que cero
- No puede exceder 999,999

### Entrada de usuario (consola)
- Protección contra entrada inválida en números (letras no rompen el programa)
- Validación con reintento hasta ingresar un valor correcto

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
