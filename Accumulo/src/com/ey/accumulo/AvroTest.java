package com.ey.accumulo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;

public class AvroTest {
    public static void main(String[] args) throws IOException {
        // Schema
        String schemaDescription = " {    \n"
                + " \"name\": \"FacebookUser\", \n"
                + " \"type\": \"record\",\n" + " \"fields\": [\n"
                + "   {\"name\": \"name\", \"type\": \"string\"},\n"
                + "   {\"name\": \"num_likes\", \"type\": \"int\"},\n"
                + "   {\"name\": \"num_photos\", \"type\": \"int\"},\n"
                + "   {\"name\": \"num_groups\", \"type\": \"int\"} ]\n" + "}";

        Schema s = Schema.parse(schemaDescription);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Encoder e = EncoderFactory.get().binaryEncoder(outputStream, null);
        GenericDatumWriter w = new GenericDatumWriter(s);

        // Populate data
        GenericRecord r = new GenericData.Record(s);
        r.put("name", new org.apache.avro.util.Utf8("Doctor Who"));
        r.put("num_likes", 1);
        r.put("num_groups", 423);
        r.put("num_photos", 0);
        System.out.println("Generic==>"+r);
        // Encode
        //w.write(r, e);
        
        //e.flush();
       
        byte[] encodedByteArray = outputStream.toByteArray();
        String encodedString = outputStream.toString();
       
        System.out.println("encodedString: "+encodedString);
       
        // Decode using same schema
        DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>(s);
        Decoder decoder = DecoderFactory.get().binaryDecoder(encodedByteArray, null);
        GenericRecord result = reader.read(null, decoder);
        System.out.println(result.get("name").toString());
        System.out.println(result.get("num_likes").toString());
        System.out.println(result.get("num_groups").toString());
        System.out.println(result.get("num_photos").toString());
       
    }
}
