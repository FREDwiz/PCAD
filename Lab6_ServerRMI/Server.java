/*
*	Server
*	Il Server deve esportare un oggetto remoto con due metodi remoti
*/


import java.rmi.*;

public interface Server extends java.rmi.Remote{
	String sayHello() throws RemoteException;
}

public static void main(String[] args) {
	
}