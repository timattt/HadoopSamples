package org.timattt;

import lombok.SneakyThrows;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.timattt.jobs.Jobs;

public class App {
    public static void main(String[] args) {
        runJobs(args[0], args[1]);
    }

    @SneakyThrows
    private static void runJobs(String inputPath, String outputPath) {
        Configuration conf = new Configuration();

        String threadDayJobInput = inputPath;
        String threadDayJobOutput = outputPath + "/ThreadDayResult";

        String onlyThreadJobInput = threadDayJobOutput;
        String onlyThreadJobOutput = outputPath + "/OnlyThreadResult";

        Job threadDayJob = Jobs.createThreadDayJob(conf);
        Job onlyThreadJob = Jobs.createOnlyThreadJob(conf);

        FileInputFormat.addInputPath(threadDayJob, new Path(threadDayJobInput));
        FileOutputFormat.setOutputPath(threadDayJob, new Path(threadDayJobOutput));

        FileInputFormat.addInputPath(onlyThreadJob, new Path(onlyThreadJobInput));
        FileOutputFormat.setOutputPath(onlyThreadJob, new Path(onlyThreadJobOutput));

        ControlledJob controlledThreadDayJob = new ControlledJob(threadDayJob.getConfiguration());
        ControlledJob controlledOnlyThreadJob = new ControlledJob(onlyThreadJob.getConfiguration());

        controlledOnlyThreadJob.addDependingJob(controlledThreadDayJob);

        JobControl jobControl = new JobControl("MinecraftJobs");
        jobControl.addJob(controlledThreadDayJob);
        jobControl.addJob(controlledOnlyThreadJob);

        Thread jobControlThread = new Thread(jobControl);
        jobControlThread.start();

        while (!jobControl.allFinished()) {
            Thread.sleep(1000);
        }

        jobControl.stop();
    }
}
