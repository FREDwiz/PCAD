import java.rmi.*;
// A remote interface for an object that supports the "call"
// operation.
public interface ServerOp extends Remote {
	
	public String getName() throws RemoteException;
	public String frob(ClientOp o) throws RemoteException;
}