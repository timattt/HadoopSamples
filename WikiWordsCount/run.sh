#!/bin/bash

INPUT_PATH="/data/wiki/en_articles"
OUTPUT_PATH="/user/hobod2024s052/output"
OUT_WORDS_COUNT=10
OUT_STREAM=/dev/null

cd WikiWordsCountMapReduceJavaScript
mvn clean package > $OUT_STREAM
yarn jar target/WikiWordsCountMapReduceJavaScript-0.0.1-SNAPSHOT-jar-with-dependencies.jar $INPUT_PATH $OUTPUT_PATH > $OUT_STREAM 2>&1
cd ..

cd WikiWordsCollector
mvn clean package > $OUT_STREAM
java -jar target/WikiWordsCollector-1.0-SNAPSHOT-jar-with-dependencies.jar $OUTPUT_PATH $OUT_WORDS_COUNT
cd ..

hadoop fs -rm -r -f $OUTPUT_PATH > $OUT_STREAM 2>&1