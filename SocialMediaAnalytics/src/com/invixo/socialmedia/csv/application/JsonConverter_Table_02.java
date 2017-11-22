package com.invixo.socialmedia.csv.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.invixo.socialmedia.beans.KeyWordObjects_Json;
import com.invixo.socialmedia.beans.Schema_Json;
import com.invixo.socialmedia.beans.SourceDetails;
import com.invixo.socialmedia.beans.Table_01_schema_json;
import com.invixo.socialmedia.beans.Table_02_schema_csv;
import com.invixo.socialmedia.beans.Table_02_schema_json;
import com.invixo.socialmedia.util.ApplicationConstatnts;

public class JsonConverter_Table_02 {

	static List<KeyWordObjects_Json> twitter_keyWordListIndustries = new ArrayList<KeyWordObjects_Json>();
	static List<KeyWordObjects_Json> twitter_keyWordListMajor = new ArrayList<KeyWordObjects_Json>();
	static List<KeyWordObjects_Json> facebook_keyWordListIndustries = new ArrayList<KeyWordObjects_Json>();
	static List<KeyWordObjects_Json> facebook_keyWordListMajor = new ArrayList<KeyWordObjects_Json>();

	Schema_Json table_facebook_industrialkey = new Schema_Json();

	Schema_Json table_facebook_majorKey = new Schema_Json();

	Schema_Json table_twitter_industrialkey = new Schema_Json();

	Schema_Json table_twiiter_majorKey = new Schema_Json();

	public Table_02_schema_json writeAsJson_02(List<Map<?, ?>> data, File file) throws IOException {

		List<Schema_Json> shema_JsonList = new ArrayList<Schema_Json>();

		Table_02_schema_json table_02 = new Table_02_schema_json();

		JsonConverter_Table_02 JsonConverter = new JsonConverter_Table_02();

		for (Map<?, ?> map : data) {
			Table_02_schema_csv schema_CSV = new Table_02_schema_csv();

			Object source_value = null;
			Object source_key_value = null;
			Object keywordType_value = null;
			Object keyWordList_value = null;
			Object count_value = null;
			for (Entry<?, ?> entry : map.entrySet()) {

				source_value = map.get(ApplicationConstatnts.source);
				schema_CSV.setSource(source_value.toString());
				source_key_value = map.get(ApplicationConstatnts.sourceKey);

				schema_CSV.setSourceKey(source_key_value.toString());

				keywordType_value = map.get(ApplicationConstatnts.keywordType);
				schema_CSV.setKeywordType(keywordType_value.toString());
				keyWordList_value = map.get(ApplicationConstatnts.keyWord);

				schema_CSV.setKeyWord(keyWordList_value.toString());

				count_value = map.get(ApplicationConstatnts.count);
				schema_CSV.setCount(count_value.toString());
			}

			if ((source_value.toString().equalsIgnoreCase(ApplicationConstatnts.twitter))
					&& (keywordType_value.toString().equalsIgnoreCase(ApplicationConstatnts.industries))) {
				table_twitter_industrialkey = JsonConverter.getdataObject(table_twitter_industrialkey, schema_CSV);
				shema_JsonList.add(table_twitter_industrialkey);

			} else if ((source_value.toString().equalsIgnoreCase(ApplicationConstatnts.twitter))
					&& (keywordType_value.toString().equalsIgnoreCase(ApplicationConstatnts.majorkeywords))) {

				table_twiiter_majorKey = JsonConverter.getdataObject(table_twiiter_majorKey, schema_CSV);
				shema_JsonList.add(table_twiiter_majorKey);

			}

			else if ((source_value.toString().equalsIgnoreCase(ApplicationConstatnts.facebook))
					&& (keywordType_value.toString().equalsIgnoreCase(ApplicationConstatnts.majorkeywords))) {

				table_facebook_majorKey = JsonConverter.getdataObject(table_facebook_majorKey, schema_CSV);
				shema_JsonList.add(table_facebook_majorKey);

			} else if ((source_value.toString().equalsIgnoreCase(ApplicationConstatnts.facebook))
					&& (keywordType_value.toString().equalsIgnoreCase(ApplicationConstatnts.industries))) {

				table_facebook_industrialkey = JsonConverter.getdataObject(table_facebook_industrialkey, schema_CSV);
				shema_JsonList.add(table_facebook_industrialkey);

			}

		}
		if (table_twitter_industrialkey.getSource() != null) {
			shema_JsonList.add(table_twitter_industrialkey);
		}
		if (table_twiiter_majorKey.getSource() != null) {
			shema_JsonList.add(table_twiiter_majorKey);
		}
		if (table_facebook_industrialkey.getSource() != null) {
			shema_JsonList.add(table_facebook_industrialkey);
		}
		if (table_facebook_majorKey.getSource() != null) {
			shema_JsonList.add(table_facebook_majorKey);
		}

		table_02.setShema_JsonList(shema_JsonList);
		return table_02;
	}

