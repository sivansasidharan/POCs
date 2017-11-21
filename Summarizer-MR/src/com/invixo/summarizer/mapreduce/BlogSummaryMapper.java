package com.invixo.summarizer.mapreduce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.knowledgebooks.nlp.KeyPhraseExtractionAndSummary;

public class BlogSummaryMapper extends Mapper<LongWritable, Text, Text, Text> {

	// hdfs directory path as the argument
	public void map(LongWritable key, Text value, Context context) {
		String in_path = value.toString();

		try {

			// reading from hdfs as string so that text_from_doc will have the
			// text in each file
			FileSystem fs = FileSystem.get(new Configuration());
			FileStatus[] status = fs.listStatus(new Path(in_path));
			for (int i = 0; i < status.length; i++) {
				BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(status[i].getPath())));

				// System.out.println("path--->"+fs.open(status[i].getPath()));

				String line = null;
				StringBuilder stringBuilder = new StringBuilder();
				String ls = System.getProperty("line.separator");

				while ((line = br.readLine()) != null) {
					stringBuilder.append(line);
					stringBuilder.append(ls);
				}

				String text_from_blog = stringBuilder.toString();

				// System.out.println("text from blog-->"+text_from_blog);

				// to get the urlNo

				String[] path = status[i].getPath().toString().split("/");

				int index = 0;
				for (int k = 0; k < path.length; k++) {
					Pattern tagmatcher = Pattern.compile(".txt");
					Matcher matcher = tagmatcher.matcher(path[k]);
					while (matcher.find()) {
						index = k;
						break;
					}
				}
				// System.out.println("index-->" + index);
				// System.out.println("str-->" + path[index]);

				StringTokenizer fileName = new StringTokenizer(path[index], ".");
				// tokens has the "urlNo" and txt for each file
				List<String> tokens = new ArrayList<String>();
				while (fileName.hasMoreElements()) {
					tokens.add(fileName.nextElement().toString());
				}
				String urlNo = tokens.get(0);
				// System.out.println("url"+urlNo);

				KeyPhraseExtractionAndSummary extraction = new KeyPhraseExtractionAndSummary(text_from_blog);

				String summary = extraction.getSummary();

				Text out_key = new Text(urlNo);

				Text out_value = new Text(summary);

				// System.out.println("key-->" + out_key + " " + "value--->"
				// + out_value);
				context.write(out_key, out_value);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("File not found");

		}
	}
}
