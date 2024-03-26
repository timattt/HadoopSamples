#!/bin/bash

cd WikiWordsCountMapReduceJavaScript
mvn clean package > /dev/null
hadoop jar target/WikiWordsCountMapReduceJavaScript-0.0.1-SNAPSHOT-jar-with-dependencies.jar "/data/wiki/en_articles" "/user/hobod2024s052/output" -Dmapred.reduce.tasks=1 > /dev/null
cd ..

cd WikiWordsCollector
mvn clean package > /dev/null
java -jar target/WikiWordsCollector-1.0-SNAPSHOT-jar-with-dependencies.jar
cd ..

hadoop fs -rm -r -f /user/hobod2024s052/output > /dev/null