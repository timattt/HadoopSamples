#!/bin/bash

INPUT_PATH="/data/minecraft-server-logs"
OUTPUT_PATH="/user/hobod2024s052/output"
OUT_STREAM=/dev/stdout

cd MinecraftServerLogsMapReduceJavaScript
mvn clean package > $OUT_STREAM
yarn jar target/MinecraftServerLogsJob-0.0.1-SNAPSHOT-jar-with-dependencies.jar $INPUT_PATH $OUTPUT_PATH > $OUT_STREAM 2>&1
cd ..

let count=0
for f in "$OUTPUT_PATH"/*
do
    echo $(basename $f)
    let count=count+1
done