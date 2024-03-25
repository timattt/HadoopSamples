package org.timattt.jobs.resorter;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class ResortMapper extends Mapper<Object, Text, IntWritable, Text> {
    private final IntWritable number = new IntWritable();
    private final Text word = new Text();

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] divs = value.toString().split(" ");

        String wordString = divs[0];
        int count = Integer.parseInt(divs[1]);

        number.set(count);
        word.set(wordString);

        context.write(number, word);
    }
}