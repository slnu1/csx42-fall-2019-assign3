package loadbalancer.observer;

public class Trie {
	private TrieNode root;
	
	public Trie() {
		root = null;
	}
	
	public void insert(String key,ServiceManager value) {
		
		//TrieNode current = root;
		if(key == null || key.isEmpty() || value == null) {
			return;
		}
		
		if(root == null) {
			root = new TrieNode();
		}
		
		TrieNode current = root;
	    for(int i = 0; i < key.length(); i++) {
	    	if(!current.getChild().containsKey(key.charAt(i))) {
	    		TrieNode temp = new TrieNode();
	    		current.getChild().put(key.charAt(i), temp);
	    		current = temp;
	    	}else {
	    		current = current.getChild().get(key.charAt(i));
	    	}
	    }
	    current.setWord(true);
	    current.setServiceManager(value);
	}
	
	public ServiceManager find(String key) {
		
		if(key == null || key.isEmpty()) {
			return null;
		}
		
		TrieNode current = root;
		
		for(int i = 0; i < key.length(); i++) {
			if(current.getChild().containsKey(key.charAt(i))) {
				current = current.getChild().get(key.charAt(i));
			}else {
				return null;
			}
		}
		if(current.isWord()) {
			return current.getServiceManager();
		}else {
			return null;
		}
	}
	
//	public boolean delete(String key) {
//		
//		if(key.isEmpty()|| key.equals(null)) {
//			return false;
//		}
//		
//		TrieNode current = root;
//		
//		for(int i = 0; i < key.length(); i++) {
//			if(current.getChild().containsKey(key.charAt(i))) {
//				if(current.getChild().size() > 1) {
//					current = current.getChild().get(key.charAt(i));
//				}else {
//					//delete do
//					current = current.getChild().get(key.charAt(i));
//				}
//				
//			}else {
//				return false;
//			}
//		}
//		
//		if(current.isWord()) {
//			current.setWord(false);
//			current.setServiceManager(null);
//			return true;
//		}else {
//			return false;
//		}
//	}
	
	public boolean deleteNodes(String key,TrieNode currentNode,int length,int level) {
		
		boolean isDeleted = false;
		
		if(length == level) {
			if(currentNode.getChild().size() > 0) {
				isDeleted = false;
				currentNode.setWord(false);
				currentNode.setServiceManager(null);
			}else {
				isDeleted = true;
				currentNode.setWord(false);
				currentNode.setServiceManager(null);	
			}
			
		}else {
			TrieNode child = currentNode.getChild().get(key.charAt(level));
			boolean deleteChild = deleteNodes(key,child,length,level+1);
			
			if(deleteChild) {
				
				currentNode.getChild().remove(key.charAt(level));
				
				if(currentNode.isWord()) {
					isDeleted = false;
				}else if(currentNode.getChild().size() > 0) {
					isDeleted = false;
				}else {
					isDeleted = true;
				}
				
			}else {
				isDeleted = false;
			}
		}
		return isDeleted;	
	}
	
	public void delete(String key) {
		
		if(key == null || root == null || key.isEmpty()) {
			return;
		}
		
		deleteNodes(key,root,key.length(),0);
		
	}
} 
