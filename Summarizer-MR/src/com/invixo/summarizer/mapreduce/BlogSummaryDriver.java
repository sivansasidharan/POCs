package com.invixo.summarizer.mapreduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

public class BlogSummaryDriver extends Configured implements Tool {
	private static Logger log = Logger.getLogger(BlogSummaryDriver.class);

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("Inside main");
		int res = ToolRunner.run(new Configuration(), new BlogSummaryDriver(), args);
		System.exit(res);

	}

	@Override
	public int run(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Inside Run");
		Configuration conf = new Configuration();
		Job job = null;
		try {
			job = new Job(conf, "sumdriver");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("IOException caught when initializing Job Object");
			log.error("IOException caught when initializing Job Object");
			System.err.println(e1);
			e1.printStackTrace();
		}
		job.setJarByClass(BlogSummaryDriver.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		job.setMapperClass(BlogSummaryMapper.class);
		job.setReducerClass(BlogSummaryReducer.class);

		// job.setCombinerClass(wordCountReducer.class);
		// job.setInputFormatClass(TextInputFormat.class);

		try {
			FileInputFormat.addInputPath(job, new Path(args[0]));
			FileOutputFormat.setOutputPath(job, new Path(args[1]));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("IOException caught in main program");
			e.printStackTrace();
		}

		return job.waitForCompletion(true) ? 0 : 1;

	}

}