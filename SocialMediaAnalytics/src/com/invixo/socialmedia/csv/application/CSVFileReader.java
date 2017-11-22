package com.invixo.socialmedia.csv.application;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class CSVFileReader {

	public static List<Map<?, ?>> readObjectsFromCsv(File file, Class<?> schema_CSV) throws IOException {

		CsvMapper csvMapper = new CsvMapper();
		CsvSchema schema = csvMapper.schemaFor(schema_CSV);
		MappingIterator<Map<?, ?>> mappingIterator = csvMapper.reader(Map.class).with(schema).readValues(file);
		return mappingIterator.readAll();
	}

}
