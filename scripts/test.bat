@echo off
if exist out rmdir /s /q out
if exist sources.txt del sources.txt
for /r src %%f in (*.java) do echo %%f >> sources.txt
javac -encoding UTF-8 -d out @sources.txt
java -cp out inventario.tests.AllTests
