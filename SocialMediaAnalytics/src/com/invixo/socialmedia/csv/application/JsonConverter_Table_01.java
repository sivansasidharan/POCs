package com.invixo.socialmedia.csv.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.invixo.socialmedia.beans.SourceDetails;
import com.invixo.socialmedia.beans.Table_01_schema_json;
import com.invixo.socialmedia.util.ApplicationConstatnts;

public class JsonConverter_Table_01 {

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
