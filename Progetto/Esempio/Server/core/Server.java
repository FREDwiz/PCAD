package core;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import core.ServerInt;
import core.ClientInt;

public class Server implements ServerInt {
  private static final long serialVersionUID = 1L;
  
  public Server() {
	  System.out.println("ciao");
  }
  
  public synchronized void request(String x,ClientInt stub) throws RemoteException {
	  System.out.println("Request da "+x);
	  stub.notifyClient();
  }
  
  public static void main(String args[]) {
		try {
			System.setProperty("java.security.policy","file:./sec.policy");
			System.setProperty("java.rmi.server.codebase","file:${workspace_loc}/Server/");
			if(System.getSecurityManager() == null) System.setSecurityManager(new SecurityManager());
			System.setProperty("java.rmi.server.hostname","localhost");
			Registry r = null;
			try {
				r = LocateRegistry.createRegistry(8000);
			} catch (RemoteException e) {
				r = LocateRegistry.getRegistry(8000);
			}
			System.out.println("Registro trovato");
			Server server = (Server) new Server();
			ServerInt stubRequest = (ServerInt) UnicastRemoteObject.exportObject(server,0);
			r.rebind("REG", stubRequest);
		    System.out.println("Tutto ok");
		    
		} 
		catch (Exception e) {
			System.out.println(e);
		}
	 }
  
}