package com.invixo.summarizer.mapreduce;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class Demo {

	public static void main(String args[]) throws Exception {

		ArrayList<ReferenceBlogFile> blogs = new ArrayList<ReferenceBlogFile>();

		ArrayList<Table_05_blog_schema_json> blog_summary_list = new ArrayList<Table_05_blog_schema_json>();

		ArrayList<Table_05_blog_schema_json> blog_summary_list_final = new ArrayList<Table_05_blog_schema_json>();

		Table_05_blog_schema_json blog_summary1 = new Table_05_blog_schema_json();
		Table_05_blog_schema_json blog_summary2 = new Table_05_blog_schema_json();
		Table_05_blog_schema_json blog_summary3 = new Table_05_blog_schema_json();

		blog_summary1.setSummary("hxxnuh");
		blog_summary1.setUrl("hu");
		blog_summary1.setUrlNo("url1");
		blog_summary2.setSummary("hxxnuh");
		blog_summary2.setUrl("hu");
		blog_summary2.setUrlNo("url2");
		blog_summary3.setSummary("hxxnuh");
		blog_summary3.setUrl("hu");
		blog_summary3.setUrlNo("url3");
		blog_summary_list.add(blog_summary1);
		blog_summary_list.add(blog_summary3);
		blog_summary_list.add(blog_summary2);

		Path path = new Path("hdfs://localhost.localdomain:8020/user/cloudera/Sivan/final_blog.csv");
		FileSystem fileSystem = FileSystem.get(new Configuration());
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileSystem.open(path)));
		List<Map<?, ?>> mappedCSV = CSVFileReader.readObjectsFromCsv(bufferedReader, ReferenceBlogFile.class);

		for (int i = 1; i < mappedCSV.size(); i++) {
			ReferenceBlogFile blog = new ReferenceBlogFile();

			// blog.setDocNo(mappedCSV.get(i).get("urlNo").toString());
			blog.setUrl(mappedCSV.get(i).get("url").toString());
			blog.setUrlNo(mappedCSV.get(i).get("docNo").toString());

			blogs.add(blog);

		}

		// System.out.println("size of the reference blog-->"+blogs.size());
		Iterator<ReferenceBlogFile> iteratorref = blogs.iterator();
		Table_05_blog_schema_json blogsummary = new Table_05_blog_schema_json();

		while (iteratorref.hasNext()) {

			// System.out.println("inside first iterator");
			ReferenceBlogFile referenceBlogFile = (ReferenceBlogFile) iteratorref.next();

			String urlNo = referenceBlogFile.getUrlNo();

			Iterator<Table_05_blog_schema_json> summaryiter = blog_summary_list.iterator();

			// System.out.println("blog summary
			// size--->"+blog_summary_list.size());

			while (summaryiter.hasNext()) {
				blogsummary = (Table_05_blog_schema_json) summaryiter.next();
				// System.out.println("blog summary url
				// No--->"+blogsummary.getUrlNo());

				// System.out.println("reference url No-->"+urlNo);

				if (blogsummary.getUrlNo().equalsIgnoreCase(urlNo)) {

					// System.out.println("inside if");

					// System.out.println(blogsummary.getUrlNo());

					blogsummary.setUrl(referenceBlogFile.getUrl());
					break;
				}

				blog_summary_list_final.add(blogsummary);

			}

		}
		// Entry<?, ?> entry=(Entry<?, ?>) map.entrySet();

		System.out.println(blog_summary_list_final.size());

		Iterator<Table_05_blog_schema_json> iterator = blog_summary_list_final.iterator();

		System.out.println(blog_summary_list_final.size());

		while (iterator.hasNext()) {
			Table_05_blog_schema_json table_05_blog_schema_json = (Table_05_blog_schema_json) iterator.next();

			System.out.println(table_05_blog_schema_json.getSummary());
			System.out.println(table_05_blog_schema_json.getUrl());
			System.out.println(table_05_blog_schema_json.getUrlNo());
			System.out.println("**********************************************");

		}

	}

}
