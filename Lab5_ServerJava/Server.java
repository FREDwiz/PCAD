/*
*	Server
*	Il Server usa ServerSocket sulla porta 9000 per accettare connessioni TCP da client e un ThreadPool
*	con 5 thread per gestire ogni richiesta di un client attraverso un socket dedicato.
*	Il server mantiene in una lista in memoria gli user-id di utenti registrati.
*	Utilizzare un timer (o un timeout sul accept) per chiudere  il SeverSocket e stampare la lista di utenti registrati.
*/

/*

if (IDs.contains(uuid)) {
    System.out.println("uuid found");
} else {
    System.out.println("uuid not found");
}


List<String> IDs = new ArrayList<String>();
IDs.add(uuid);

*/

import java.io.*;
import java.net.*;

class TCPServer {
 public static void main(String argv[]) throws Exception {
  String uuid;
  String capitalizedUUID;
  ServerSocket welcomeSocket = new ServerSocket(9000);

  List<String> IDs = new ArrayList<String>();

	if (IDs.contains(uuid)) {
	    System.out.println("Connection refused! You're already connected");
	} else {
		Socket connectionSocket = welcomeSocket.accept();
   		BufferedReader inFromClient =
    		new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
   		DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
   		uuid = inFromClient.readLine();
   		System.out.println("Welcome " + uuid);
   		capitalizedUUID = uuid.toUpperCase() + '\n';
   		outToClient.writeBytes(capitalizedUUID);
	}
 }
}




