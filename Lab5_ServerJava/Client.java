/*
*   Client
*   I client si collegano al server nviando una richiesta di registrazione con il proprio user-id (stringa).
*   Se lo user-id compare già nella lista il server risponde al client con "Fail".
*   Altrimenti aggiunge lo user-id alla lista e risponde con "Ok".
*/


import java.io.*;
import java.net.*;
import java.util.UUID;

class TCPClient {
 public static void main(String argv[]) throws Exception {

    String uuid = UUID.randomUUID().toString();
    outToServer.writeBytes(uuid + '\n');

      String response;
      //BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
      Socket clientSocket = new Socket("localhost", 9000);
      DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
      BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      
      response = inFromServer.readLine();
      System.out.println("FROM SERVER: " + response);
      //clientSocket.close();
 }
}