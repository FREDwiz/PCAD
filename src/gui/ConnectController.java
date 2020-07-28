package gui;

import java.io.IOException;
import java.rmi.RemoteException;

import client.PCADclient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ConnectController {

	@FXML
	private AnchorPane pane_connect;

	@FXML
	private TextField username_field;

	@FXML
	private TextField ip_field;

	@FXML
	private Button button_connect;

	@FXML
	private Label notifyUsername;

	@FXML
	private Label noIpLabel;

	private static final String USERNAME_PATTERN = "^[a-zA-Z0-9]([._](?![._])|[a-zA-Z0-9]){2,18}[a-zA-Z0-9]$";

	private static final String IP_PATTERN = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])|(localhost)$";

	@FXML
	void connettimi(ActionEvent event) throws IOException {
		if(username_field.getText().matches(USERNAME_PATTERN)) {
			if(ip_field.getText().matches(IP_PATTERN)) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("PublishTopic.fxml"));
				Stage stage = new Stage();
				stage.setTitle("PCADBroker");
				Scene scene = new Scene(loader.load());
				stage.setScene(scene);

				PublishTopicController publishTopicController = loader.getController();

				PCADclient client = new PCADclient(username_field.getText(),ip_field.getText());

				if(client.connect2server(username_field.getText(),client)) {
					client.getServer().request(client.getName(), client);
					initialize(publishTopicController, client);
					client.setGui(publishTopicController);
					stage.show();
					((Node)event.getSource()).getScene().getWindow().hide();
				}

				else {
					username_field.clear();
					notifyUsername.setTextFill(Color.web("#DF0101"));
					notifyUsername.setText("username already exists.");
					notifyUsername.setVisible(true);
				}

			}

			else {
				ip_field.clear();
				noIpLabel.setTextFill(Color.web("#DF0101"));
				noIpLabel.setText("IP not valid");
				noIpLabel.setVisible(true);
			}
		}
		
		else {
			username_field.clear();
			notifyUsername.setTextFill(Color.web("#DF0101"));
			notifyUsername.setText("username not valid");
			notifyUsername.setVisible(true);

		}
	}

	private void initialize(PublishTopicController publishTopicController, PCADclient client) throws RemoteException {
		publishTopicController.initClient(client);
		publishTopicController.initializeLoggedUserLabel(username_field.getText());
		publishTopicController.initializeTopicLabel();
		publishTopicController.initializeCurrentTopicLabel();
		publishTopicController.initializeNoSelectedTopicLabel();
	}

	@FXML
	void resetNotifyUsernameLabel(MouseEvent event) {
		username_field.clear();
		notifyUsername.setVisible(false);
	}

	@FXML
	void resetNoIpLabel(MouseEvent event) {
		ip_field.clear();
		noIpLabel.setVisible(false);
	}
}