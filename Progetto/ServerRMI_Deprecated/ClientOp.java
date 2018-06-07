import java.rmi.*;
// A remote interface for an object that supports the "call"
// operation.
public interface ClientOp extends Remote {
	public String getString() throws RemoteException;
}