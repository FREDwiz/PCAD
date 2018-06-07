import java.rmi.*;
import java.rmi.server.*;
import Extensions.*;

public class ClientImpl extends UnicastRemoteObject implements ClientOp {
	static int delay = 0;
	String message;
	
	public String getString() {
		return(message);
	}
	
	public ClientImpl() throws RemoteException {
		message = "No Message";
	}
	
	public ClientImpl(String msg) throws RemoteException {
		message = msg;
	}
}