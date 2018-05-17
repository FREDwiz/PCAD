package core;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import core.ServerInt;
import core.ClientInt;

public class UniServer extends UnicastRemoteObject implements ServerInt {
  private static final long serialVersionUID = 1L;
  
  public UniServer() throws RemoteException {
	  super();
	  System.out.println("ciao");
  }  
  
  public synchronized void request(String x,ClientInt stub) throws RemoteException {
	  System.out.println("Request da "+x);
	  stub.notifyClient();
  }
  
  public static void main(String args[]) {
		try {
			System.setProperty("java.security.policy","file:./sec.policy");
			if(System.getSecurityManager() == null) System.setSecurityManager(new SecurityManager());
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			UniServer server = (UniServer) new UniServer();
			System.out.println("Istanza UniServer Creato");
			Naming.rebind("rmi://localhost/Server", server);
			System.out.println("UniServer in ascolto all'URL rmi://localhost/Server");
		    
		} 
		catch (Exception e) {
			System.out.println(e);
		}
	 }
  
}