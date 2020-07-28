package shared;

import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;

public class Topic {

	private InterfaceServer broker;
	private ConcurrentHashMap <String,InterfaceClient> userList = new ConcurrentHashMap<>(); 

	public void setBroker(InterfaceServer broker) {
		this.broker=broker;
	}

	public InterfaceServer getBroker() {
		return this.broker;
	}
	
	public void addUser(InterfaceClient client, String username) {
		userList.putIfAbsent(username, client);
	}

	public void publishToUser(String msg, String topicname, String username) throws RemoteException {
		userList.values().forEach(c -> {
			try {
				c.notify(msg,username,topicname);
			} catch(RemoteException e) {e.printStackTrace();}});
		
		if(broker!=null)
			broker.publishToBroker(msg, topicname, username);
	}

	public void publishToBrokerServer(String msg, String topicname, String username) throws RemoteException {
		userList.values().forEach(c -> {
			try {
				c.notify(msg,username,topicname);
			} catch(RemoteException e ) {e.printStackTrace();}});
	}
}
