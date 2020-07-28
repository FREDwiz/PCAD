package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;

public interface InterfaceServer extends Remote {
	public void addTopic(String topicName, InterfaceClient client) throws RemoteException;
	public void subscribeTopic(InterfaceClient client, String topic, String username) throws RemoteException;
	public void publish(String msg, String topicname, String username) throws RemoteException;
	public void request(String x, InterfaceClient stub) throws RemoteException;
	public boolean connect2server(String username, InterfaceClient c) throws RemoteException;
	public void setBroker(InterfaceServer broker) throws RemoteException;
	public ConcurrentHashMap<String,Topic> getTopicList() throws RemoteException;
	public ConcurrentHashMap<String, InterfaceClient> getUserList() throws RemoteException;
	void publishToBroker(String msg, String topicname, String username) throws RemoteException;


}
