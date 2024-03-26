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

        String wordCountJobInput = inputPath;
        String wordCountJobOutput = outputPath + "/WordCountResult";

        String resortJobInput = wordCountJobOutput;
        String resortJobOutput = outputPath + "/ResortJobResult";

        Job wordCountJob = Jobs.createWordCountJob(conf);
        Job resortJob = Jobs.createResortJob(conf);

        FileInputFormat.addInputPath(wordCountJob, new Path(wordCountJobInput));
        FileOutputFormat.setOutputPath(wordCountJob, new Path(wordCountJobOutput));

        FileInputFormat.addInputPath(resortJob, new Path(resortJobInput));
        FileOutputFormat.setOutputPath(resortJob, new Path(resortJobOutput));

        ControlledJob controlledWordCountJob = new ControlledJob(wordCountJob.getConfiguration());
        ControlledJob controlledResortJob = new ControlledJob(resortJob.getConfiguration());

        controlledResortJob.addDependingJob(controlledWordCountJob);

        JobControl jobControl = new JobControl("WikiJobs");
        jobControl.addJob(controlledWordCountJob);
        jobControl.addJob(controlledResortJob);

        Thread jobControlThread = new Thread(jobControl);
        jobControlThread.start();

        while (!jobControl.allFinished()) {
            Thread.sleep(1000);
        }

        jobControl.stop();
    }
}
