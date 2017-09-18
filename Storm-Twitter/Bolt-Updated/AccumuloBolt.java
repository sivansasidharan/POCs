package com.ey.bolt;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.accumulo.core.client.AccumuloException;
import org.apache.accumulo.core.client.AccumuloSecurityException;
import org.apache.accumulo.core.client.BatchWriter;
import org.apache.accumulo.core.client.Connector;
import org.apache.accumulo.core.client.Instance;
import org.apache.accumulo.core.client.MutationsRejectedException;
import org.apache.accumulo.core.client.TableExistsException;
import org.apache.accumulo.core.client.TableNotFoundException;
import org.apache.accumulo.core.client.ZooKeeperInstance;
import org.apache.accumulo.core.data.Mutation;
import org.apache.accumulo.core.data.Value;
import org.apache.accumulo.core.security.ColumnVisibility;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.hadoop.io.Text;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

public class AccumuloBolt extends BaseRichBolt {

	/**
	 * Bolt to print the current count to the console
	 */
	private static final long serialVersionUID = -6133323709164892042L;

	private File dataFolder;

	private static String instanceName = "accumulo";
	private static String zooServers = "192.168.0.174:2181";
	private static String table = "usertable3";
	private static BatchWriter bw;
	private static Connector connector;
	private static int i = 0;

	private String schemaDescription = " {    \n"
			+ " \"name\": \"tweets\", \n" + " \"type\": \"record\",\n"
			+ " \"fields\": [\n"
			+ "   {\"name\": \"created_at\", \"type\": \"string\"},\n"
			+ "   {\"name\": \"userId\", \"type\": \"string\"},\n"
			+ "   {\"name\": \"username\", \"type\": \"string\"},\n"
			+ "   {\"name\": \"statusId\", \"type\": \"string\"},\n"
			+ "   {\"name\": \"languageCode\", \"type\": \"string\"},\n"
			+ "   {\"name\": \"statusStr\", \"type\": \"string\"},\n"
			+ "   {\"name\": \"urlsStr\", \"type\": \"string\"},\n"
			+ "	  {\"name\": \"userLocation\", \"type\": \"string\"},\n"
			+ "   {\"name\": \"retweetCount\", \"type\": \"string\"}]\n" + "}";
	
	

	static {
		try {
			connector = new ZooKeeperInstance(instanceName, zooServers)
					.getConnector("eyuser", "eyuser".getBytes());
			bw = connector.createBatchWriter(table, 100000l, 30l, 1);

		} catch (AccumuloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AccumuloSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TableNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public AccumuloBolt() {
		System.out.println("Going to create Accumulo table...!");
		// createAccumuloTable();
		System.out.println("Table created succesfully....!");
	}

	public void createAccumuloTable() {

		// connector.tableOperations().create(table);

	}

	public void prepare(@SuppressWarnings("rawtypes") Map stormConf,
			TopologyContext context, OutputCollector collector) {

	}

	public void execute(Tuple tuple) {
		String status = tuple.getString(0);

		System.out.println("To accumulo ===>" + status);
		i++;

		createEntry(status, i);
		// createEntry(word);

	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {

	}

	private File getCurrentHourFile() {

		FastDateFormat fastDateFormat = FastDateFormat
				.getInstance("yyyy_MM_dd_HH");
		FastDateFormat fastDateFormatMinute = FastDateFormat
				.getInstance("yyyy_MM_dd_HH_mm");

		return new File(dataFolder, "feed_" + "_"
				+ fastDateFormat.format(System.currentTimeMillis()) + ".txt");
	}

	private File getCurrentMinuteFile() {
		FastDateFormat fastDateFormatMinute = FastDateFormat
				.getInstance("yyyy_MM_dd_HH");
		return new File(dataFolder, "feed_"
				+ fastDateFormatMinute.format(System.currentTimeMillis())
				+ ".txt");
	}

	public void createEntry(String status, int i) {

		try {

			Schema s = Schema.parse(schemaDescription);
			GenericDatumWriter w = new GenericDatumWriter(s);

			// Populate data
			GenericRecord generic = new GenericData.Record(s);

			StringTokenizer st2 = new StringTokenizer(status, "|");
			 List<String> words=new LinkedList<String>();
		//	 int i=0;
			while (st2.hasMoreElements()) {
				words.add(st2.nextElement().toString());
				//System.out.println(st2.nextElement());
			}
			
			

			
			generic.put("created_at", new org.apache.avro.util.Utf8(words.get(0)));
			System.out.println("userId"+words.get(1));
			generic.put("userId", new org.apache.avro.util.Utf8(words.get(1)));
			generic.put("username", new org.apache.avro.util.Utf8(words.get(2)));
			generic.put("statusId", new org.apache.avro.util.Utf8(words.get(3)));
			generic.put("languageCode", new org.apache.avro.util.Utf8(words.get(4)));
			generic.put("statusStr", new org.apache.avro.util.Utf8(words.get(5)));
			generic.put("urlsStr", new org.apache.avro.util.Utf8(words.get(6)));
			generic.put("userLocation", new org.apache.avro.util.Utf8(words.get(7)));
			generic.put("retweetCount", new org.apache.avro.util.Utf8(words.get(8)));

			System.out.println("In create entry Word " + status);
			System.out.println("In create entry WordBytes"
					+ generic.toString().getBytes());
			Text rowID = new Text("row1" + i);
			Text colFam = new Text("myColFam");
			Text colQual = new Text("myColQual");
			ColumnVisibility colVis = new ColumnVisibility("public");
			long timestamp = System.currentTimeMillis();

			Value value = new Value(generic.toString().getBytes());

			Mutation mutation = new Mutation(rowID);
			mutation.put(colFam, colQual, colVis, timestamp, value);

			bw.addMutation(mutation);
			bw.flush();
			// jus for checking need to remove
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			Encoder e = EncoderFactory.get().binaryEncoder(outputStream, null);

			w.write(generic, e);

			e.flush();

			byte[] encodedByteArray = outputStream.toByteArray();
			String encodedString = outputStream.toString();

			System.out.println("encodedString: " + encodedString);
			// bw.close();
			System.out.println("Persisted successfully...!!");

		} catch (AccumuloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void cleanup() {
		try {
			bw.close();
		} catch (MutationsRejectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
