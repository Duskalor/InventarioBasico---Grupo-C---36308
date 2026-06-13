#!/bin/bash

JUNIT_URL="https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.11.4/junit-platform-console-standalone-1.11.4.jar"
JUNIT_JAR="lib/junit-platform-console-standalone-1.11.4.jar"

mkdir -p lib
if [ ! -f "$JUNIT_JAR" ]; then
    echo "Descargando JUnit 5..."
    curl -L -o "$JUNIT_JAR" "$JUNIT_URL"
fi

mkdir -p bin

echo "Compilando fuentes..."
javac -d bin -cp "$JUNIT_JAR" src/inventario/*.java

echo "Compilando tests..."
javac -d bin -cp "$JUNIT_JAR:bin" test/inventario/*.java

echo "Ejecutando tests..."
java -jar "$JUNIT_JAR" --class-path bin --scan-class-path
