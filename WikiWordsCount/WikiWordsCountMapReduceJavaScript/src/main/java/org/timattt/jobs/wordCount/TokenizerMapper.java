package org.timattt.jobs.wordCount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

public class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private final static IntWritable zero = new IntWritable(0);
    private final static String[] useless = {",", ".", "\"", "'", ":", ";"};
    private final Text word = new Text();

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        StringTokenizer itr = new StringTokenizer(value.toString());
        while (itr.hasMoreTokens()) {
            String token = itr.nextToken();

            for (String item : useless) {
                token = token.replace(item, "");
            }

            if (token.length() >= 6 && token.length() <= 9) {
                boolean skip = false;
                for (int i = 1; i < token.length(); i++) {
                    if (!Character.isLowerCase(token.charAt(i))) {
                        skip = true;
                    }
                }

                if (skip) {
                    continue;
                }

                word.set(token.toLowerCase());
                if (Character.isUpperCase(token.charAt(0))) {
                    context.write(word, one);
                }
                if (Character.isLowerCase(token.charAt(0))) {
                    context.write(word, zero);
                }
            }
        }
    }
}