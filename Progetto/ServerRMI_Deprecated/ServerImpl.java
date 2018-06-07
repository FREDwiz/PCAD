import java.rmi.*;
import java.rmi.server.*;
import Extensions.*;

public class ServerImpl extends UnicastRemoteObject implements ServerOp {
	int nCalls = 0;
	
	public ServerImpl() throws RemoteException {
	}

	public String frob(ClientOp o) throws RemoteException {
	
		int localCalls;
		ClientOp ci = (ClientOp)o;
		String request = ci.getString();
		String selfName = Thread.currentThread().getName();
		String reply;
		
		synchronized (this) {
			localCalls = nCalls++;
		}
		
		if (Server.DEBUG) {
			System.out.println("Server[" + selfName + "]\t Starting:  '" + request + "'");
		}
		
		InterruptibleThread.sleep(Server.serverDelay);
		
		reply = "[Server" + selfName + "] Reply: " + localCalls + " to: " + request;
		
		if (Server.DEBUG) {
			System.out.println("Server[" + selfName + "]\t Processed: '" + reply + "'");
		}
		
		if ((localCalls%100) == 0) {
			System.out.println("Server[" + selfName + "]\t Processed: " + localCalls + " requests.");
		}
		
		return(reply);
	}
		
		public String getName() throws RemoteException {
			return("<ServerImpl: " + nCalls +">");
	}
}