package core;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.Serializable;

public interface ClientInt extends Remote,Serializable {
	public void notifyClient() throws RemoteException;
}
