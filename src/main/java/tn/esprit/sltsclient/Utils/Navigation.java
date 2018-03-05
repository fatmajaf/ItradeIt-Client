package tn.esprit.sltsclient.Utils;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Navigation {
	private final String test = "/tn/esprit/sltsclient/Views/Main.fxml";
	private final String login = "/tn/esprit/sltsclient/Views/Login.fxml";
	private final String home = "/tn/esprit/sltsclient/Views/Home.fxml";
	private final String menu = "/tn/esprit/sltsclient/Views/Menu.fxml";
	private final String usermanagement = "/tn/esprit/sltsclient/Views/Usermanagement.fxml";
	private final String profile = "/tn/esprit/sltsclient/Views/profile.fxml";
	private final String homemmanagement = "/tn/esprit/sltsclient/Views/HomeManagement.fxml";
	private final String addtrader = "/tn/esprit/sltsclient/Views/AddTrader.fxml";
	private final String forgetPassword = "/tn/esprit/sltsclient/Views/forgotpassword.fxml";
	private final String addcustomer = "/tn/esprit/sltsclient/Views/Addcustomer.fxml";
	public Image applicationIcon = new Image(getClass().getResourceAsStream("/tn/esprit/sltsclient/Images/logo.png"));

	public String getLogin() {
		return login;
	}
    public String getAddcustomer() {
		return addcustomer;
	}
	public String getTest() {
		return test;
	}

	public String getHome() {
		return home;
	}
public String getAddtrader() {
	return addtrader;
}
	public String getMenu() {
		return menu;
	}
public String getHomemmanagement() {
	return homemmanagement;
}
	public String getUsermanagement() {
		return usermanagement;
	}

	public String getForgetPassword() {
		return forgetPassword;
	}

	public String getProfile() {
		return profile;
	}

	public void showAlert(Alert.AlertType type, String title, String header, String text) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(text);

		alert.showAndWait();
	}

	public void animationFade(Node e) {
		FadeTransition x = new FadeTransition(new Duration(1000), e);
		x.setFromValue(0);
		x.setToValue(100);
		x.setCycleCount(1);
		x.setInterpolator(Interpolator.LINEAR);
		x.play();
	}

	public boolean numbersCheck(final TextField text) {
		if (text.getText().matches("[0-9]*")) {
			return true;
		}
		text.setOnKeyReleased(new EventHandler<javafx.scene.input.KeyEvent>() {
			@Override
			public void handle(javafx.scene.input.KeyEvent event) {
				if (!text.getText().matches("[0-9]*")) {
					showAlert(Alert.AlertType.WARNING, "Warning", null, " input supports numbers only!!");

					text.setText("");
					text.requestFocus();

				}

			}

		});
		return false;
	}

	

}
