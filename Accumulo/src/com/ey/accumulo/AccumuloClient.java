package com.ey.accumulo;

import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.Map;

import org.apache.accumulo.core.Constants;
import org.apache.accumulo.core.client.AccumuloException;
import org.apache.accumulo.core.client.AccumuloSecurityException;
import org.apache.accumulo.core.client.BatchWriter;
import org.apache.accumulo.core.client.Connector;
import org.apache.accumulo.core.client.Instance;
import org.apache.accumulo.core.client.Scanner;
import org.apache.accumulo.core.client.TableNotFoundException;
import org.apache.accumulo.core.client.ZooKeeperInstance;
import org.apache.accumulo.core.data.Key;
import org.apache.accumulo.core.data.Mutation;
import org.apache.accumulo.core.data.Range;
import org.apache.accumulo.core.data.Value;
import org.apache.accumulo.core.security.Authorizations;
import org.apache.accumulo.core.security.ColumnVisibility;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.hadoop.io.Text;

public class AccumuloClient {
	static String instanceName = "accumulo";
	static String zooServers = "10.165.162.62:2181";
	static Instance inst = new ZooKeeperInstance(instanceName, zooServers);
	static Connector conn;

	public static void createEntry() {

		try {
			conn = inst.getConnector("eyuser", "eyuser".getBytes());
			System.out.println("Got connection...!!!");
			Text rowID = new Text("row1");
			Text colFam = new Text("myColFam");
			Text colQual = new Text("myColQual");
			ColumnVisibility colVis = new ColumnVisibility("public");
			long timestamp = System.currentTimeMillis();

			Value value = new Value("myValue".getBytes());

			Mutation mutation = new Mutation(rowID);
			mutation.put(colFam, colQual, colVis, timestamp, value);
			long memBuf = 1000000L; // bytes to store before sending a batch
			long timeout = 1000L; // milliseconds to wait before sending
			int numThreads = 10;

			BatchWriter writer = conn.createBatchWriter("usertable2", memBuf,
					timeout, numThreads);

			writer.addMutation(mutation);

			writer.close();
			System.out.println("Persisted successfully...!!");

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

	public static void readEntry() {
		Scanner scan;
		try {
			scan = conn.createScanner("usertable3",
					new Authorizations("public"));
			// scan.setRange(new Range("rowId", "rowId"));
			Iterator<Map.Entry<Key, Value>> iterator = scan.iterator();
			while (iterator.hasNext()) {
				Map.Entry<Key, Value> entry = iterator.next();
				Key key = entry.getKey();
				Value value = entry.getValue();
				System.out.println(key + " ==> " + value);
			}
		} catch (TableNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void createEntryWithAvro() {

		String schemaDescription = " {    \n"
				+ " \"name\": \"FacebookUser\", \n"
				+ " \"type\": \"record\",\n" + " \"fields\": [\n"
				+ "   {\"name\": \"name\", \"type\": \"string\"},\n"
				+ "   {\"name\": \"num_likes\", \"type\": \"int\"},\n"
				+ "   {\"name\": \"num_photos\", \"type\": \"int\"},\n"
				+ "   {\"name\": \"num_groups\", \"type\": \"int\"} ]\n" + "}";

		Schema s = Schema.parse(schemaDescription);
		GenericDatumWriter w = new GenericDatumWriter(s);

		// Populate data
		GenericRecord generic = new GenericData.Record(s);
		generic.put("name", new org.apache.avro.util.Utf8("Doctor Who"));
		generic.put("num_likes", 1);
		generic.put("num_groups", 423);
		generic.put("num_photos", 0);

		try {
			conn = inst.getConnector("eyuser", "eyuser".getBytes());
			System.out.println("Got connection...!!!");
			Text rowID = new Text("row1");
			Text colFam = new Text("myColFam");
			Text colQual = new Text("myColQual");
			ColumnVisibility colVis = new ColumnVisibility("public");
			long timestamp = System.currentTimeMillis();

			Value value = new Value(generic.toString().getBytes());

			Mutation mutation = new Mutation(rowID);
			mutation.put(colFam, colQual, colVis, timestamp, value);
			long memBuf = 1000000L; // bytes to store before sending a batch
			long timeout = 1000L; // milliseconds to wait before sending
			int numThreads = 10;

			BatchWriter writer = conn.createBatchWriter("usertable3", memBuf,
					timeout, numThreads);

			writer.addMutation(mutation);

			writer.close();
			System.out.println("Persisted successfully...!!");

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

	public static void main(String[] args) {
		createEntryWithAvro();
		readEntry();
	}

}
