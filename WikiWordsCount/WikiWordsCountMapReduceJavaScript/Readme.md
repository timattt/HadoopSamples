# Как запускать

* Команда ```mvn clean package```
* Команда ```hadoop jar WikiWordsCountMapReduceJavaScript-0.0.1-SNAPSHOT-jar-with-dependencies.jar /data/wiki/en_articles_part /user/hobod2024s052/output```
* Посмотреть результат командой ```hadoop fs -cat /user/hobod2024s052/output/part-r-00000```

# Прочее полезное

* Посмотреть, с чем имеем дело командой ```hadoop fs -cat /data/wiki/en_articles/articles```
* Удалить папку с результатом ```hadoop fs -rm -r -f /user/hobod2024s052/output```