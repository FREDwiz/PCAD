package core;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.Serializable;

public interface ServerInt extends Remote,Serializable {
	public void request(String x,ClientInt stub) throws RemoteException;
}
