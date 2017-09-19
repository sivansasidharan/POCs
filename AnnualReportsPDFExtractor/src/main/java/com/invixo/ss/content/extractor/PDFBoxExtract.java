package com.invixo.ss.content.extractor;

import java.io.IOException;

public class PDFBoxExtract {

	public static void main(String[] args) throws IOException {
		PDFManager pdfManager = new PDFManager();
		pdfManager.setFilePath("C:\\Users\\sivan.sasidharan\\Desktop\\Sample\\RT_Annual_Report_2015.pdf");
		//System.out.println(pdfManager.ToText());

	}
}