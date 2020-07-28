package client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import gui.PublishTopicController;
import shared.InterfaceClient;
import shared.InterfaceServer;

public class PCADclient extends UnicastRemoteObject implements InterfaceClient {

	private String name;
	private static final long serialVersionUID = 1L;
	private InterfaceServer server;
	private PublishTopicController gui;
	private String address;

	public PCADclient (String name, String address) throws RemoteException, MalformedURLException {
		try {
			this.name=name;
			this.setAddress(address);
			Registry registry = LocateRegistry.getRegistry(address,8080);
			this.server = (InterfaceServer) registry.lookup("server");
		}

		catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void notifyClient() {
		System.out.println("hand-shake ok!");
	}

	@Override
	public void addTopic(String topicname) throws RemoteException {
		try {
			server.addTopic(topicname,this);	
		}
		catch(RemoteException e) {e.printStackTrace();}
	}

	@Override
	public void notify(String msg, String username, String topicname) throws RemoteException {
		gui.appendMessage(msg, username, topicname);
	}

	public boolean connect2server(String username, InterfaceClient c) throws RemoteException {
		return this.server.connect2server(username,c);	
	}

	//********** METODI GETTER **********//

	@Override
	public String getName() throws RemoteException {
		return name;
	}

	public InterfaceServer getServer() throws RemoteException {
		return server;
	}

	public String getAddress() {
		return address;
	}
	//********** METODI SETTER **********//

	public void setGui(PublishTopicController gui) {
		this.gui=gui;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}