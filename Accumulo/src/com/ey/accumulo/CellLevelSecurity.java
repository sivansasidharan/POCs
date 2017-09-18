package com.ey.accumulo;

import java.io.IOException;
import java.util.Map.Entry;

import org.apache.accumulo.core.client.AccumuloException;
import org.apache.accumulo.core.client.AccumuloSecurityException;
import org.apache.accumulo.core.client.BatchWriter;
import org.apache.accumulo.core.client.Connector;
import org.apache.accumulo.core.client.MutationsRejectedException;
import org.apache.accumulo.core.client.Scanner;
import org.apache.accumulo.core.client.TableExistsException;
import org.apache.accumulo.core.client.TableNotFoundException;
import org.apache.accumulo.core.client.ZooKeeperInstance;
import org.apache.accumulo.core.data.Key;
import org.apache.accumulo.core.data.Mutation;
import org.apache.accumulo.core.data.Range;
import org.apache.accumulo.core.data.Value;
import org.apache.accumulo.core.security.Authorizations;
import org.apache.accumulo.core.security.ColumnVisibility;
import org.apache.hadoop.io.Text;
import org.apache.log4j.Logger;

public class CellLevelSecurity {

	private static String table;
	private static final Logger log = Logger.getLogger(RowOperations.class);

	private static Connector connector;
	// private static String table = "accumulotable";
	private static BatchWriter bw;
	// default value
	private static String securityLevelsWhichNeedsToAccess;

	public static void main(String[] args) throws AccumuloException,
			AccumuloSecurityException, TableExistsException,
			TableNotFoundException, MutationsRejectedException {
		if (args.length != 6) {
			log.error("Usage: <instance name> <zoo keepers> <username> <password> <accumulotablename> <securitylevel>");
			return;
		}

		// First the setup work
		connector = new ZooKeeperInstance(args[0], args[1]).getConnector(
				args[2], args[3].getBytes());
		table = args[4];
		securityLevelsWhichNeedsToAccess = args[5];
		String[] arrayAuthorizations = securityLevelsWhichNeedsToAccess
				.split(",");
		// listOfAccessQualifiers = new
		// ArrayList<String>(Arrays.asList(splitString));
		// lets create an example table
		connector.tableOperations().create(table);

		System.out.println("Table created successfully...!!");
		// lets create 3 rows of information
		Text row1 = new Text("row1");
		Text row2 = new Text("row2");

		Mutation mut1 = new Mutation(row1);
		Mutation mut2 = new Mutation(row2);

		Text colFam_name = new Text("Name");
		Text colQual_name = new Text("Identifier");
		ColumnVisibility colVis_name = new ColumnVisibility("public");
		// Now we'll add them to the mutations
		mut1.put(colFam_name, colQual_name, colVis_name,
				new Value("Amal".getBytes()));

		Text colFam_pass = new Text("Password");
		Text colQual_pass = new Text("Secret");
		ColumnVisibility colVis_pass = new ColumnVisibility("private");
		// Now we'll add them to the mutations
		mut2.put(colFam_pass, colQual_pass, colVis_pass,
				new Value("amal@123".getBytes()));

		// Now we'll make a Batch Writer
		bw = connector.createBatchWriter(table, 100000l, 30l, 1);

		// And add the mutations
		bw.addMutation(mut1);
		bw.addMutation(mut2);

		// Force a send
		bw.flush();

		// Now lets look at the rows
		Scanner rowOne = getRow(new Text("row1"), arrayAuthorizations);
		Scanner rowTwo = getRow(new Text("row2"), arrayAuthorizations);

		// And print them
		log.info("This is everything");
		printRow(rowOne);
		printRow(rowTwo);
		System.out.flush();
		bw.close();

		// and lets clean up our mess
		connector.tableOperations().delete(table);

	}

	/**
	 * Just a generic print function given an iterator. Not necessarily just for
	 * printing a single row
	 * 
	 * @param scanner
	 */
	private static void printRow(Scanner scanner) {
		// iterates through and prints
		for (Entry<Key, Value> entry : scanner)
			log.info("Key: " + entry.getKey().toString() + " Value: "
					+ entry.getValue().toString());
	}

	/**
	 * Gets a scanner over one row
	 * 
	 * @param row
	 * @return
	 * @throws TableNotFoundException
	 * @throws AccumuloSecurityException
	 * @throws AccumuloException
	 * @throws IOException
	 */
	private static Scanner getRow(Text row, String[] authorizations)
			throws AccumuloException, AccumuloSecurityException,
			TableNotFoundException {
		// Create a scanner
		Authorizations auth = new Authorizations(authorizations);

		Scanner scanner = connector.createScanner(table, auth);
		// Say start key is the one with key of row
		// and end key is the one that immediately follows the row
		scanner.setRange(new Range(row));
		return scanner;
	}

}
