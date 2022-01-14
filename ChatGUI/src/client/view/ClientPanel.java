/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import java.io.IOException;

import client.network.Client;
import common.Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;

/**
 *
 * @author remi
 */
public class ClientPanel extends Parent {

	private Client network;

	private ScrollPane scrollReceivedText;
	private static int receivedTextWidth = 400;

	private TextFlow receivedText;
	private TextArea textToSend;
//	private Text textMembers;
	// private TextArea connected;
	private Button sendBtn;
	private Button clearBtn;

	public ClientPanel() {

//		Label test = new Label("hello world");
//		this.getChildren().add(test);

		scrollReceivedText = new ScrollPane();
		receivedText = new TextFlow();
		textToSend = new TextArea();
//		textMembers = new Text();
		// connected = new TextArea();
		sendBtn = new Button();
		clearBtn = new Button();

		scrollReceivedText.setLayoutX(50);
		scrollReceivedText.setLayoutY(50);
		scrollReceivedText.setPrefHeight(275);
		scrollReceivedText.setPrefWidth(receivedTextWidth);
		scrollReceivedText.setFitToWidth(true);

		receivedText.setLayoutX(5);
		receivedText.setLayoutY(0);
		receivedText.setPrefWidth(receivedTextWidth - 20);
		scrollReceivedText.vvalueProperty().bind(receivedText.heightProperty());

		scrollReceivedText.setContent(receivedText);

		receivedText.setVisible(true);

		textToSend.setLayoutX(50);
		textToSend.setFont(new Font(28));
		textToSend.setLayoutY(350);
		textToSend.setPrefHeight(100);
		textToSend.setPrefWidth(400);

//		textMembers.setText("Connecté :");
//		textMembers.setLayoutX(470);
//		textMembers.setLayoutY(45);

//		connected.setText("toto");
//		connected.setEditable(false);
//		connected.setLayoutX(470);
//		connected.setLayoutY(50);
//		connected.setPrefHeight(275);
//		connected.setPrefWidth(100);

		sendBtn.setText("Envoyer");
		sendBtn.setLayoutX(470);
		sendBtn.setLayoutY(350);
		sendBtn.setPrefHeight(20);
		sendBtn.setPrefWidth(100);
		sendBtn.setVisible(true);

		sendBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/*
				 * try { network.sendMessage(textToSend.getText()); } catch (IOException ex) {
				 * Logger.getLogger(ClientPanel.class.getName()).log(Level.SEVERE, null, ex); }
				 */

				Message mess = new Message("Moi", textToSend.getText());

				printNewMessage(mess);

				try {
					network.sendMessage(textToSend.getText());
				} catch (IOException ex) {
					System.out.println(ex.getMessage());
				}
				textToSend.setText("");

			}
		});

		clearBtn.setText("Effacer");
		clearBtn.setLayoutX(470);
		clearBtn.setLayoutY(380);
		clearBtn.setPrefHeight(20);
		clearBtn.setPrefWidth(100);
		clearBtn.setVisible(true);

		clearBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				textToSend.setText("");
			}

		});

//		this.getChildren().add(connected);
//		this.getChildren().add(textMembers);
		this.getChildren().add(scrollReceivedText);
		this.getChildren().add(textToSend);
		this.getChildren().add(clearBtn);
		this.getChildren().add(sendBtn);

	}

	void setClient(Client client) {
		this.network = client;
	}

	public void printNewMessage(Message mess) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {

//				

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
