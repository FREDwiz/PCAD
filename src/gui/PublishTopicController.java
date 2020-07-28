package gui;

import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;

import client.PCADclient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class PublishTopicController {

	private PCADclient client;

	final private ConcurrentHashMap<String,TextArea> chatPanel = new ConcurrentHashMap<>();

	@FXML
	private AnchorPane panel2;

	@FXML
	private TextField topicField;

	@FXML
	private Button sendButton;

	@FXML
	private Button addTopicButton;

	@FXML
	private Label loggedUser;

	@FXML
	private TextField msgbox;

	@FXML
	private ListView<String> topicList;

	@FXML
	private Label checkTopicIsContained;

	@FXML
	private StackPane stackPane;

	@FXML
	private Label currentTopicLabel;

	@FXML
	private Label noSelectedTopicLabel;

	public void initClient(PCADclient client) {
		this.client = client;
	}

	public void initializeLoggedUserLabel(String username) {
		loggedUser.setText("Ciao, " + username);
	}

	@FXML
	public void initializeTopicLabel() {
		checkTopicIsContained.setVisible(false);
	}

	@FXML
	public void initializeNoSelectedTopicLabel() {
		noSelectedTopicLabel.setVisible(false);
	}

	@FXML 
	public void resetTopicLabel(MouseEvent event) {
		topicField.clear();
		checkTopicIsContained.setVisible(false);
	}

	@FXML
	public void initializeCurrentTopicLabel() {
		currentTopicLabel.setText("");
		//currentTopicLabel.setVisible(false);
	}
	@FXML
	void resetNoSelectedTopicLabel(ActionEvent event) {
		msgbox.clear();
		noSelectedTopicLabel.setVisible(false);
	}

	@FXML
	public void addTopic(ActionEvent event) throws RemoteException {
		if(!topicList.getItems().contains(topicField.getText()) && !topicField.getText().equals("")) {
			client.addTopic(topicField.getText().toLowerCase());
			topicList.getItems().add((topicField.getText()).toLowerCase());
			chatPanel.computeIfAbsent(topicField.getText().toLowerCase(), k->
			{
				TextArea textArea = new TextArea();
				textArea.setPrefSize(200,246);
				textArea.setWrapText(true);
				stackPane.getChildren().add(textArea);
				return textArea;
			});

			topicField.clear();
		}
		else {
			checkTopicIsContained.setTextFill(Color.web("#DF0101"));
			checkTopicIsContained.setText("Topic is empty or already present.");
			checkTopicIsContained.setVisible(true);
		}
	}

	@FXML
	void selectTopic(MouseEvent event) {
		String topicname = topicList.getSelectionModel().getSelectedItem();
		if(topicList.getItems().size()>0 && topicname!= "" && topicname != null)
		{
			currentTopicLabel.setText("");
			currentTopicLabel.setVisible(true);
			currentTopicLabel.setText("Topic selezionato: " + topicname);
			chatPanel.computeIfPresent(topicname,(k,v) -> {
				v.toFront();
				return v;
			});
		}
		else {}
	}

	@FXML
	void sendMessage(ActionEvent event) throws RemoteException {
		String message = msgbox.getText();
		String currentTopic = topicList.getSelectionModel().getSelectedItem();
		String username = client.getName();

		if(topicList.getItems().isEmpty() || topicList.getSelectionModel().isEmpty())
		{
			noSelectedTopicLabel.setTextFill(Color.web("#DF0101"));
			noSelectedTopicLabel.setText("Devi prima selezionare un topic.");
			noSelectedTopicLabel.setVisible(true);
		}
		else {
			if(msgbox.getText().length()!=0) {
				resetNoSelectedTopicLabel(event);
				client.getServer().publish(message,currentTopic,username);
			}
		}
	}

	public void appendMessage(String message, String username, String currentTopic) throws RemoteException {
		if(message.length()!=0)
			chatPanel.get(currentTopic).appendText("<" + username + "> " + message + "\n");
	}
}