package shared;

import java.rmi.*;

public interface InterfaceClient extends Remote{	
	public String getName() throws RemoteException;
	public void notify(String msg, String username, String topicname) throws RemoteException;
	void notifyClient() throws RemoteException;
	void addTopic(String topicname) throws RemoteException;
}