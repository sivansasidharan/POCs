package com.invixo.socialmedia.rexecute;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import rcaller.RCaller;
import rcaller.RCode;

public class RcallerExample {

	public static void main(String[] args) {
		FileInputStream in = null;
		try {

			RCaller caller = new RCaller();
			caller.setRscriptExecutable("C:\\Program Files\\R\\R-3.0.2\\bin\\x64\\Rscript.exe");
			caller.cleanRCode();
			RCode code = new RCode();
			code.clear();
			in = new FileInputStream("C:\\RCaller\\input\\SampleScript.r");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			/*
			 * code.addRCode("s1 <- \"R-\""); code.addRCode("s2 <- \"project\""
			 * ); code.addRCode("paste(s1, s2, sep = \"\")");
			 * caller.setRCode(code); caller.runOnly();
			 * caller.runAndReturnResult(
			 * "C:\\RCaller\\output\\myOut.csv"
			 * );
			 */
			System.out.println("-----------Reading Script file-------------");
			while ((strLine = br.readLine()) != null) {
				System.out.println(strLine);
				code.addRCode(strLine);
				caller.setRCode(code);
				caller.runOnly();
			}
			System.out.println("------------Script Executed Succesfully------------");

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
