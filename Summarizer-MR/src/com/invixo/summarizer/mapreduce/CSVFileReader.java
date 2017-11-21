package com.invixo.summarizer.mapreduce;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class CSVFileReader {

	public static List<Map<?, ?>> readObjectsFromCsv(Reader file, Class<?> schema_CSV) {

		CsvMapper csvMapper = new CsvMapper();
		CsvSchema schema;

		List<Map<?, ?>> listDataInMap = null;

		try {
			schema = csvMapper.schemaFor(schema_CSV);
			MappingIterator<Map<?, ?>> mappingIterator = csvMapper.reader(Map.class).with(schema).readValues(file);
			listDataInMap = mappingIterator.readAll();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return listDataInMap;

	}

}
