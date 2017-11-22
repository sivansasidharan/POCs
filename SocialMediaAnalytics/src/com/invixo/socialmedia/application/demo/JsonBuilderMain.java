package com.invixo.socialmedia.application.demo;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.invixo.socialmedia.beans.Table_01_schema_csv;
import com.invixo.socialmedia.beans.Table_01_schema_json;
import com.invixo.socialmedia.beans.Table_02_schema_csv;
import com.invixo.socialmedia.beans.Table_02_schema_json;
import com.invixo.socialmedia.beans.Table_03_schema_csv;
import com.invixo.socialmedia.csv.application.CSVFileReader;
import com.invixo.socialmedia.csv.application.JsonConverter_Table_01;
import com.invixo.socialmedia.csv.application.JsonConverter_Table_02;
import com.invixo.socialmedia.csv.application.JsonConverter_Table_03;
import com.invixo.socialmedia.persist.MongoConnect;

public class JsonBuilderMain {

	public static void main(String[] args) {

		File input_01 = new File(".\\input\\Doughnut.csv");
		File input_02 = new File(".\\input\\inputFile.csv");
		File input_03 = new File(".\\input\\inputFile_03.csv");

		File output_01 = new File(".\\output\\out_01.json");
		File output_02 = new File(".\\output\\out_02.json");
		File output_03 = new File(".\\output\\out_03.json");

		try {
			List<Map<?, ?>> data_01 = CSVFileReader.readObjectsFromCsv(input_01, Table_01_schema_csv.class);
			List<Map<?, ?>> data_02 = CSVFileReader.readObjectsFromCsv(input_02, Table_02_schema_csv.class);
			List<Map<?, ?>> data_03 = CSVFileReader.readObjectsFromCsv(input_03, Table_03_schema_csv.class);

			JsonConverter_Table_02 jsonconverter = new JsonConverter_Table_02();
			JsonConverter_Table_01 jsonconverter_table_01 = new JsonConverter_Table_01();
			JsonConverter_Table_03 jsonconverter_table_03 = new JsonConverter_Table_03();

			// jsonconverter.writeAsJson_01(data_01, output_01);
			Table_02_schema_json dataTable_02 = jsonconverter.writeAsJson_02(data_02, output_02);

			Table_01_schema_json dataTable_01 = jsonconverter_table_01.writeAsJson_01(data_01, output_01);

			jsonconverter_table_03.writeasJson_03(data_03, output_03);

			MongoConnect mongoConnect = new MongoConnect();

			// mongoConnect.persistMongo("SocialMediaInsights_02",dataTable_02);

			// mongoConnect.persistMongo("SocialMediaInsights_01",
			// dataTable_01);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
