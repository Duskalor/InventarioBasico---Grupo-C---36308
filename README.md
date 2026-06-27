# v3.0 - Pruebas unitarias y TDD

Esta version agrega pruebas unitarias al Sistema de Control de Inventarios. El codigo esta separado en capas: `model`, `repository`, `service` y `tests`.

## Objetivo academico
- Validar CRUD de productos.
- Validar entradas y salidas de inventario.
- Validar reportes, alertas y dashboard.
- Evidenciar el ciclo TDD Red-Green-Refactor mediante pruebas automatizadas.

## Ejecutar pruebas

Linux/macOS/Git Bash:

```bash
bash scripts/test.sh
```

Windows CMD:

```bat
scripts\test.bat
```

## Ejecutar sistema

```bash
bash scripts/run.sh
```

## Commit sugerido

```bash
git add v3.0-pruebas-unitarias-tdd
git commit -m "test: agregar pruebas unitarias y ciclo TDD inicial"
git tag v3.0-pruebas-unitarias-tdd
```
