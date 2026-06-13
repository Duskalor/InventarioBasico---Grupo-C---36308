@echo off
setlocal

set JUNIT_URL=https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.11.4/junit-platform-console-standalone-1.11.4.jar
set JUNIT_JAR=lib\junit-platform-console-standalone-1.11.4.jar

if not exist lib mkdir lib
if not exist %JUNIT_JAR% (
    echo Descargando JUnit 5...
    powershell -Command "Invoke-WebRequest -Uri '%JUNIT_URL%' -OutFile '%JUNIT_JAR%'"
)

if not exist bin mkdir bin

echo Compilando fuentes...
javac -d bin -cp %JUNIT_JAR% src\inventario\*.java

echo Compilando tests...
javac -d bin -cp %JUNIT_JAR%;bin test\inventario\*.java

echo Ejecutando tests...
java -jar %JUNIT_JAR% --class-path bin --scan-class-path

endlocal
