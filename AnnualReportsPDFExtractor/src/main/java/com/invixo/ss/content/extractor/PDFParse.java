package com.invixo.ss.content.extractor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class PDFParse {

	public static void main(final String[] args) throws IOException, TikaException {

		BodyContentHandler handler = new BodyContentHandler(-1);
		Metadata metadata = new Metadata();
		FileInputStream inputstream = new FileInputStream(
				new File("C:\\Users\\sivan.sasidharan\\Desktop\\Sample\\RT_Annual_Report_2015.pdf"));
		ParseContext pcontext = new ParseContext();

		// parsing the document using PDF parser
		PDFParser pdfparser = new PDFParser();

		for (int i = 0; i <= Integer.parseInt(metadata.get("xmpTPg:NPages")); i++) {
			try {
				pdfparser.parse(inputstream, handler, metadata, pcontext);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// getting the content of the document
			// System.out.println("Contents of the PDF :" + handler.toString());

			File file = new File("C:\\Users\\sivan.sasidharan\\Desktop\\Sample\\ContentExtract.txt");
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(handler.toString());
			bw.close();
		}
		// getting metadata of the document
		System.out.println("Metadata of the PDF:");
		String[] metadataNames = metadata.names();

		for (String name : metadataNames) {
			System.out.println(name + " : " + metadata.get(name));
		}
	}
}