
package com.invixo.summarizer.mapreduce;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class CSV_HDFSTest {
	public static void main(String[] args) throws IOException {

		Path path = new Path("hdfs://localhost.localdomain:8020/user/cloudera/Sivan/final_blog.csv");
		FileSystem fileSystem = FileSystem.get(new Configuration());
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileSystem.open(path)));

		List<Map<?, ?>> obj = CSVFileReader.readObjectsFromCsv(bufferedReader, ReferenceBlogFile.class);

		System.out.println(obj.size());

	}

}
