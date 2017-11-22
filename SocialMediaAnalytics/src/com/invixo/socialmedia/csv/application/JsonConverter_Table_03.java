package com.invixo.socialmedia.csv.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.invixo.socialmedia.beans.KeyWordTypeDataFrame;
import com.invixo.socialmedia.beans.KeywordDataFrame;
import com.invixo.socialmedia.beans.SourceDataFrame;
import com.invixo.socialmedia.beans.Table_03_schema_csv;
import com.invixo.socialmedia.beans.Table_03_schema_json;
import com.invixo.socialmedia.util.ApplicationConstatnts;

public class JsonConverter_Table_03 {

	static List<KeywordDataFrame> keywordDataFrameList_majorKeyword_twitter = new ArrayList<KeywordDataFrame>();
	static List<KeywordDataFrame> keywordDataFrameList_industrial_facebook = new ArrayList<KeywordDataFrame>();

	static List<KeywordDataFrame> keywordDataFrameList_majorKeyword_facebook = new ArrayList<KeywordDataFrame>();
	static List<KeywordDataFrame> keywordDataFrameList_industrial_twitter = new ArrayList<KeywordDataFrame>();

	static List<KeyWordTypeDataFrame> keywordTypeDataFrameList_facebook = new ArrayList<KeyWordTypeDataFrame>();

	static List<KeyWordTypeDataFrame> keywordTypeDataFrameList_twitter = new ArrayList<KeyWordTypeDataFrame>();

