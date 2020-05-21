package loadbalancer.observer;

import java.util.HashMap;
import java.util.Map;

import loadbalancer.subject.SubjectI;
import loadbalancer.util.OpData;
import loadbalancer.util.Operations;
import loadbalancer.util.Results;

public class LoadBalancer implements ObserverI {
	//private Trie ServiceURLAndHostnameFetcher;
	private Trie trieTest;
	private SubjectI cluster;
	public Results results;

	public LoadBalancer(SubjectI clusterIn,Results resultsIn) {
		setCluster(clusterIn);
		clusterIn.registerObserver(this);
		trieTest = new Trie();
		results = resultsIn;
	}
	
	public Trie getServiceURLAndHostnameFetcher() {
		return trieTest;
	}
	
	public void setServiceURLAndHostnameFetcher(Trie serviceURLAndHostnameFetcher) {
		trieTest = serviceURLAndHostnameFetcher;
	}

	public SubjectI getCluster() {
		return cluster;
	}

	public void setCluster(SubjectI clusterIn) {
		cluster = clusterIn;
	}
	
	public void opRequest(String serviceNameIn) {
		if(trieTest.find(serviceNameIn) != null) {
			if(trieTest.find(serviceNameIn).getHostnames().isEmpty()) {
				//no instances left on the cluster for the service,service is inactive
				results.addResult("Service Inactive - Service::"+serviceNameIn +"\n");
			}else {
				String currentHostName = trieTest.find(serviceNameIn).getCurrentHost();
				results.addResult("Processed Request - Service_URL::"+trieTest.find(serviceNameIn).getUrl() +" Host::"+currentHostName+"\n");
			}
		}else {
			results.addResult("Invalid Service"+"\n");
		}
	}

	@Override
	public void update(Operations opIn, OpData dataIn) {
		// TODO Auto-generated method stub
		if (opIn == Operations.SERVICE_OP__ADD_SERVICE) {
			trieTest.insert(dataIn.getServicename(),dataIn.getServiceManager());
		}else if(opIn == Operations.SERVICE_OP__REMOVE_SERVICE) {
			trieTest.delete(dataIn.getServicename());
		}
	}
}
