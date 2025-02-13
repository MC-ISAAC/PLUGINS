#!/bin/bash
if [ -z "$1" ]; then
    echo "❌ Erreur : Vous devez fournir un argument."
    echo "Usage: $0 <votre token>"
    exit 1  
fi

TokenApi="$1"

if [ ! -d "minecraft-data" ]; then
    mkdir -p minecraft-data/plugins/ISAAC
fi

echo TokenApi=$TokenApi >  minecraft-data/plugins/ISAAC/.env

rm minecraft-data/plugins/isaac-*.*.jar

cp target/isaac-*.*.jar minecraft-data/plugins/