	public void writeasJson_03(List<Map<?, ?>> data, File file) {

		Table_03_schema_json table_03_schema_json = new Table_03_schema_json();

		Table_03_schema_csv table_03_schema_csv = new Table_03_schema_csv();

		KeywordDataFrame keywordDataFrame = new KeywordDataFrame();
		SourceDataFrame sourceDataFrame = new SourceDataFrame();

		List<SourceDataFrame> sourceDataFrameList = new ArrayList<SourceDataFrame>();

		for (Map<?, ?> map : data) {

			for (Entry<?, ?> entry : map.entrySet()) {

				table_03_schema_csv.setSourceKey(map.get(ApplicationConstatnts.sourceKey).toString());

				table_03_schema_csv.setSource(map.get(ApplicationConstatnts.source).toString());

				table_03_schema_csv.setKeyWord(map.get(ApplicationConstatnts.keyWord).toString());
				table_03_schema_csv.setKeywordType(map.get(ApplicationConstatnts.keywordType).toString());
				table_03_schema_csv.setMessage(map.get(ApplicationConstatnts.message).toString());
				table_03_schema_csv.setMessageId(map.get(ApplicationConstatnts.messageId).toString());
				table_03_schema_csv.setUserName(map.get(ApplicationConstatnts.userName).toString());
				table_03_schema_csv.setTimestamp(map.get(ApplicationConstatnts.timestamp).toString());

			}
			List<SourceDataFrame> previousSourceDataFrameList = new ArrayList<SourceDataFrame>();

			if (table_03_schema_json.getSourceDataFrameList() != null) {

				previousSourceDataFrameList = table_03_schema_json.getSourceDataFrameList();

				for (int i = 0; i < previousSourceDataFrameList.size(); i++) {

					if (table_03_schema_csv.getSource()
							.equalsIgnoreCase(previousSourceDataFrameList.get(i).getSource())) {

						sourceDataFrame = previousSourceDataFrameList.get(i);
						break;

					}
				}

			}
			sourceDataFrame = getSourceDataFrame(sourceDataFrame, table_03_schema_csv);

		}

		sourceDataFrameList.add(sourceDataFrame);
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(file, sourceDataFrameList);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private KeyWordTypeDataFrame getkeywordTypeDataFrame(KeyWordTypeDataFrame keywordTypeDataFrame,
			Table_03_schema_csv table_03_schema_csv) {

		if (table_03_schema_csv.getKeywordType().equalsIgnoreCase(ApplicationConstatnts.industries)) {

			if (keywordTypeDataFrame.getKeywordType() == null) {

				keywordTypeDataFrame.setKeywordType(table_03_schema_csv.getKeywordType());
			}

			KeywordDataFrame keywordDataFrame = getKeywordDataFrame(table_03_schema_csv);

			if (table_03_schema_csv.getSource().equalsIgnoreCase(ApplicationConstatnts.facebook)) {
				keywordDataFrameList_industrial_facebook.add(keywordDataFrame);

				keywordTypeDataFrame.setKeywordDataFrameList(keywordDataFrameList_industrial_facebook);
			}

			if (table_03_schema_csv.getSource().equalsIgnoreCase(ApplicationConstatnts.twitter)) {
				keywordDataFrameList_industrial_twitter.add(keywordDataFrame);
				keywordTypeDataFrame.setKeywordDataFrameList(keywordDataFrameList_industrial_twitter);
			}

		}

		if (table_03_schema_csv.getKeywordType().equalsIgnoreCase(ApplicationConstatnts.majorkeywords)) {

			if (keywordTypeDataFrame.getKeywordType() == null) {

				keywordTypeDataFrame.setKeywordType(table_03_schema_csv.getKeywordType());
			}

			KeywordDataFrame keywordDataFrame = getKeywordDataFrame(table_03_schema_csv);
			if (table_03_schema_csv.getSource().equalsIgnoreCase(ApplicationConstatnts.facebook)) {
				keywordDataFrameList_majorKeyword_facebook.add(keywordDataFrame);

				keywordTypeDataFrame.setKeywordDataFrameList(keywordDataFrameList_majorKeyword_facebook);
			}

			if (table_03_schema_csv.getSource().equalsIgnoreCase(ApplicationConstatnts.twitter)) {
				keywordDataFrameList_majorKeyword_twitter.add(keywordDataFrame);
				keywordTypeDataFrame.setKeywordDataFrameList(keywordDataFrameList_majorKeyword_twitter);
			}

		}

		return keywordTypeDataFrame;
	}

	public KeywordDataFrame getKeywordDataFrame(Table_03_schema_csv table_03_schema_csv) {

		KeywordDataFrame keywordDataFrame = new KeywordDataFrame();

		keywordDataFrame.setKeyword(table_03_schema_csv.getKeyWord());

		keywordDataFrame.setMessage(table_03_schema_csv.getMessage());

		keywordDataFrame.setMessageId(table_03_schema_csv.getMessageId());

		keywordDataFrame.setTimestamnp(table_03_schema_csv.getTimestamp());

		keywordDataFrame.setUserName(table_03_schema_csv.getUserName());

		return keywordDataFrame;

	}

	public SourceDataFrame getSourceDataFrame(SourceDataFrame sourceDataFrame,
			Table_03_schema_csv table_03_schema_csv) {

		if (sourceDataFrame.getSource() == null) {
			sourceDataFrame.setSource(table_03_schema_csv.getSource());
			sourceDataFrame.setSourceKey(table_03_schema_csv.getSourceKey());

		}

		List<KeyWordTypeDataFrame> previouskeywordTypeDataFrameList = new ArrayList<KeyWordTypeDataFrame>();

		if (table_03_schema_csv.getSource().equalsIgnoreCase(ApplicationConstatnts.facebook)) {

			KeyWordTypeDataFrame keywordTypeDataFrame = new KeyWordTypeDataFrame();
			if (sourceDataFrame.getKeywordTypeDataFrameList() != null) {

				previouskeywordTypeDataFrameList = sourceDataFrame.getKeywordTypeDataFrameList();

				for (int i = 0; i < previouskeywordTypeDataFrameList.size(); i++) {

					if (previouskeywordTypeDataFrameList.get(i).getKeywordType()
							.equalsIgnoreCase(table_03_schema_csv.getKeywordType())) {

						keywordTypeDataFrame = previouskeywordTypeDataFrameList.get(i);

						break;

					}
				}

			}

			keywordTypeDataFrameList_facebook.add(getkeywordTypeDataFrame(keywordTypeDataFrame, table_03_schema_csv));

			sourceDataFrame.setKeywordTypeDataFrameList(keywordTypeDataFrameList_facebook);

		}
		if (table_03_schema_csv.getSource().equalsIgnoreCase(ApplicationConstatnts.twitter)) {

			KeyWordTypeDataFrame keywordTypeDataFrame = new KeyWordTypeDataFrame();

			if (sourceDataFrame.getKeywordTypeDataFrameList() != null) {

				previouskeywordTypeDataFrameList = sourceDataFrame.getKeywordTypeDataFrameList();

				for (int i = 0; i < previouskeywordTypeDataFrameList.size(); i++) {

					if (previouskeywordTypeDataFrameList.get(i).getKeywordType()
							.equalsIgnoreCase(table_03_schema_csv.getKeywordType())) {

						keywordTypeDataFrame = previouskeywordTypeDataFrameList.get(i);

						break;

					}
				}

			}

			keywordTypeDataFrameList_twitter.add(getkeywordTypeDataFrame(keywordTypeDataFrame, table_03_schema_csv));

			sourceDataFrame.setKeywordTypeDataFrameList(keywordTypeDataFrameList_twitter);
		}

		return sourceDataFrame;
	}
}
