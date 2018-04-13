package tn.esprit.sltsclient.Controllers;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AdviceCallableController {
	@FXML
	private JFXButton closeButtonAdviceId;

	@FXML
	void closeAdvice(ActionEvent event) {
		Stage stage = (Stage) closeButtonAdviceId.getScene().getWindow();
		stage.close();
	}
	
	
}
