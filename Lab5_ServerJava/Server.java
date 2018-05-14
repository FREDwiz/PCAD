/*
*	Server
*	Il Server usa ServerSocket sulla porta 9000 per accettare connessioni TCP da client 						X
*	e un ThreadPool con 5 thread per gestire ogni richiesta di un client attraverso un socket dedicato.			TODO
*	Il server mantiene in una lista in memoria gli user-id di utenti registrati.								X
*	Utilizzare un timer (o un timeout sul accept) per chiudere  il SeverSocket 									X
*	e stampare la lista di utenti registrati.																	X
*/

import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;
import java.util.concurrent.*; 

class MyThread extends Thread {

	public MyThread (String s) { 
		super(s); 
	}
}


class TCPServer {
	public static void main(String argv[]) throws Exception {
		
		final int PORT = 9000;
		final int TIMEOUT = 60000;
		final int numberOfThreads = 5;

		String uuid;
		ServerSocket welcomeSocket = new ServerSocket(PORT);
		
		ExecutorService tp = Executors.newFixedThreadPool(numberOfThreads);

		System.out.println("TCPServer Listening on port " + PORT);
		
		List<String> IDs = new ArrayList<String>();

		while (true) {

			System.out.println("Waiting for new connections: Timeout in " + TIMEOUT/1000 + " seconds");

			welcomeSocket.setSoTimeout(TIMEOUT);
		   
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient =
				new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

			uuid = inFromClient.readLine();
			if(!IDs.contains(uuid)){
				
				outToClient.writeBytes("CONNECTED");
				

				IDs.add(uuid);

				System.out.println("Guess who joined:\n" + uuid);

				for(int i=0; i<numberOfThreads; i++){
					
					//int n = (int)(Math.random()*1000);

					MyThread temp = new MyThread("Thread #" + i);
        			System.out.println("Started Thread:" + i);
        			temp.start();
        			temp.sleep(5000);
        			System.out.println("Waiting for " + i + " milliseconds on thread #" + i);
				}

				tp.shutdown();

				System.out.println("\nOther joiners:");

				for (int i = 0; i < IDs.size(); i++) { System.out.println(IDs.get(i)); }

				outToClient.close();
					
			}
			else{ 
				
				System.out.println(uuid + " Already connected");
				outToClient.writeBytes("FAIL");
				outToClient.close();
			}
		}
	}
}