# Evidencia de Katas TDD - v3.1

## Kata 1: Clasificacion de stock
- Red: prueba para casos SIN_STOCK, STOCK_BAJO y OK.
- Green: condicionales minimos en `StockKata.clasificarStock`.
- Refactor: se agrego `esStockBajo` para que `ReporteService` no repita reglas.

## Kata 2: Reposicion sugerida
- Red: prueba para calcular cantidad hasta el doble del stock minimo.
- Green: calculo `stockMinimo * 2 - stock`.
- Refactor: se aplico `Math.max(0, ...)` para evitar sugerencias negativas.

## Kata 3: Generacion SKU
- Red: prueba de formato `CAT-NOM-0001`.
- Green: generacion con prefijos y padding numerico.
- Refactor: se limpio el texto de caracteres especiales.
