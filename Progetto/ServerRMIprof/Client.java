package core;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import core.ServerInt;
import core.ClientInt;

public class Client implements ClientInt {
   
	private static final long serialVersionUID = 1L;
    private ClientInt stub;
    private ServerInt server;
   
	public Client(String x) {
		try {
		System.setProperty("java.security.policy","file:./sec.policy");
		System.setProperty("java.rmi.server.codebase","file:${workspace_loc}/Client/");
		if (System.getSecurityManager() == null) System.setSecurityManager(new SecurityManager());
		System.setProperty("java.rmi.server.hostname","localhost");
		//Registry r = LocateRegistry.getRegistry("localhost",8000);
		Registry r = LocateRegistry.getRegistry(8000);
		server = (ServerInt) r.lookup("REG");
		stub = (ClientInt) UnicastRemoteObject.exportObject(this,0);
		server.request(x,stub);
		
		} catch (RemoteException | NotBoundException e) {
		e.printStackTrace();
		}
	}
		
	public void notifyClient() throws RemoteException {
		System.out.println("hand-shake ok!");
	}
	
	public static void main(String args[]) {
		int x = (int) (Math.random() * 1000);
		String s = Integer.toString(x);
		System.out.println("Client "+s);
		new Client(s);
	  }
	
}

