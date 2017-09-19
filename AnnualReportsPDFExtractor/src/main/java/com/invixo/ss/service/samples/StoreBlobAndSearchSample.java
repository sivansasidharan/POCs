package com.invixo.ss.service.samples;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.invixo.ss.content.extractor.DataExtractUtils;
import com.invixo.ss.service.utils.IndexSearchOptions;
import com.invixo.ss.service.utils.IndexSearchResult;
import com.invixo.ss.service.utils.SearchIndexClient;
import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;

public class StoreBlobAndSearchSample {

	public static String storageConnectionString = "";

	private static final String SERVICE_NAME = "taxdata3";
	private static final String INDEX_NAME = "tax-poc-index";
	private static final String API_KEY = "63BCC4552225E345B44C488B0717AA5B";
	private static SearchIndexClient indexClient = new SearchIndexClient(SERVICE_NAME, INDEX_NAME, API_KEY);

	public static void main(String[] args) throws IOException {
		storeBlobData();
		// searchAndExtractData();
	}

	private static void storeBlobData() {

		String sourcePDF = "C:\\Users\\sivan.sasidharan\\Desktop\\Sample\\RT_Annual_Report_2015.pdf";
		String sourceDir = "C:\\Users\\sivan.sasidharan\\Desktop\\Sample\\ContentExtract\\Rio";
		String key = "83DaTzFJP3iB8dah4RC7dqG7TOeFxKnLG5Gft1RXX8uFlgawazTjX0rWwacvVhQ6srF/zgjGpUKKoS5VDqoQdA==";
		byte[] authBytes = key.getBytes(StandardCharsets.UTF_8);
		String encodedKey = Base64.getEncoder().encodeToString(authBytes);
		storageConnectionString = "DefaultEndpointsProtocol=https;" + "AccountName=taxdatapocstorage01;" + "AccountKey="
				+ key;
		try {
			CloudStorageAccount account = CloudStorageAccount.parse(storageConnectionString);
			CloudBlobClient serviceClient = account.createCloudBlobClient();

			// Container name must be lower case.
			CloudBlobContainer container = serviceClient.getContainerReference("data01");
			container.createIfNotExists();
			// get Files and upload to container
			List<File> filesInFolder = Files.walk(Paths.get(sourceDir)).filter(Files::isRegularFile).map(Path::toFile)
					.collect(Collectors.toList());
			String companyName = DataExtractUtils.extractCompanyName(sourcePDF);
			for (File sourceFile : filesInFolder) {
				// Upload the files.
				CloudBlockBlob blob = container.getBlockBlobReference(sourceFile.getName());
				//System.out.println("Blob uri - " + blob.getQualifiedUri());
				HashMap<String, String> metadataInfo = new HashMap<>();
				metadataInfo.put("filename", sourceFile.getName());
				metadataInfo.put("size", Long.toString(sourceFile.length()));
				metadataInfo.put("companyname", companyName.toUpperCase());
				metadataInfo.put("url", blob.getQualifiedUri().toString());
				blob.setMetadata(metadataInfo);
				blob.upload(new FileInputStream(sourceFile), sourceFile.length());
				System.out.println("Blob Stored - " + blob.getName());

				// Ends here
			}

			// Download the file.
			// File destinationFile = new File(sourceFile.getParentFile(),
			// "fileDownload.tmp");
			// blob.downloadToFile(destinationFile.getAbsolutePath());
		} catch (FileNotFoundException fileNotFoundException) {
			System.out.print("FileNotFoundException encountered: ");
			System.out.println(fileNotFoundException.getMessage());
			System.exit(-1);
		} catch (StorageException storageException) {
			System.out.print("StorageException encountered: ");
			System.out.println(storageException.getMessage());
			System.exit(-1);
		} catch (Exception e) {
			System.out.print("Exception encountered: ");
			System.out.println(e.getMessage());
			System.exit(-1);
		}

	}

	private static void searchAndExtractData() throws IOException {
		// SearchData
		String fileName = "", companyName = "", content = "", searchKey = "loss";
		IndexSearchOptions options = new IndexSearchOptions();
		options.setIncludeCount(true);
		IndexSearchResult result = indexClient.search(searchKey, options);
		System.out.printf("Found %s hits\n", result.getCount());
		for (IndexSearchResult.SearchHit hit : result.getHits()) {
			fileName = hit.getDocument().get("filename").toString();
			companyName = hit.getDocument().get("companyname").toString();
			content = hit.getDocument().get("content").toString();
			// ExtractData
			String dataExtract = DataExtractUtils.extractSearchInfo(content);
			System.out.println("data extract from search --> " + dataExtract.trim());
			String cashExtract = DataExtractUtils.extractCashInfo(dataExtract);
			System.out.println("Cash Extract from data--->" + cashExtract);

			System.out.println(
					"data to csv ---> \n SearchKey = " + searchKey + "\n Value = " + cashExtract + "\n Statement = "
							+ dataExtract + "\n CompanyName = " + companyName + "\n DocumentName = " + fileName);
		}
		// Ends here
	}
}
