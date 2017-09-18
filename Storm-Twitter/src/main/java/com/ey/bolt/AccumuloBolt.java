package com.ey.bolt;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

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
	private  static String zooServers = "192.168.0.174:2181";
	private  static String table = "usertable3";
	private  static BatchWriter bw;
	private static Connector connector;
	private static int i=0;

	static{
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
		//createAccumuloTable();
		System.out.println("Table created succesfully....!");
	}

	public  void createAccumuloTable() {
		
			
			//connector.tableOperations().create(table);
			
			


	}

	public void prepare(@SuppressWarnings("rawtypes") Map stormConf,
			TopologyContext context, OutputCollector collector) {

	}

	public void execute(Tuple tuple) {
		String word = tuple.getString(0);

		System.out.println("To accumulo ===>" + word);
		i++;
		createEntry(word,i);
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

	public  void createEntry(String word, int i) {

		try {
			
			
			System.out.println("In create entry Word " + word);
			System.out.println("In create entry WordBytes" + word.getBytes());
			Text rowID = new Text("row1"+i);
			Text colFam = new Text("myColFam");
			Text colQual = new Text("myColQual");
			ColumnVisibility colVis = new ColumnVisibility("public");
			long timestamp = System.currentTimeMillis();

			Value value = new Value(word.getBytes());

			Mutation mutation = new Mutation(rowID);
			mutation.put(colFam, colQual, colVis, timestamp, value);
			
			bw.addMutation(mutation);
			bw.flush();
		   // bw.close();
			System.out.println("Persisted successfully...!!");

		} catch (AccumuloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Override
	public void  cleanup(){
		try {
			bw.close();
		} catch (MutationsRejectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
