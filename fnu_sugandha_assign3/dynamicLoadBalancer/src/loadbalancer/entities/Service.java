package loadbalancer.entities;

import loadbalancer.observer.ServiceManager;

public class Service {
	// Service URL.
	private String url;
	// Service name.
	private String serviceName;
	private ServiceManager serviceManager;
	
	public Service(String urlIn,String nameIn,ServiceManager serviceManagerIn) {
		url = urlIn;
		serviceName = nameIn;
		serviceManager = serviceManagerIn;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String urlIn) {
		url = urlIn;
	}

	public ServiceManager getServicemanager() {
		return serviceManager;
	}

	public void setServicemanager(ServiceManager serviceManagerIn) {
		serviceManager = serviceManagerIn;
	}

	public String getServicename() {
		return serviceName;
	}

	public void setServicename(String serviceNameIn) {
		serviceName = serviceNameIn;
	}
}
