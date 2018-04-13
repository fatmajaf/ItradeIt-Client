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
    	private final String gestion = "/tn/esprit/sltsclient/Views/Portfolio_managment.fxml";
    	private final String stat = "/tn/esprit/sltsclient/Views/stati.fxml";
    	private final String forum = "/tn/esprit/sltsclient/Views/liste.fxml";
	private final String reponse = "/tn/esprit/sltsclient/Views/reponse.fxml";
	private final String row = "/tn/esprit/sltsclient/Views/row.fxml";
    	private final String porfolio = "/tn/esprit/sltsclient/Views/add_portfolio.fxml";
	private final String delete = "/tn/esprit/sltsclient/Views/delete_portfolio.fxml";
	private final String test = "/tn/esprit/sltsclient/Views/Main.fxml";
	private final String login = "/tn/esprit/sltsclient/Views/Login.fxml";
	private final String home = "/tn/esprit/sltsclient/Views/Home.fxml";
	private final String menu = "/tn/esprit/sltsclient/Views/Menu.fxml";
	private final String menutrader = "/tn/esprit/sltsclient/Views/MenuTrader.fxml";
	private final String usermanagement = "/tn/esprit/sltsclient/Views/Usermanagement.fxml";
	private final String profile = "/tn/esprit/sltsclient/Views/profile.fxml";
	private final String profileuser = "/tn/esprit/sltsclient/Views/profileUser.fxml";
	private final String homemmanagement = "/tn/esprit/sltsclient/Views/HomeManagement.fxml";
	private final String addtrader = "/tn/esprit/sltsclient/Views/AddTrader.fxml";
	private final String forgetPassword = "/tn/esprit/sltsclient/Views/forgotpassword.fxml";
	private final String addcustomer = "/tn/esprit/sltsclient/Views/Addcustomer.fxml";
	private final String editaddress = "/tn/esprit/sltsclient/Views/editaddress.fxml";
	private final String editphoto = "/tn/esprit/sltsclient/Views/editphoto.fxml";
	private final String optionscalculation = "/tn/esprit/sltsclient/Views/optioncalculation.fxml";
	private final String optionsshares = "/tn/esprit/sltsclient/Views/SearchOptionsshares.fxml";
	//asma 
	private final String addinstrument = "/tn/esprit/sltsclient/Views/addinstrument.fxml";
	private final String instrumentman = "/tn/esprit/sltsclient/Views/instrumentmanagement.fxml";
	public Image applicationIcon = new Image(getClass().getResourceAsStream("/tn/esprit/sltsclient/Images/logo.png"));
    	public String getrow() {
		return row;
	}
    public String getStat() {
		return stat;
	}
	public String getReponse() {
		return reponse;
	}
	public String getForum() {
		return forum;
	}

	public String getTest() {
		return test;
	}

	public String getPorfolio() {
		return porfolio;
	}

	public String getDelete() {
		return delete;
	}

	public String getGestion() {
		return gestion;
	}

	public String getLogin() {
		return login;
	}
	public String getOptionsshares() {
		return optionsshares;
	}
	public String getOptionscalculation() {
		return optionscalculation;
	}
    public String getAddcustomer() {
		return addcustomer;
	}

public String getEditphoto() {
		return editphoto;
	}
public String getAddinstrument() {
	return addinstrument;
}
	public String getHome() {
		return home;
	}
	public String getEditaddress() {
		return editaddress;
	}
	public String getInstrumentman() {
		return instrumentman;
	}
	public String getProfileuser() {
		return profileuser;
	}
	public String getMenutrader() {
		return menutrader;
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
