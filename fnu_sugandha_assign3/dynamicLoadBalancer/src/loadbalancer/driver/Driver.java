package loadbalancer.driver;

import loadbalancer.observer.LoadBalancer;
import loadbalancer.observer.ServiceManager;
import loadbalancer.subject.Cluster;
import loadbalancer.util.FileProcessor;
import loadbalancer.util.Results;

public class Driver {
	
	public static void main(String[] args) {
		if (args.length != 2 || args[0].equals("${arg0}") || args[1].equals("${arg1}")) {
			System.err.println("Error: Incorrect number of arguments. Program accepts 2 argumnets.");
			System.exit(1);
		}
		
		Results re = new Results(args[1]);
		Cluster c = new Cluster(re);
		LoadBalancer lb = new LoadBalancer(c,re);
		FileProcessor fp = new FileProcessor(args[0]);
		fp.readInputFile(c,lb,re);
		re.createFile();
		re.displayResults();
		re.closeFile();
		re.displayStdoutResults();
	}
}
