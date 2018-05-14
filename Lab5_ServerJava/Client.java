/*
*   Client
*   I client si collegano al server inviando una richiesta di registrazione con il proprio user-id (stringa).	X
*   Se lo user-id compare già nella lista il server risponde al client con "Fail".								X
*   Altrimenti aggiunge lo user-id alla lista																	X
*	e risponde con "Ok".																						X
*/


import java.io.*;
import java.net.*;
import java.util.UUID;

class TCPClient {
	public static void main(String argv[]) throws Exception {

		String uuid = UUID.randomUUID().toString();

		String response;

		Socket clientSocket = new Socket("localhost", 9000);

		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		outToServer.writeBytes(uuid + '\n');

		response = inFromServer.readLine();

		if(response.equals("CONNECTED")) { System.out.println(response + ": Connection accepted"); }
		else{ System.out.println(response + ": Connection refused"); }

		clientSocket.close();
	}
}