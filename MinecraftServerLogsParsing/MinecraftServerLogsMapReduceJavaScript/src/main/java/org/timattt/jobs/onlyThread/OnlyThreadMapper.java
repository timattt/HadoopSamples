package org.timattt.jobs.onlyThread;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class OnlyThreadMapper extends Mapper<Text, IntWritable, Text, IntWritable> {
    private final Text word = new Text();
    private final IntWritable intWritable = new IntWritable();

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String thread = key.toString().split(":")[0];
        int count = Integer.parseInt(key.toString().split(":")[1]);

        word.set(thread);
        intWritable.set(count);
        context.write(word, intWritable);
    }
}