package server;

import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;

import shared.InterfaceClient;
import shared.InterfaceServer;
import shared.Topic;

public class PCADserver implements InterfaceServer {

	public PCADserver() throws RemoteException {}

	private InterfaceServer broker;
	private ConcurrentHashMap<String,Topic> topicList = new ConcurrentHashMap<>(); 
	private ConcurrentHashMap<String,InterfaceClient> usersList = new ConcurrentHashMap<>(); 

	@Override
	public ConcurrentHashMap<String,InterfaceClient> getUserList() throws RemoteException {
		return this.usersList;
	}

	@Override
	public void setBroker(InterfaceServer brokerServer) throws RemoteException {
		this.broker=brokerServer;
	}

	@Override
	public ConcurrentHashMap<String,Topic> getTopicList() throws RemoteException {
		return this.topicList;
	}

	@Override
	public void addTopic(String topicName, InterfaceClient client) throws RemoteException {
		Topic t = new Topic();
		topicList.putIfAbsent(topicName, t);
		t.addUser(client, client.getName());
		this.subscribeTopic(client, topicName, client.getName());
	}

	@Override
	public boolean connect2server(String username, InterfaceClient c) {
		return usersList.putIfAbsent(username,c)==null;
	}

	@Override
	public void subscribeTopic(InterfaceClient client, String topic, String username) throws RemoteException {	
		System.out.println(client.getName() + "  got connected....");	
		topicList.computeIfPresent(topic,(k,v) -> {v.addUser(client, username); return v;});		
	}

	@Override
	public void publish(String msg, String topicname, String username) throws RemoteException {   
		Topic t = topicList.get(topicname);
		t.setBroker(broker);
		t.publishToUser(msg,topicname,username);
	}	

	@Override
	public void publishToBroker(String msg, String topicname, String username) throws RemoteException {
		Topic t = topicList.get(topicname);
		if(t!=null)
		t.publishToBrokerServer(msg,topicname,username);
	}
	
	@Override
	public synchronized void request(String x, InterfaceClient stub) throws RemoteException {
		System.out.println("Request from " + x);
		stub.notifyClient();
	}
}