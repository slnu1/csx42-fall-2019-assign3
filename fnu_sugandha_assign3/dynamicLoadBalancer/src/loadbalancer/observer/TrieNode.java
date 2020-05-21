package loadbalancer.observer;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {
	private Map<Character, TrieNode> child;
	private boolean isWord;
	private ServiceManager serviceManager;
	
	public TrieNode(){
		child = new HashMap<Character,TrieNode>();
		isWord = false;
		serviceManager = null;
	}
	
	public Map<Character, TrieNode> getChild() {
		return child;
	}
	
	public void setChild(Map<Character, TrieNode> child) {
		this.child = child;
	}
	
	public boolean isWord() {
		return isWord;
	}
	
	public void setWord(boolean isWord) {
		this.isWord = isWord;
	}
	
	public ServiceManager getServiceManager() {
		return serviceManager;
	}
	
	public void setServiceManager(ServiceManager serviceManager) {
		this.serviceManager = serviceManager;
	}
}
