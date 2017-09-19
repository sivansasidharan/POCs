package com.invixo.ss.content.extractor;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

	public static void main(String[] args) throws IOException {

		test6("$");
		// test2();
	}

	
	private static String test6(String cashInfo) {

		Number number = null;
		String validCashInfo = null;
		try {
			number = NumberFormat.getCurrencyInstance(Locale.US).parse(cashInfo);
		} catch (ParseException pe) {
			// ignore
		}

		if (number != null) {
			validCashInfo = cashInfo;
		} else {
			validCashInfo = "";
		}
		System.out.println("validCashInfo -->"+validCashInfo);
		return validCashInfo;
	}
	
	
	private static void test5(String searchString) {

		HttpClient httpclient = HttpClients.createDefault();
		try {
			URIBuilder builder = new URIBuilder(
					"https://api.projectoxford.ai/luis/v1/application?id=6ae16ff0-1ff7-4a12-bec8-edcaebc8890d&subscription-key=e482e0e2024f44a5b98ff1b15a090bba"
							+ "&q='" + URLEncoder.encode(searchString)+"'");
			URI uri = builder.build();
			
			HttpGet getRequest = new HttpGet(uri);
			HttpResponse response = httpclient.execute(getRequest);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				System.out.println("--return value -->" + EntityUtils.toString(entity));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private static void test1() throws IOException {

		PDDocument document = PDDocument
				.load(new File("C:\\Users\\sivan.sasidharan\\Desktop\\Sample\\RT_Annual_Report_2015.pdf"));
		PDFTextStripper pdfStripper = new PDFTextStripper();
		Splitter splitter = new Splitter();
		List<PDDocument> splittedDocuments = splitter.split(document);
		for (PDDocument pdDocument : splittedDocuments) {
			// 1 --> [^.?!]*(?<=[.?\\s!])net loss(?=[\\s.?!])[^.?!]*[.?!]*\\.
			// 2--> [^.?!]*(?<=[.?\\s!])net loss(?=[\\s.?!])[^.?!]*[.?!]*.

			//
			Pattern p = Pattern.compile("[^.?!](?<=[.?\\S\\s!])net loss (?=[\\S\\s.?!])[^.?!]*[.?!]+.{3,10}");
			Matcher m = p.matcher(pdfStripper.getText(pdDocument));
			if (m.find()) {
				// we're only looking for one group, so get it
				String theGroup = m.group();
				System.out.format("'%s'\n", theGroup);
			}
		}

	}

	private static void test2() throws IOException {

		PDDocument document = PDDocument
				.load(new File("C:\\Users\\sivan.sasidharan\\Desktop\\Sample\\RT_Annual_Report_2015.pdf"));

		// Data Extraction Page Wise Using PDFBOX
		PDFTextStripper pdfStripper = new PDFTextStripper();
		int filecount = 0;
		Splitter splitter = new Splitter();
		List<PDDocument> splittedDocuments = splitter.split(document);

		for (PDDocument pdDocument : splittedDocuments) {

			if (filecount == 0) {
				pullLinks(pdfStripper.getText(pdDocument));
			}
			filecount++;
			/*
			 * filecount++; File file = new File(
			 * "C:\\Users\\sivan.sasidharan\\Desktop\\Sample\\ContentExtract\\ContentExtract"
			 * + filecount + ".txt"); if (!file.exists()) {
			 * file.createNewFile(); } FileWriter fw = new
			 * FileWriter(file.getAbsoluteFile()); BufferedWriter bw = new
			 * BufferedWriter(fw); bw.write(pdfStripper.getText(pdDocument));
			 * bw.close();
			 */
		}

	}

	private static void test3() throws IOException {

		Pattern p = Pattern.compile("[^.?!](?<=[?\\S\\s!])US(?=[\\S\\s.?!])[^?!]*[.?!]+.{3,10}");
		Matcher m = p.matcher("net loss of US$0.9 billion");
		if (m.find()) {
			String theGroup = m.group();
			System.out.format("'%s'\n", theGroup);
		}

	}

	private static void test4() {
		System.out.println("test 4 ");

		// .*?\b(loss|losses|net|profit)\b.*\n.*
		Pattern p = Pattern.compile("(?i)(.*?\\b(loss|losses|net)\\b.*\\n.*)");
		Matcher m = p.matcher(". net loss of US$0.9 billion for the " + "loss of net loss US$345.5");
		if (m.find()) {
			String theGroup = m.group();
			System.out.format("'%s'\n", theGroup);
		}

	}

	private static void pullLinks(String text) {

		String regex = "(?:[a-zA-Z0-9](?:[a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}";
		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		Matcher matcher = p.matcher(text);
		if (matcher.find()) {
			String url = (matcher.group(0));
			String[] exits = url.split(".com");
			System.out.println(exits[0]);
		}
	}
}
