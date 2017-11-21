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
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class BlogSummaryReducer extends Reducer<Text, Text, Text, Text> {
	private Logger log = Logger.getLogger(BlogSummaryReducer.class);

	private List<Table_05_blog_schema_json> blog_summary_list = new ArrayList<Table_05_blog_schema_json>();

	private final List<Table_05_blog_schema_json> blog_summary_list_final = new ArrayList<Table_05_blog_schema_json>();

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException {
		try {

			Table_05_blog_schema_json blog_summary = new Table_05_blog_schema_json();

			String keyin = key.toString();
			System.out.println("keyin-->" + keyin);

			/*
			 * Iterator<Text> iter=values.iterator(); while (iter.hasNext()) {
			 * Text text = (Text) iter.next();
			 * 
			 * }
			 */
			// System.out.println(iter.next());

			for (Text val : values) {

				blog_summary.setSummary(val.toString());

				blog_summary.setUrlNo(key.toString());

				blog_summary_list.add(blog_summary);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {

		try {

			ArrayList<ReferenceBlogFile> blogs = new ArrayList<ReferenceBlogFile>();
			Path path = new Path(ApplicationConstants.blog_referenceFilePath);
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
			ObjectMapper mapper = new ObjectMapper();

			ListAttributeBean attributeBean = new ListAttributeBean();

			attributeBean.setBlog_schema_jsons(blog_summary_list_final);

			String result = mapper.writeValueAsString(attributeBean);

			Configuration conf = context.getConfiguration();

			String searchkey = conf.get("searchkey");

			String table_05_blog_summary = searchkey + "_" + ApplicationConstants.table_05_blog_summary_graphType;
			MongoConnector mongoConnector = new MongoConnector();

			// to persist in mongo
			mongoConnector.persistMongo(table_05_blog_summary, result, true);

			Text resultvalue = new Text(result);
			context.write(new Text("out"), resultvalue);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}