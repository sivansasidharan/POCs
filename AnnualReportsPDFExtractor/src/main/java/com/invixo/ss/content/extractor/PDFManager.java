package com.invixo.ss.content.extractor;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFManager {

	private PDFParser parser;
	private PDFTextStripper pdfStripper;
	private PDDocument pdDoc;
	private COSDocument cosDoc;

	private String Text;
	private String filePath;
	private File file;

	public PDFManager() {

	}

	public String ToText() throws IOException {
		this.pdfStripper = null;
		this.pdDoc = null;
		this.cosDoc = null;
		int filecount = 0;
		file = new File(filePath);
		parser = new PDFParser(new RandomAccessFile(file, "r"));
		parser.parse();
		cosDoc = parser.getDocument();
		pdfStripper = new PDFTextStripper();
		pdDoc = new PDDocument(cosDoc);
		pdDoc.getNumberOfPages();
		// pdfStripper.setStartPage(1);
		// pdfStripper.setEndPage(10);
		// Text = pdfStripper.getText(pdDoc);
		Splitter splitter = new Splitter();
		List<PDDocument> splittedDocuments = splitter.split(pdDoc);
		for (PDDocument pdDocument : splittedDocuments) {
			Text = pdfStripper.getText(pdDocument);
			filecount++;
			File file = new File("C:\\Users\\sivan.sasidharan\\Desktop\\Sample\\ContentExtract" + filecount + ".txt");
			if (!file.exists()) {
				file.createNewFile();
			}
		}
		return Text;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
