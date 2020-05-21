package loadbalancer.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;

public class Results implements FileDisplayInterface, StdoutDisplayInterface {
	static String output;
	String filename;
	BufferedWriter bw;
	
	
	public Results(String filenamein) {
		output = "";
		filename = filenamein;
	}
	public void addResult(String messagein) {
		output = output + messagein;
	}
	
	@Override
	public void displayStdoutResults() {
		// TODO Auto-generated method stub
		System.out.println(output);
	}
	@Override
	public void displayResults() {
		// TODO Auto-generated method stub
		try {
			bw.write(output);
			//bw.newLine(); 
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	}
	
	public void createFile() {
		try {
			 File file = new File(filename);
	         if (!file.exists()) {
	            file.createNewFile();
	         } 
	         FileWriter fw = new FileWriter(file.getAbsoluteFile());
		     bw = new BufferedWriter(fw);
		} catch (IOException e) {
			 e.printStackTrace();
		}   	  
	}
	
	public void closeFile() {
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
