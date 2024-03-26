#!/bin/bash

cd WikiWordsCountMapReduceJavaScript
mvn clean package
hadoop jar target/WikiWordsCountMapReduceJavaScript-0.0.1-SNAPSHOT-jar-with-dependencies.jar "/data/wiki/en_articles_part" "/user/hobod2024s052/output" -Dmapred.reduce.tasks=1
cd ..

cd WikiWordsCollector
mvn clean package
java -jar target/WikiWordsCollector-1.0-SNAPSHOT-jar-with-dependencies.jar > ../result.txt
cd ..

hadoop fs -rm -r -f /user/hobod2024s052/output