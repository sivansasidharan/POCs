package com.invixo.ss.content.extractor;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;

import technology.tabula.ObjectExtractor;
import technology.tabula.Page;
import technology.tabula.PageIterator;
import technology.tabula.RectangularTextContainer;
import technology.tabula.Table;
import technology.tabula.extractors.BasicExtractionAlgorithm;
import technology.tabula.extractors.ExtractionAlgorithm;

public class TableExtract {

	/*
	 * //traprange sample not suitable public static void main(String[] args) {
	 * PDFTableExtractor extractor = new PDFTableExtractor(); List<Table> tables
	 * = extractor .setSource(
	 * "C:\\Users\\sivan.sasidharan\\Desktop\\Sample\\RT_Annual_Report_2015.pdf")
	 * .addPage(2).extract(); String html = tables.get(0).toHtml(); String csv =
	 * tables.get(0).toString(); System.out.println("csv - " + csv); }
	 */

	public static void main(String[] args) throws IOException {
		PDDocument document = PDDocument
				.load(new File("C:\\Users\\sivan.sasidharan\\Desktop\\Sample\\RT_Annual_Report_2015.pdf"));
		ObjectExtractor oe = new ObjectExtractor(document);
		ExtractionAlgorithm extractor = new BasicExtractionAlgorithm();
		PageIterator iterator = oe.extract();
		while (iterator.hasNext()) {
			Page page = (Page) iterator.next();

			for (Table table : extractor.extract(page)) {
				for (List<RectangularTextContainer> row : table.getRows()) {
					for(RectangularTextContainer cell : row){
						System.out.println("cell"+cell.getText());
					}
				}
			}
		}
	}
}
