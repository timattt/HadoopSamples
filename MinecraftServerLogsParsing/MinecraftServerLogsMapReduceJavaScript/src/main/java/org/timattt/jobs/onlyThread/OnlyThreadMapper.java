package org.timattt.jobs.onlyThread;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class OnlyThreadMapper extends Mapper<Text, IntWritable, Text, IntWritable> {
    private final Text word = new Text();

    public void map(Text key, IntWritable value, Context context) throws IOException, InterruptedException {
        String thread = key.toString().split(":")[0];
        context.write(word, value);
    }
}