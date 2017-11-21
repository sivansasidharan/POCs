package com.invixo.summarizer.mapreduce;

import java.net.UnknownHostException;

import com.google.gson.Gson;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

public class MongoConnector {

	public void persistMongo(String collectionName, String jsonString, Boolean toDrop) {
		Gson gson = new Gson();
		// System.out.println(gson.toJson(table_01));

		try {

			Mongo mongo = new Mongo(ApplicationConstants.hostname, 27017);
			DB db = mongo.getDB(ApplicationConstants.dbName);
			DBCollection collection = db.getCollection(collectionName);
			if (toDrop) {
				collection.drop();
				collection = db.getCollection(collectionName);
			}

			// convert JSON to DBObject directly
			// DBObject dbObject = (DBObject) JSON.parse(gson.toJson(table_01));
			DBObject dbObject = (DBObject) JSON.parse(jsonString);
			// DBCollection collection_updated =
			// db.getCollection(collectionName);
			collection.insert(dbObject);
			DBCursor cursorDoc = collection.find();
			while (cursorDoc.hasNext()) {
				System.out.println(cursorDoc.next());
			}
			System.out.println("---------------Document persisted succesfully-----------------");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
