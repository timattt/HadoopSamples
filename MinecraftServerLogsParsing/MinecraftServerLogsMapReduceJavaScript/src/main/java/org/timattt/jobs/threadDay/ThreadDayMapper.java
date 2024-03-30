package org.timattt.jobs.threadDay;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringTokenizer;

public class ThreadDayMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private final static String[] useless = {",", ".", "\"", "'", ":", ";"};
    private final Text word = new Text();

    public static String parse(String line) {
        int begin = line.indexOf("[");
        int end = line.indexOf("]");

        String date = line.substring(begin, end + 1);
        line = line.replace(date, "");

        date = date.substring(1, date.length() - 1);
        begin = line.indexOf("[");
        end = line.indexOf("]");

        String threadInfo = line.substring(begin + 1, end);

        String[] divs = threadInfo.split("/");

        String threadName = divs[0];

        date = date.substring(0, date.indexOf("."));

        return threadName + ":" + date;
    }

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        try {
            String line = value.toString();
            String into = parse(line);
            word.set(into);
            context.write(word, one);
        } catch (Exception e) {
        }
    }
}