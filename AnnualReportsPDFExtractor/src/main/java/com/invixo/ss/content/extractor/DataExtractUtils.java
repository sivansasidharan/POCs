package com.invixo.ss.content.extractor;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public final class DataExtractUtils {

	public static String extractCompanyName(String sourcePdf) throws IOException {

		PDDocument document = PDDocument.load(new File(sourcePdf));
		PDFTextStripper pdfStripper = new PDFTextStripper();
		int filecount = 0;
		String linkName = "";
		Splitter splitter = new Splitter();
		List<PDDocument> splittedDocuments = splitter.split(document);

		for (PDDocument pdDocument : splittedDocuments) {
			if (filecount == 0) {
				linkName = pullLinks(pdfStripper.getText(pdDocument));
				if (linkName.equals("")) {
					linkName = matchCompanyName(pdfStripper.getText(pdDocument));
				}
			}
			break;
		}
		System.out.println("Company Name set --> " + linkName);
		return linkName;
	}

	private static String matchCompanyName(String text) {

		String name = "";
		Pattern pattern = Pattern.compile("(\\w+)(\\s\\bAnnual Report)");
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			System.out.println(matcher.group(1)); // prints /{item}/
			name = matcher.group(1);
		} else {
			System.out.println("Match not found");
		}

		return name;
	}

	public static String extractSearchInfo(String text) throws IOException {

		String searchData = "";
		Pattern p = Pattern.compile("[^.?!](?<=[.?\\S\\s!])net loss (?=[\\S\\s.?!])[^.?!]*[.?!]+.{3,10}");
		Matcher m = p.matcher(text);
		if (m.find()) {
			// we're only looking for one group, so get it
			String theGroup = m.group();
			System.out.println("here 1--> " + theGroup);
			searchData = theGroup;
		}
		return searchData.trim();
	}

	public static String extractCashInfo(String text) throws IOException {

		String cashInfo = "";
		Pattern p = null;
		Matcher m = null;
		String p1 = "US\\$(?<=\\$)\\d+(\\.\\d+)?\\b.{3,8}";// "\\$[0-9]\\.[0-9]\\b.{3,10}";//(?<=\$)\d+(\.\d+)?\b.{3,8}
		// String p2 =
		// "[^.?!](?<=[?\\S\\s!])US$(?=[\\S\\s.?!])[^?!]*[.?!]+.{3,10}";
		p = Pattern.compile(p1);
		m = p.matcher(text);
		if (m.find()) {
			String theGroup = m.group();
			System.out.println("here 2-1 --> " + theGroup);
			cashInfo = theGroup;
		}
		return cashInfo;
	}

	private static String pullLinks(String text) {

		String linkName = "";
		String regex = "(?:[a-zA-Z0-9](?:[a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}";
		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		Matcher matcher = p.matcher(text);
		if (matcher.find()) {
			String url = (matcher.group(0));
			String[] exits = url.split(".com");
			linkName = exits[0];
		}
		return linkName;
	}

	
}
