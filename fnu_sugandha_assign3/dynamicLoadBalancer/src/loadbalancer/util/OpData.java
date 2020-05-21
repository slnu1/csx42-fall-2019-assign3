package loadbalancer.util;

import java.util.List;

import loadbalancer.observer.ServiceManager;

public class OpData {
	private List<String> hostnames;
	private String servicename;
	private String url;
	private ServiceManager serviceManager;

	public List<String> getHostnames() {
		return hostnames;
	}

	public void setHostnames(List<String> hostnamesin) {
		hostnames = hostnamesin;
	}

	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicenamein) {
		servicename = servicenamein;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String urlin) {
		url = urlin;
	}

	public ServiceManager getServiceManager() {
		return serviceManager;
	}

	public void setServiceManager(ServiceManager serviceManagerin) {
		serviceManager = serviceManagerin;
	}
}
