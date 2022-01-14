package client.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TestControler {

	@FXML
	public Button myButton;

	public void sendMessage(ActionEvent event) {
		System.out.println("toto");
	}

}
