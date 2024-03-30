#!/bin/bash

INPUT_PATH="/data/wiki/en_articles"
OUTPUT_PATH="/user/hobod2024s052/output"
OUT_STREAM=/dev/null

cd MinecraftServerLogsMapReduceJavaScript
mvn clean package > $OUT_STREAM
yarn jar target/MinecraftServerLogsJob-0.0.1-SNAPSHOT-jar-with-dependencies.jar $INPUT_PATH $OUTPUT_PATH > $OUT_STREAM 2>&1
cd ..

hadoop fs -rm -r -f $OUTPUT_PATH > $OUT_STREAM 2>&1