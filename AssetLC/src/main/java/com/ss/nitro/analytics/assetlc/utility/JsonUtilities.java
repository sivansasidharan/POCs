package com.ss.nitro.analytics.assetlc.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ss.nitro.analytics.assetlc.domain.Nodes;
import com.ss.nitro.analytics.assetlc.domain.Sankey;
import com.ss.nitro.analytics.assetlc.domain.SankeyJson;
import com.ss.nitro.analytics.assetlc.domain.expired.ExNode;
import com.ss.nitro.analytics.assetlc.domain.expired.ExSankeyModJson;
import com.ss.nitro.analytics.assetlc.domain.expired.ExSource;
import com.ss.nitro.analytics.assetlc.domain.sankey.SankeyModJson;
import com.ss.nitro.analytics.assetlc.domain.sankey.Source;

public final class JsonUtilities {

	public static String formatJsonForAssetInfo(String pJsonStringToFormat)
			throws JSONException {
		JSONObject finalJson = new JSONObject();
		Set<String> serviceLines = new HashSet<String>();
		serviceLines.add("Advisory");
		serviceLines.add("Assurance");
		serviceLines.add("Core Business Services");
		serviceLines.add("TAS");
		serviceLines.add("TAX");
		String stringfromInput = pJsonStringToFormat;
		// input to be converted to string and do the below
		JSONObject j = new JSONObject(stringfromInput);
		JSONArray arr = j.getJSONArray("AssetInfo");
		JSONArray result = new JSONArray();
		for (String ser : serviceLines) {
			JSONObject res = new JSONObject();
			Set<Integer> assetValues = new HashSet<Integer>();
			for (int i = 0; i < arr.length(); i++) {

				JSONObject obj = (JSONObject) arr.get(i);

				if (ser.equalsIgnoreCase(obj.getString("serviceLine"))) {
					assetValues.add(obj.getInt("totalAssetValue"));

					res.put("serviceline", ser);
					res.put(obj.getString("subServiceLine"),
							obj.getInt("totalAssetValue"));
					// System.out.println(obj.getInt("totalAssetValue"));

					// toadd=toadd+obj.getInt("totalAssetValue");

				}

			}
			// System.out.println(sum);
			int add = 0;
			for (int i : assetValues) {
				add = add + i;
			}
			System.out.println(add);
			res.put("totalAssetValue", add);
			result.put(res);
			// finalJson.put("Year",j.getJSONArray("Year"));
			finalJson.put("AssetInfo", result);

		}
		System.out.println("result---->" + finalJson);
		return finalJson.toString();
	}

	private static List<Nodes> makeList(List<String> list1) {
		int size = list1.size();
		List<Nodes> node = new ArrayList<Nodes>();
		// Nodes name = new Nodes();
		for (int i = 0; i < size; i++) {
			Nodes name = new Nodes();
			System.out.println("Name " + list1.get(i));
			name.setName(list1.get(i));
			System.out.println("Name " + name.getName());
			node.add(name);
			// nodes.get(i)
		}
		return node;
	}

	public static String makeSankeyJson(List<Sankey> links, List<String> node) {

		/*
		 * List<Sankey> links = allSankey(entity, sl, ssl, region, country,
		 * city, location, year, target, service); List<String> node =
		 * allNodes(entity, sl, ssl, region, country, city, location, year,
		 * service);
		 */

		List<Nodes> nodes = makeList(node);
		SankeyJson fromClass = new SankeyJson();
		fromClass.setLinks(links);
		fromClass.setNodes(nodes);
		String jsonString = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonString = mapper.writeValueAsString(fromClass);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

	public static String makeNewSankeyJson(List<Source> sourceValue,
			List<String> node) {
		List<Nodes> nodes = makeList(node);
		SankeyModJson fromClass = new SankeyModJson();
		fromClass.setLinks(sourceValue);
		fromClass.setNodes(nodes);
		String jsonString = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonString = mapper.writeValueAsString(fromClass);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("- " + jsonString);
		return jsonString;
	}

	public static String makeExpiredSankeyJson(List<ExSource> sourceValue,
			List<String> node) {
		List<ExNode> nodes = makeExList(node);
		ExSankeyModJson fromClass = new ExSankeyModJson();
		fromClass.setLinks(sourceValue);
		fromClass.setNodes(nodes);
		String jsonString = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonString = mapper.writeValueAsString(fromClass);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("- " + jsonString);
		return jsonString;
	}

	private static List<ExNode> makeExList(List<String> list1) {
		int size = list1.size();
		List<ExNode> node = new ArrayList<ExNode>();
		// Nodes name = new Nodes();
		for (int i = 0; i < size; i++) {
			ExNode name = new ExNode();
			System.out.println("Name " + list1.get(i));
			name.setName(list1.get(i));
			System.out.println("Name " + name.getName());
			node.add(name);
			// nodes.get(i)
		}
		return node;
	}

}
