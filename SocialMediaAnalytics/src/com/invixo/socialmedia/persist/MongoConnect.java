package com.invixo.socialmedia.persist;

import java.net.UnknownHostException;

import com.google.gson.Gson;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

public class MongoConnect {

	public void persistMongo(String collectionName, Object table_01) {
		Gson gson = new Gson();
		// System.out.println(gson.toJson(table_01));

		try {
			Mongo mongo = new Mongo("10.165.162.107", 27017);
			DB db = mongo.getDB("SocialMediaInsights_Visualizations");
			// convert JSON to DBObject directly
			DBObject dbObject = (DBObject) JSON.parse(gson.toJson(table_01));
			DBCollection collection = db.getCollection(collectionName);
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
		}

	}
}
