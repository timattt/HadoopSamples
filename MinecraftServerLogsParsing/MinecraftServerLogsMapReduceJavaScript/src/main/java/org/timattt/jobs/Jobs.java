package org.timattt.jobs;

import lombok.SneakyThrows;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.timattt.App;
import org.timattt.jobs.onlyThread.OnlyThreadMapper;
import org.timattt.jobs.onlyThread.OnlyThreadReducer;
import org.timattt.jobs.threadDay.ThreadDayReducer;
import org.timattt.jobs.threadDay.ThreadDayMapper;

public class Jobs {

    @SneakyThrows
    public static Job createThreadDayJob(Configuration conf) {
        Job job = Job.getInstance(conf, "thread-day");
        job.setJarByClass(App.class);

        job.setMapperClass(ThreadDayMapper.class);
        job.setCombinerClass(ThreadDayReducer.class);
        job.setReducerClass(ThreadDayReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        return job;
    }

    @SneakyThrows
    public static Job createOnlyThreadJob(Configuration conf) {
        Job job = Job.getInstance(conf, "thread-only");
        job.setJarByClass(App.class);

        job.setMapperClass(OnlyThreadMapper.class);
        job.setCombinerClass(OnlyThreadReducer.class);
        job.setReducerClass(OnlyThreadReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        return job;
    }

}
