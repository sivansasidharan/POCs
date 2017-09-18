package com.ey.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import backtype.storm.Constants;
import backtype.storm.tuple.Tuple;

/*
 * Helper class that contains Tuple-related utility functions
 */
public class TupleHelper {

	public static boolean isTickTuple(Tuple tuple){
		return tuple.getSourceComponent().equals(Constants.SYSTEM_COMPONENT_ID)
				&& tuple.getSourceStreamId().equals(Constants.SYSTEM_TICK_STREAM_ID);
	}
	
	public   Properties loadPropertyFile(){
		Properties prop = new Properties();
		InputStream in = getClass().getResourceAsStream("twitter4j.properties");
		try {
			prop.load(in);
	
			
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		finally{
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		return prop;
		
	}
}
