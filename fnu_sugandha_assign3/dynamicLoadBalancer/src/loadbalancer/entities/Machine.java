package loadbalancer.entities;

import java.util.HashMap;
import java.util.Map;

public class Machine {
	private String hostname;
	// Service name to hosted services.
	private Map<String, Service> hostedServices;
	
	
	public Machine(String hostnamein) {
		hostname = hostnamein;
		hostedServices = new HashMap<String, Service>();
	}

	public Map<String, Service> getHostedServices() {
		return hostedServices;
	}
	
	//public void setHostedServices(HashMap<String, Service> hostedServices) {
	public void setHostedServices(HashMap<String, Service> hostedServices) {
		this.hostedServices = hostedServices;
	} 

	
	public void addHostedServices(String servicenamein,Service servicein) {
		hostedServices.put(servicenamein, servicein);
	} 
	
	
}
