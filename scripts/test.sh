#!/usr/bin/env bash
set -euo pipefail
rm -rf out sources.txt
find src/main/java src/test/java -name "*.java" > sources.txt
javac -encoding UTF-8 -d out @sources.txt
java -cp out inventario.tests.AllTests
