package client;

import java.rmi.RemoteException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String args[]) throws RemoteException {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("../gui/Connect.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Connessione");
		primaryStage.setScene(scene);
		primaryStage.show();	
	}
}