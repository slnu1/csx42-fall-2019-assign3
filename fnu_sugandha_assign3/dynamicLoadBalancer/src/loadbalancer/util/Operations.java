package loadbalancer.util;

public enum Operations {
	SERVICE_OP__ADD_SERVICE,
	SERVICE_OP__REMOVE_SERVICE,
	SERVICE_OP__ADD_INSTANCE,
	SERVICE_OP__REMOVE_INSTANCE,
	CLUSTER_OP__SCALE_UP,
	CLUSTER_OP__SCALE_DOWN,
	REQUEST;
	
	public String toString() {
		switch(this){
        case SERVICE_OP__ADD_SERVICE :
            return "SERVICE_OP__ADD_SERVICE";
        case SERVICE_OP__REMOVE_SERVICE :
            return "SERVICE_OP__REMOVE_SERVICE";
        case SERVICE_OP__ADD_INSTANCE :
            return "SERVICE_OP__ADD_INSTANCE";
        case SERVICE_OP__REMOVE_INSTANCE :
            return "SERVICE_OP__REMOVE_INSTANCE";
        case CLUSTER_OP__SCALE_UP :
            return "CLUSTER_OP__SCALE_UP";
        case CLUSTER_OP__SCALE_DOWN :
            return "CLUSTER_OP__SCALE_DOWN";
        case REQUEST :
            return "REQUEST";
        }
        return null;
	}

}
