#!/bin/bash

mvn clean package
hadoop jar target/WikiWordsCountMapReduceJavaScript-0.0.1-SNAPSHOT-jar-with-dependencies.jar /data/wiki/en_articles_part /user/hobod2024s052/output
hadoop fs -tail /user/hobod2024s052/output/part-r-00000