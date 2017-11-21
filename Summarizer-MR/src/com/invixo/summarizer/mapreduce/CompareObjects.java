package com.invixo.summarizer.mapreduce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class CompareObjects {

	public static void main(String[] args) {

	}

	public void compareObjects(List<Table_05_blog_schema_json> blog_summary_list) throws IOException {
		ArrayList<Table_05_blog_schema_json> blog_summary_list_final = new ArrayList<Table_05_blog_schema_json>();

		ArrayList<ReferenceBlogFile> blogs = new ArrayList<ReferenceBlogFile>();
		Path path = new Path("hdfs://localhost.localdomain:8020/user/cloudera/Sivan/final_blog.csv");
		FileSystem fileSystem = FileSystem.get(new Configuration());
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileSystem.open(path)));

		List<Map<?, ?>> mappedCSV = CSVFileReader.readObjectsFromCsv(bufferedReader, ReferenceBlogFile.class);

		for (int i = 1; i < mappedCSV.size(); i++) {
			ReferenceBlogFile blog = new ReferenceBlogFile();

			blog.setUrl(mappedCSV.get(i).get("url").toString());
			blog.setUrlNo(mappedCSV.get(i).get("urlNo").toString());

			blog.setDocument_heading(mappedCSV.get(i).get("document_heading").toString());

			System.out.println("Url No--->" + mappedCSV.get(i).get("urlNo").toString());

			blogs.add(blog);

		}

		Iterator<ReferenceBlogFile> iteratorref = blogs.iterator();

		while (iteratorref.hasNext()) {
			ReferenceBlogFile referenceBlogFile = (ReferenceBlogFile) iteratorref.next();

			String urlNo = referenceBlogFile.getUrlNo();

			Iterator<Table_05_blog_schema_json> summaryiter = blog_summary_list.iterator();

			Table_05_blog_schema_json blogsummary = new Table_05_blog_schema_json();
			while (summaryiter.hasNext()) {
				blogsummary = (Table_05_blog_schema_json) summaryiter.next();

				if (blogsummary.getUrlNo().equalsIgnoreCase(urlNo)) {

					blogsummary.setUrl(referenceBlogFile.getUrl());
					break;
				}

				blog_summary_list_final.add(blogsummary);

			}

		}

	}
}
