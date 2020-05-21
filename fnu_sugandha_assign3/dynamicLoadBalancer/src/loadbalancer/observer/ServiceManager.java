package loadbalancer.observer;

import java.util.ArrayList;
import java.util.List;

import loadbalancer.subject.SubjectI;
import loadbalancer.util.OpData;
import loadbalancer.util.Operations;

public class ServiceManager implements ObserverI {
	private String key;
	// Information pertaining to the service.
	private String url;
	private List<String> hostNames;
	private SubjectI cluster;
	private int index;
	
	public ServiceManager(SubjectI clusterIn, String serviceNameIn,String urlIn) {
		setCluster(clusterIn);
		cluster.registerObserver(this);
		key = serviceNameIn;
		url = urlIn;
		hostNames = new ArrayList<String>();
		index = 0;
	}
	
	private void setCluster(SubjectI clusterIn) {
		cluster = clusterIn;	
	}

	public String getKey() {
		return key;
	}
	
	public void setKey(String keyIn) {
		key = keyIn;
	}
	
	public List<String> getHostnames() {
		return hostNames;
	}
	
	public void setHostnames(List<String> hostNamesIn) {
		hostNames = hostNamesIn;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getCurrentHost() {
		
		String currentHost = hostNames.get(index);
		if(index < (hostNames.size()-1)) {
			index++;
		}else {
			index = 0;
		}
		return currentHost;
		
	}

	@Override
	public void update(Operations opIn, OpData dataIn) {
		if (opIn == Operations.CLUSTER_OP__SCALE_DOWN) {
			hostNames.remove(dataIn.getHostnames());
		}else if (opIn == Operations.SERVICE_OP__ADD_INSTANCE) {
			if(key.equals(dataIn.getServicename())) {
				hostNames.add(dataIn.getHostnames().get(0));
			}
		}else if(opIn == Operations.SERVICE_OP__REMOVE_INSTANCE) {
			if(key.equals(dataIn.getServicename())) {
				hostNames.remove(dataIn.getHostnames());
			}
		}
	}
}
