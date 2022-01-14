/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import client.network.Client;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author remi
 */
public class MainView extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		String address = this.getParameters().getRaw().get(0);
		int port = Integer.parseInt(this.getParameters().getRaw().get(1));

		ClientPanel clientPanel = new ClientPanel();
		Client client = new Client(address, port);
		clientPanel.setClient(client);
		client.setView(clientPanel);

		Group root = new Group();
		root.getChildren().add(clientPanel);
		Scene scene = new Scene(root, 600, 500);
		stage.setTitle("my chat");
		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		Application.launch(MainView.class, args);
	}
}
