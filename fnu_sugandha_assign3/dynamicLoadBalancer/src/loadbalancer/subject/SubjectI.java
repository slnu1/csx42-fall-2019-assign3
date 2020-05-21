package loadbalancer.subject;

import loadbalancer.observer.ObserverI;
import loadbalancer.util.OpData;
import loadbalancer.util.Operations;

public interface SubjectI {
	public void registerObserver(ObserverI o);
	public void removeObserver(ObserverI o);
	public void notifyObservers(Operations optypein, OpData data);
}
