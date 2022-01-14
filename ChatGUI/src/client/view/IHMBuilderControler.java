package client.view;

import java.io.IOException;

import client.network.Client;
import common.Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.TextFlow;

public class IHMBuilderControler {

	@FXML
	public Button clearBtn;

	@FXML
	public Button sendBtn;

	@FXML
	public TextArea textToSend;

	@FXML
	public TextFlow receivedText;

	@FXML
	public Button Mybutton;

	@FXML
	public ScrollPane scrollReceivedText;

	private Client network;

	@FXML
	public void initialize() {
		scrollReceivedText.vvalueProperty().bind(receivedText.heightProperty());
		scrollReceivedText.setContent(receivedText);
	}

	public void sendMessage(ActionEvent event) {

		System.out.println("send message ");
		Message mess = new Message("Moi", textToSend.getText());

		printNewMessage(mess);

		try {
			network.sendMessage(textToSend.getText());
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		textToSend.setText("");
	}

	public void clear(ActionEvent event) {
		System.out.println("clearBtn");
		textToSend.setText("");
	}

	public void setClient(Client client) {
		this.network = client;
	}

	public void printNewMessage(Message mess) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				Label text = new Label("\n" + mess.toString());
				double value = receivedText.getPrefWidth() - 20;
				System.err.println("message : " + text.getText());
				text.setPrefWidth(value);
				text.setAlignment(Pos.CENTER_LEFT);

				receivedText.getChildren().add(text);

			}
		});
	}

}
