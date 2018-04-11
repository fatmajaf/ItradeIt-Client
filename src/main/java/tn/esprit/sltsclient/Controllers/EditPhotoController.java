/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.sltsclient.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.image.Image;
import imageresolver.MainImageResolver;
import javafx.animation.PauseTransition;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javafx.util.Duration;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.bcel.generic.ATHROW;
import org.apache.commons.text.RandomStringGenerator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import tn.esprit.SLTS_server.persistence.Address;
import tn.esprit.SLTS_server.persistence.User;
import tn.esprit.SLTS_server.services.CommentServiceRemote;
import tn.esprit.SLTS_server.services.UserServiceRemote;
import tn.esprit.sltsclient.Utils.FadeInTransition;
import tn.esprit.sltsclient.Utils.Navigation;
import webservicefatma.GlobalWeatherDelegate;

import com.jfoenix.controls.JFXComboBox;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import java.awt.image.BufferedImage;
import javafx.scene.image.WritableImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.layout.AnchorPane;
import com.jfoenix.controls.JFXSpinner;

/**
 * FXML Controller class
 *
 * @author AGORA
 */
public class EditPhotoController implements Initializable {
	String pathimgtoadd="";
	File selectedfile;
	@FXML
	private JFXButton insertphoto;
	@FXML
	private ImageView photoshow;
	@FXML
	private AnchorPane rootpane;
	@FXML
	private JFXSpinner loggingProgress;
	@FXML
	private JFXButton btnClear;
	@FXML
	private JFXTextField urltext;

	@FXML
	private ImageView urlgo;

	/**
	 * Initializes the controller class.
	 */
	String jndiName = "SLTS_server-ear/SLTS_server-ejb/UserService!tn.esprit.SLTS_server.services.UserServiceRemote";
	UserServiceRemote service;
	Context context;
	@FXML
	private JFXButton editB;

	@FXML
	private Label ide;

	Navigation nav = new Navigation();
	int id;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		loggingProgress.setVisible(false);
		id = HomeController.idcurrentuser;
		ide.setText(String.valueOf(id));
		try {
			context = new InitialContext();
		} catch (NamingException e1) {

			e1.printStackTrace();
		}
		try {
			service = (UserServiceRemote) context.lookup(jndiName);
		} catch (NamingException e1) {

			Logger.getLogger(ProfileUserController.class.getName()).log(Level.SEVERE, null, e1);
		}

	}

	public void setData(String idJ, String country, String cityy, String zipcodee, String streett) {
		ide.setText(idJ);

	}
	private void setNode(Node node) {
		rootpane.getChildren().clear();
		rootpane.setOpacity(0);
		new FadeInTransition(rootpane).play();
		rootpane.getChildren().clear();
		rootpane.getChildren().add((Node) node);
	}
	@FXML
	void clearFields(ActionEvent event) throws IOException {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Discard");
		alert.setHeaderText("Discard changes");
		alert.setContentText("Do you really want to discard these changes?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			AnchorPane profile = FXMLLoader.load(getClass().getResource(nav.getProfileuser()));
	        
		     
	         setNode(profile);
		} 
		}
	

	@FXML
	void showphoto(ActionEvent event) {

		System.out.println("heyyyy");
		
		// String path_img = null;
		FileChooser fc = new FileChooser();

		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image", "*.jpg", "*.png"));

		selectedfile = fc.showOpenDialog(null);

		if (selectedfile != null) {

			loggingProgress.setVisible(true);
			@SuppressWarnings("restriction")
			PauseTransition pauseTransition = new PauseTransition();
			pauseTransition.setDuration(Duration.seconds(5));
			pauseTransition.setOnFinished(ev -> {
				System.out.println("Complte one");
				loggingProgress.setVisible(false);
				BufferedImage bufferedImage = null;
				try {
					bufferedImage = ImageIO.read(selectedfile);
				} catch (IOException ex) {

				}

				String path_img = selectedfile.getName();
				pathimgtoadd = "C:/wamp64/www/ImagesItradeit/" + ide.getText() + ".png";
				System.out.println(path_img);
				WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);

				photoshow.setImage(image);
				photoshow.setFitHeight(1198);
				photoshow.setFitWidth(301);
				photoshow.setPreserveRatio(true);

		/*		File target = new File("C:\\wamp64\\www\\ImagesItradeit\\" + ide.getText() + selectedfile.getName());
				try {
					Files.copy(selectedfile.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}**/

			});
			pauseTransition.play();

		} else {
			System.out.println("FICHIER erroné");
		}

	}

	@FXML
	void urlgoclicked(MouseEvent event) {
		System.out.println("ide" + ide.getText());
		String urlu = urltext.getText();
		Optional<String> mainImage = MainImageResolver.resolveMainImage(urlu);

		String imageUrl = mainImage.get();

		loggingProgress.setVisible(true);
		@SuppressWarnings("restriction")
		PauseTransition pauseTransition = new PauseTransition();
		pauseTransition.setDuration(Duration.seconds(5));
		pauseTransition.setOnFinished(ev -> {
			InputStream inputStream = null;
			OutputStream outputStream = null;
			System.out.println("Complte one");
			loggingProgress.setVisible(false);
			try {
				URL url = new URL(imageUrl);
				inputStream = url.openStream();
				outputStream = new FileOutputStream("C:\\wamp64\\www\\1.jpg");
				pathimgtoadd = "C:/wamp64/www/ImagesItradeit/" + ide.getText() + ".jpg";
				byte[] buffer = new byte[2048];
				int length;

				while ((length = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, length);
				}

			} catch (MalformedURLException e) {
				System.out.println("MalformedURLException :- " + e.getMessage());

			} catch (FileNotFoundException e) {
				System.out.println("FileNotFoundException :- " + e.getMessage());

			} catch (IOException e) {
				System.out.println("IOException :- " + e.getMessage());

			} finally {
				try {

					inputStream.close();
					outputStream.close();

				} catch (IOException e) {
					System.out.println("Finally IOException :- " + e.getMessage());
				}
			}
			String localUrl = "";
			File file = new File("C:\\wamp64\\www\\1.jpg");
			try {
				localUrl = file.toURI().toURL().toString();
			} catch (MalformedURLException ex) {
				System.out.println("MalformedURLException :- " + ex.getMessage());
			}

			Image image = new Image(localUrl);
			photoshow.setImage(image);
			photoshow.setFitHeight(1198);
			photoshow.setFitWidth(301);
			photoshow.setPreserveRatio(true);
		});
		pauseTransition.play();

	}

	@FXML
	private void editClicked(ActionEvent event) throws NamingException {
	if (pathimgtoadd.equals("")){
		nav.showAlert(Alert.AlertType.ERROR, "Error", null, "Please choose an image");
	}
	else {
		File file = null ;
		if (urltext.getText().equals("")){
			File target = new File(pathimgtoadd);
			try {
				Files.copy(selectedfile.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		}
		else {
			File src= new File("C:\\wamp64\\www\\1.jpg");
	        File target = new File(pathimgtoadd);
	        try {
				Files.copy(src.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
}
		//Image image = new Image(localUrl);
		//photoshow.setImage(image);
		System.out.println("here :"+ide.getText()+" . path "+pathimgtoadd);
		service.updatephotouserbyid(id, pathimgtoadd);
		nav.showAlert(Alert.AlertType.INFORMATION, "Success", null, "Image updated");	
	}
	

	}

}
