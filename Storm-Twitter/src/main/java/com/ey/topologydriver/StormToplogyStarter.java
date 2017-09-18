package com.ey.topologydriver;

import java.io.File;



import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;


import com.ey.bolt.AccumuloBolt;
import com.ey.bolt.SplitTweet;
import com.ey.spout.TwitterSampleSpout;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.DistributedRPC.Client;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.TopologySummary;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.NimbusClient;

public class StormToplogyStarter {

	public static void main(String[] args) throws AlreadyAliveException,
			InvalidTopologyException {

		StormToplogyStarter starter = new StormToplogyStarter();
		System.out.println("in submitter");
		starter.jobSubmitter();

	}

	public void jobSubmitter() {
		// NimbusClient nimbus = new NimbusClient("10.165.162.62",6627);
		try {
			Config stormConf = new Config();
			stormConf.setDebug(false);
			Map<String, String> originalConf = new HashMap<String, String>();

			// String
			// filepath="E:/Ahalya/MyWorks/BigData/EclipseWorkspace/storm-word-count-master/storm-word-count-master/src/main/resources/storm.yaml";

			originalConf.put("storm.zookeeper.servers", "192.168.0.174");
			originalConf.put("storm.zookeeper.port", "2181");
			// originalConf.put("storm.zookeeper.port", "2181");
			originalConf.put("supervisor.slots.ports", "6700");
			originalConf.put("nimbus.host", "192.168.0.174");
			// originalConf.put("nimbus.host", "10.165.162.62");

			originalConf.put("ui.port", "8772");

			stormConf.putAll(originalConf); // merging!

			TopologyBuilder builder = new TopologyBuilder();

			// use TwitterSampleSpout
			String twitterSampleSpoutId = "sample_tweet";
			
			
			String keywordtoSearch="#expo2020|#dubaiexpo2020";
			int twitterSampleSpoutParallelism = 1;
			builder.setSpout(twitterSampleSpoutId, new TwitterSampleSpout(keywordtoSearch),
					twitterSampleSpoutParallelism);

			// use SplitTweet Bolt
			String splitTweetBoltId = "split_tweet";
			int splitTweetParallelism = 10;
			builder.setBolt(splitTweetBoltId, new SplitTweet(),
					splitTweetParallelism)
					.shuffleGrouping(twitterSampleSpoutId);

			// use CountWord Bolt
			// better code: use builder class to build specific bolt
			BaseRichBolt countWordBolt = null;
		//	String countWordBoltId = null;
		/*	int numArgument = 0;
			countWordBolt = new CountWord();
			countWordBoltId = "count_word";

			int countWordBoltParallelism = 10;
			builder.setBolt(countWordBoltId, countWordBolt,
					countWordBoltParallelism).fieldsGrouping(splitTweetBoltId,
					new Fields("word"));*/

			// use PrinterCount Bolt to print the result
			String printerBoltId = "accumulo_bolt";
			int printerBoltPar = 10;
			builder.setBolt(printerBoltId, new AccumuloBolt(), printerBoltPar)
					.fieldsGrouping(splitTweetBoltId, new Fields("word"));

			stormConf.setMaxTaskParallelism(3);

			String topologyName = "word_count_topology7";

			 System.setProperty( "storm.jar",
			 "C:\\Amal.Babu\\POC_01\\EclipseWorkspace\\storm-word-count-master\\target\\storm-word-count-0.0.1-SNAPSHOT-jar-with-dependencies.jar"
			 );
			
			 
			 System.setProperty("keyword", "expo2020");

			// List<TopologySummary> topologyList =
			// nimbus.getClusterInfo.get_topologies();

			// nimbus.getClient().submitTopologyWithOpts(name,
			// uploadedJarLocation, jsonConf, topology, options);
			 
			 //System.setProperty("params","#expo2020|#dubaiexpo2020");
			StormSubmitter.submitTopology(topologyName, stormConf,
					builder.createTopology());
		} catch (AlreadyAliveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidTopologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
