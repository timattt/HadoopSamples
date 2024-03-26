#!/bin/bash

mvn clean package
hadoop jar target/WikiWordsCountMapReduceJavaScript-0.0.1-SNAPSHOT-jar-with-dependencies.jar -input "/data/wiki/en_articles_part" -output "/user/hobod2024s052/output" -Dmapred.reduce.tasks=1
hadoop fs -tail /user/hobod2024s052/output/part-r-00000
