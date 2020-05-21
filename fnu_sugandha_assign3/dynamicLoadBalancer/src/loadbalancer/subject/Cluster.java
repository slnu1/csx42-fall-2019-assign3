package loadbalancer.subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loadbalancer.entities.Machine;
import loadbalancer.entities.Service;
import loadbalancer.observer.ObserverI;
import loadbalancer.observer.ServiceManager;
import loadbalancer.util.OpData;
import loadbalancer.util.Operations;
import loadbalancer.util.Results;

public class Cluster implements SubjectI {
	// Hostnames to corresponding machine instances.
	private Map<String, Machine> machines;
	//private Service service;
	private List<ObserverI> observers;
	public Results results;
	//public ServiceManager serviceManager; 
	
	public Cluster(Results resultsIn) {
		observers = new ArrayList<ObserverI>();
		machines = new HashMap<String, Machine>();
		results = resultsIn;
	}
	
	public Map<String, Machine> getMachines() {
		return machines;
	}
	
	public void setMachines(Map<String, Machine> machines) {
		this.machines = machines;
	}
	
	public void registerObserver(ObserverI o) {
		observers.add(o);
	}
	
	public void removeObserver(ObserverI o) {
		int i = observers.indexOf(o);
		if(i >= 0) {
			observers.remove(o);
		}
	}
	
	public void notifyObservers(Operations opTypeIn, OpData dataIn) {
		for(int i = 0; i < observers.size(); i++) {
			ObserverI observer = (ObserverI)observers.get(i);
			observer.update(opTypeIn, dataIn);
		}
	}
	
	public void clusterOpScaleUp(String hostNameIn) {
		if(machines.containsKey(hostNameIn)) {
			results.addResult("Hostname "+hostNameIn+ " already exist\n");
		}else {
			machines.put(hostNameIn, new Machine(hostNameIn));
			results.addResult("Cluster Scaled Up\n");
		}	
	}
	
	public void clusterOpScaleDown(String hostNameIn) {
		if(machines.containsKey(hostNameIn)) {
			machines.remove(hostNameIn);
			
			//notify service manager
			OpData data = new OpData();
			List<String> hostnames = new ArrayList<String>();
			hostnames.add(hostNameIn);
			data.setHostnames(hostnames);
			notifyObservers(Operations.CLUSTER_OP__SCALE_DOWN, data);
			
			results.addResult("Cluster Scaled Down\n");
		}else {
			results.addResult("Hostname "  +hostNameIn+" doesn't exist\n");
		}	
	}
	
	public void serviceOpAddService(String serviceNameIn,String urlIn,List<String> hostsIn) {
		
		boolean exists = false;
		if(machines.keySet().containsAll(hostsIn)) {
			for(int i = 0; i < hostsIn.size(); i++) {
				if(machines.get(hostsIn.get(i)).getHostedServices().containsKey(serviceNameIn)) {
					exists = true;
				}
			}
		}else {
			results.addResult("Machine does not exist for one of the given hostnames\n");
		} 
		
		
		if(!exists) {
			//all the hosts were added during cluster up
			ServiceManager serviceManager = new ServiceManager(this, serviceNameIn, urlIn);
			Service service = new Service(serviceNameIn,urlIn,serviceManager);
			serviceManager.getHostnames().addAll(hostsIn);//remove must be done in load balancer
			
			//notify load balancer
			OpData data = new OpData();
			data.setServicename(serviceNameIn);
			data.setServiceManager(serviceManager);
			notifyObservers(Operations.SERVICE_OP__ADD_SERVICE, data);
			
			for(int i = 0; i < hostsIn.size(); i++) {
				machines.get(hostsIn.get(i)).addHostedServices(serviceNameIn,service);
			}
			results.addResult("Service Added\n");	
		}else {
			results.addResult("Service "+serviceNameIn+" with the given name already exists on the cluster\n");
		}
		
	}
	
	public void serviceOpRemoveService(String serviceNameIn) {
		//check if the service exists
		boolean removed = false;
		for (Map.Entry<String, Machine> item : machines.entrySet()) {
		    Service service = item.getValue().getHostedServices().remove(serviceNameIn);
		    if(service != null) {
		    	//service.getServicemanager().getHostnames().remove(item.getKey());
		    	removed = true;
		    }  
		}
		if(removed) {
			results.addResult("Service Removed\n");
			//notify load balancer
			OpData data = new OpData();
			data.setServicename(serviceNameIn);
			notifyObservers(Operations.SERVICE_OP__REMOVE_SERVICE, data);
		}else {
			results.addResult("Service "+serviceNameIn+" name is invalid\n");
		}
	}
	
	public void serviceOpAddInstance(String serviceNameIn,String hostNameIn) {
		
		boolean exists = false;
		for (Map.Entry<String, Machine> item : machines.entrySet()) {
		    if(item.getValue().getHostedServices().containsKey(serviceNameIn)) {
		    	exists = true;
		    }  
		}
		
		Machine tempmachine = machines.get(hostNameIn);
		if (tempmachine == null) {
			results.addResult("No host exists to add the instance of service\n");
			return;
		}
		//check if the service exists
		if(exists) {
			// check if instance is already present for that service on the given host name
			if(tempmachine.getHostedServices().get(serviceNameIn) != null) {
				results.addResult("Instance "+hostNameIn+" of the service already exists on the machine with the given hostname\n");	
			}else {
				//notify service manager
				OpData data = new OpData();
				data.setServicename(serviceNameIn);
				List<String> hostname = new ArrayList<String>();
				hostname.add(hostNameIn);
				data.setHostnames(hostname);
				notifyObservers(Operations.SERVICE_OP__ADD_INSTANCE, data);
				results.addResult("Instance Added\n");
			}	
		}else {
			results.addResult("Service " +serviceNameIn+" was not previously added to the cluster using SERVICE_OP__ADD_SERVICE operation\n");
		}
	}
	
	public void serviceOpRemoveInstance(String serviceNameIn,String hostNameIn) {
		
		Service service = machines.get(hostNameIn).getHostedServices().remove(serviceNameIn);
		if(service != null) {
			//check if the service exists and remove the given host name from the list
			//if no instances are left then mark it as inactive
			//notify service manager
			OpData data = new OpData();
			data.setServicename(serviceNameIn);
			List<String> hostname = new ArrayList<String>();
			hostname.add(hostNameIn);
			data.setHostnames(hostname);
			notifyObservers(Operations.SERVICE_OP__REMOVE_INSTANCE, data);
			results.addResult("Instance Removed\n");
			
		}else {
			results.addResult("No instance of service is present on the machine with the given hostname\n");
		}
	}	
}
