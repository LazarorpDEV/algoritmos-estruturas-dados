#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
mkdir -p out
javac -encoding UTF-8 -d out src/main/java/br/com/lazaro/estruturas/*.java src/test/java/br/com/lazaro/estruturas/*.java
java -cp out br.com.lazaro.estruturas.TestesEstruturas
java -cp out br.com.lazaro.estruturas.Main
