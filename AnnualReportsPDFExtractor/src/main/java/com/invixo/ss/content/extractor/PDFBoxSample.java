package com.invixo.ss.content.extractor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

public class PDFBoxSample {

	public static void main(String[] args) throws IOException, SAXException, TikaException {

		PDDocument document = PDDocument
				.load(new File("C:\\Users\\sivan.sasidharan\\Desktop\\Sample\\IBM-Annual-Report-2014.pdf"));

		// Data Extraction Page Wise Using PDFBOX
		PDFTextStripper pdfStripper = new PDFTextStripper();
		int filecount = 0;
		Splitter splitter = new Splitter();
		List<PDDocument> splittedDocuments = splitter.split(document);
		String companyName = "";
		for (PDDocument pdDocument : splittedDocuments) {

			if (filecount == 0) {
				companyName = pullLinkURL(pdfStripper.getText(pdDocument)).toUpperCase();
				if(companyName.equals("")){
					companyName=matchCompanyName(pdfStripper.getText(pdDocument)).toUpperCase();
				}
			}
			File file = new File("C:\\Users\\sivan.sasidharan\\Desktop\\Sample\\ContentExtract\\IBM\\" + companyName+"_"+"Annual_Report_2014"+
					"_"+filecount + ".txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(pdfStripper.getText(pdDocument));
			bw.close();
			filecount++;
		}

	}

	private static String pullLinkURL(String text) {
		String url = "";
		String regex = "(?:[a-zA-Z0-9](?:[a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}";
		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		Matcher matcher = p.matcher(text);
		if (matcher.find()) {
			url = matcher.group(0);
		}
		return url.split(".com")[0];
	}
	
	private static String matchCompanyName(String text){
		
		String name = "";
		Pattern pattern = Pattern.compile("(\\w+)(\\s\\bAnnual Report)");
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
		    System.out.println(matcher.group(1)); //prints /{item}/
		    name = matcher.group(1);
		} else {
		    System.out.println("Match not found");
		}
		
		return name;
	}
}
