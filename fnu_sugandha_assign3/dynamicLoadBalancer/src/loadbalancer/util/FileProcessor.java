package loadbalancer.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import loadbalancer.observer.LoadBalancer;
import loadbalancer.subject.Cluster;

public class FileProcessor {
	
	private String inputfilename;
	public FileProcessor(String inputfilenameIn){		
		inputfilename = inputfilenameIn;	
	}
	
	public void readInputFile(Cluster clusterIn,LoadBalancer lbIn,Results resultsIn) {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(inputfilename);
			br = new BufferedReader(fr);
			String temp = null;
			while((temp = br.readLine()) != null) {
				processOperations(temp,clusterIn,lbIn,resultsIn);
			}
		}catch(FileNotFoundException ex) {
			System.out.println("File not found exception for " + inputfilename + ex);
			System.exit(1);
		}catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}finally {
			try {
				br.close();
			}catch (IOException ex) {
				ex.printStackTrace();
				System.exit(1);
			}
		}	
	}
	
	public void processOperations(String operationIn,Cluster clusterIn,LoadBalancer lbIn,Results resultsIn) {
		
		String[] temp = operationIn.split(" ");
		switch (temp[0].trim()) {
			case "SERVICE_OP__ADD_SERVICE":
				if(temp.length != 4) {
					resultsIn.addResult("Please provide the operation with correct inputs");
					break;
				}
				List <String> hostsList = new ArrayList<String>();
				String[] hosts = temp[3].split(",");//{"h1","h2","h3"}
				hostsList = Arrays.asList(hosts);
				clusterIn.serviceOpAddService(temp[1].trim(), temp[2].trim(), hostsList);
				break;
			case "SERVICE_OP__REMOVE_SERVICE":
				if(temp.length != 2) {
					resultsIn.addResult("Please provide the operation with correct inputs");
					break;
				}
				clusterIn.serviceOpRemoveService(temp[1].trim());
				break;
			case "SERVICE_OP__ADD_INSTANCE":
				if(temp.length != 3) {
					resultsIn.addResult("Please provide the operation with correct inputs");
					break;
				}
				clusterIn.serviceOpAddInstance(temp[1].trim(), temp[2].trim());
				break;
			case "SERVICE_OP__REMOVE_INSTANCE":
				if(temp.length != 3) {
					resultsIn.addResult("Please provide the operation with correct inputs");
					break;
				}
				clusterIn.serviceOpRemoveInstance(temp[1].trim(), temp[2].trim());
				break;
			case "CLUSTER_OP__SCALE_UP":
				if(temp.length != 2){
					resultsIn.addResult("Please provide the operation with correct inputs");
					break;
				}
				clusterIn.clusterOpScaleUp(temp[1].trim());
				break;
			case "CLUSTER_OP__SCALE_DOWN":
				if(temp.length != 2) {
					resultsIn.addResult("Please provide the operation with correct inputs");
					break;
				}
				clusterIn.clusterOpScaleDown(temp[1].trim());
				break;
			case "REQUEST":
				if(temp.length != 2) {
					resultsIn.addResult("Please provide the operation with correct inputs");
					break;
				}
				lbIn.opRequest(temp[1].trim());
				break;	
			default:
				resultsIn.addResult("Please provide the correct operation");
				System.exit(1);
		} 
	}
}
