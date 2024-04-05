package org.timattt.jobs.onlyThread;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.math.BigDecimal;

public class OnlyThreadReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {
    private final Text word = new Text();
    private final DoubleWritable doubleWritable = new DoubleWritable();

    public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
        BigDecimal result = new BigDecimal(0);
        int count = 0;
        for (DoubleWritable day : values) {
            result = result.add(BigDecimal.valueOf(day.get()));
            count++;
        }
        result = result.divide(BigDecimal.valueOf(count));
        doubleWritable.set(result.doubleValue());
        context.write(key, doubleWritable);
    }
}
