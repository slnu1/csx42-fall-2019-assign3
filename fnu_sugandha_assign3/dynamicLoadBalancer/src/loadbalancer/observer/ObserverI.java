package loadbalancer.observer;

import loadbalancer.util.OpData;
import loadbalancer.util.Operations;

public interface ObserverI {
	public void update(Operations op, OpData data);
}