	public Schema_Json getdataObject(Schema_Json table_01, Table_02_schema_csv schemacsv) {

		if (schemacsv.getKeywordType().equalsIgnoreCase(ApplicationConstatnts.industries))

		{
			if (table_01.getSource() == null) {
				table_01.setSource(schemacsv.getSource());

				table_01.setSource_Key(schemacsv.getSource());
				table_01.setKeywordType(schemacsv.getKeywordType());
			}

			KeyWordObjects_Json kwo = new KeyWordObjects_Json();
			kwo.setKeyWord(schemacsv.getKeyWord());
			kwo.setCount(schemacsv.getCount());
			System.out.println(kwo.getKeyWord() + "|" + kwo.getCount());

			if (table_01.getSource().equalsIgnoreCase(ApplicationConstatnts.twitter)) {
				twitter_keyWordListIndustries.add(kwo);
				table_01.setKeywordList(twitter_keyWordListIndustries);
			} else if (table_01.getSource().equalsIgnoreCase(ApplicationConstatnts.facebook)) {
				facebook_keyWordListIndustries.add(kwo);
				table_01.setKeywordList(twitter_keyWordListIndustries);
			}

		}

		else if (schemacsv.getKeywordType().equalsIgnoreCase(ApplicationConstatnts.majorkeywords))

		{
			if (table_01.getSource() == null) {
				table_01.setSource(schemacsv.getSource());

				table_01.setSource_Key(schemacsv.getSource());
				table_01.setKeywordType(schemacsv.getKeywordType());
			}

			KeyWordObjects_Json kwo = new KeyWordObjects_Json();
			kwo.setKeyWord(schemacsv.getKeyWord());
			kwo.setCount(schemacsv.getCount());
			System.out.println(kwo.getKeyWord() + "|" + kwo.getCount());

			if (table_01.getSource().equalsIgnoreCase(ApplicationConstatnts.twitter)) {
				twitter_keyWordListMajor.add(kwo);
				table_01.setKeywordList(twitter_keyWordListIndustries);
			} else if (table_01.getSource().equalsIgnoreCase(ApplicationConstatnts.facebook)) {
				facebook_keyWordListMajor.add(kwo);
				table_01.setKeywordList(facebook_keyWordListMajor);
			}

		}

		return table_01;

	}

	public static Table_01_schema_json writeAsJson_01(List<Map<?, ?>> data, File file) throws IOException {
		Table_01_schema_json table_01 = new Table_01_schema_json();
		List<SourceDetails> list_sourceDetails = new ArrayList<SourceDetails>();
		for (Map<?, ?> map : data) {

			SourceDetails sourceDetails = new SourceDetails();
			for (Entry<?, ?> entry : map.entrySet()) {

				sourceDetails.setSourceKey(map.get(ApplicationConstatnts.sourceKey).toString());

				sourceDetails.setSource(map.get(ApplicationConstatnts.source).toString());

				sourceDetails.setCountOfMsgs(map.get(ApplicationConstatnts.countOfMsgs).toString());

			}
			list_sourceDetails.add(sourceDetails);

		}

		table_01.setSourceDetails(list_sourceDetails);
		return table_01;

	}

}
