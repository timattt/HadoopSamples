package org.timattt.jobs;

import lombok.SneakyThrows;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.timattt.App;
import org.timattt.jobs.resorter.ResortMapper;
import org.timattt.jobs.resorter.ResortReducer;
import org.timattt.jobs.wordCount.IntSumReducer;
import org.timattt.jobs.wordCount.TokenizerMapper;

public class Jobs {

    @SneakyThrows
    public static Job createWordCountJob(Configuration conf) {
        Job job = Job.getInstance(conf, "word-count");
        job.setJarByClass(App.class);

        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        return job;
    }

    @SneakyThrows
    public static Job createResortJob(Configuration conf) {
        Job job = Job.getInstance(conf, "resort");
        job.setJarByClass(App.class);

        job.setMapperClass(ResortMapper.class);
        job.setCombinerClass(ResortReducer.class);
        job.setReducerClass(ResortReducer.class);

        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);
        return job;
    }

}
