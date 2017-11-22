package com.invixo.socialmedia.rexecute;

import java.io.IOException;

/**
 * @author Sivan Sasidharan
 * 
 */
public class CallRScripts {
	public static void main(String[] args) {
		try {
			ProcessBuilder pb = new ProcessBuilder("./conf/callScripts.bat");
			pb.start();
			Thread.sleep(10000);

		} catch (IOException e) {
			System.out.println("Executing bat file Exception : " + e);
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Sleep time Exception : " + e);
			e.printStackTrace();
		}
	}
}
