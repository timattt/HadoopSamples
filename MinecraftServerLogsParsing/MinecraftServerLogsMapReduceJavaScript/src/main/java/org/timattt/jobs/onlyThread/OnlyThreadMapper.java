package org.timattt.jobs.onlyThread;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class OnlyThreadMapper extends Mapper<Object, Text, Text, DoubleWritable> {
    private final Text word = new Text();
    private final DoubleWritable intWritable = new DoubleWritable();

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        System.out.println("INPUT ----------------> [" + key + "], [" + value.toString() + "]");
        String thread = value.toString().split(":")[0];
        int count = Integer.parseInt(key.toString());

        word.set(thread);
        intWritable.set(count);
        context.write(word, intWritable);
    }
}