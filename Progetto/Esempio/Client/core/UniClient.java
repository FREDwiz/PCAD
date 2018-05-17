package core;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import core.ServerInt;
import core.ClientInt;

public class UniClient implements ClientInt {
   
	private static final long serialVersionUID = 1L;
    private ClientInt stub;
    private ServerInt server;
   
	public UniClient(String x) throws MalformedURLException {
		try {
		System.setProperty("java.security.policy","file:./sec.policy");
		if (System.getSecurityManager() == null) System.setSecurityManager(new SecurityManager());
		LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
		server = (ServerInt)  Naming.lookup("rmi://localhost/Server");
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
		try { new UniClient(s); } catch(MalformedURLException e) {} finally {};
	  }
	
}

