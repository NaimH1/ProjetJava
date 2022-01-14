/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import java.net.URL;

import client.network.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author clément
 */
public class MainViewScene extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		String address = this.getParameters().getRaw().get(0);
		int port = Integer.parseInt(this.getParameters().getRaw().get(1));

		// ClientPanel clientPanel = new ClientPanel();
		Client client = new Client(address, port);
		// clientPanel.setClient(client);

		URL location = getClass().getResource("IHMBuilder.fxml");
		// URL location = getClass().getResource("TestFXML.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader(location);
		Pane root = (Pane) fxmlLoader.load();
		IHMBuilderControler controller = fxmlLoader.getController();
		controller.setClient(client);
		client.setViewControler(controller);

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		Application.launch(MainViewScene.class, args);
	}
}
