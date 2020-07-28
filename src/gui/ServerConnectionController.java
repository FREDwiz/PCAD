package gui;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import server.PCADserver;
import shared.InterfaceServer;

public class ServerConnectionController {

	@FXML
	private AnchorPane pane;

	@FXML
	private Button startButton;

	@FXML
	private Label serverRunningLabel;

	@FXML
	private Label connectedServer;

	@FXML
	void startServer(MouseEvent event) throws InterruptedException {
			try {
				Registry registry = null;

				try {
					registry = LocateRegistry.createRegistry(0);
				} catch (RemoteException e) {
					registry = LocateRegistry.getRegistry(0);
				}

				System.out.println("Registro trovato");
				PCADserver server = new PCADserver();

				InterfaceServer stub = (InterfaceServer) UnicastRemoteObject.exportObject(server,0);
				InetAddress addr = InetAddress.getLocalHost();
				String ip = addr.getHostAddress();
				//int port = server.getLocalPort();

				System.out.println("Istanza server creata all'indirizzo " + ip + ":" /*+ port*/);
				registry.bind("server", stub);
				System.err.println("OK");
				Scanner scanner = new Scanner(System.in);
				System.out.println("Usare il server come principale (1) o collegarlo a un altro server? (2)");

				if(scanner.next().equals("2")) {
					System.out.print("Inserire indirizzo IP del server:\n");
					String ipAddress = scanner.next();
					System.out.print(ipAddress);
					scanner.close();
					
					try {
						InterfaceServer brokerServer = (InterfaceServer) Naming.lookup("//"+ipAddress+":"+"8080"+"/"+"server");
						System.out.println("\n" + "Istanza Broker creata" +"\nOK");
						connectedServer.setText("Istanza Broker creata con " +ipAddress+"\nOK");
						connectedServer.setVisible(true);
						server.setBroker(brokerServer);     
						brokerServer.setBroker(server);
						//Stage stage = (Stage) pane.getScene().getWindow();
						//stage.setIconified(true);
					} 
					catch (Exception  e) {e.printStackTrace();}
				}
				
				else {
					System.out.println("Server created at " + ip);
					startButton.setVisible(false);
					connectedServer.setText("IP: " + ip);
					connectedServer.setVisible(true);
				}

				//Stage stage = (Stage) pane.getScene().getWindow();
				//stage.setIconified(true);
			}

			catch (Exception e) {
				System.err.println("Server exception: " + e.toString());
				e.printStackTrace();
			}
	}
}