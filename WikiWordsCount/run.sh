#!/bin/bash

INPUT_PATH="/data/wiki/en_articles"
OUTPUT_PATH="/user/hobod2024s052/output"
OUT_WORDS_COUNT=10

cd WikiWordsCountMapReduceJavaScript
mvn clean package > /dev/null
yarn jar target/WikiWordsCountMapReduceJavaScript-0.0.1-SNAPSHOT-jar-with-dependencies.jar $INPUT_PATH $OUTPUT_PATH > /dev/null 2>&1
cd ..

cd WikiWordsCollector
mvn clean package > /dev/null
java -jar target/WikiWordsCollector-1.0-SNAPSHOT-jar-with-dependencies.jar $OUTPUT_PATH $OUT_WORDS_COUNT
cd ..

hadoop fs -rm -r -f $OUTPUT_PATH > /dev/null 2>&1