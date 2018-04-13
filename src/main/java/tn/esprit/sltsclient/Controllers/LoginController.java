/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.sltsclient.Controllers;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.swing.text.Element;
import javax.swing.text.html.ImageView;

import org.controlsfx.control.Notifications;
import org.jboss.sasl.util.UsernamePasswordHashUtil;
import java.util.logging.Level;
import com.jfoenix.controls.JFXButton;
import java.util.logging.Logger;
import dorkbox.notify.Notify;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import sun.misc.BASE64Encoder;
import tn.esprit.SLTS_server.persistence.Admin;
import tn.esprit.SLTS_server.persistence.Customer;
import tn.esprit.SLTS_server.persistence.Trader;
import tn.esprit.SLTS_server.persistence.User;
import tn.esprit.SLTS_server.services.UserServiceRemote;
import tn.esprit.sltsclient.Utils.FadeInRightTransition;
import tn.esprit.sltsclient.Utils.FadeOutLeftTransition;
import tn.esprit.sltsclient.Utils.Navigation;
import tn.esprit.sltsclient.Utils.exit;
import tn.esprit.sltsclient.Utils.mouseDrag;
import javafx.scene.effect.*;
/**
 * FXML Controller class
 *
 * @author Fatma Jaafar
 */
public class LoginController implements Initializable {

	/**
	 * Initializes the controller class.
	 */
	Navigation nav = new Navigation();
	@FXML
	private AnchorPane effectFade;

    @FXML
    private AnchorPane blur;
	@FXML
	private Label exit;

	@FXML
	private Button login;

	@FXML
	private TextField username;

	@FXML
	private PasswordField password;

	@FXML
	private JFXButton forgotpassword;

	@FXML
	private StackPane stpane;

	@FXML
	private Group groups;

	@FXML
	private AnchorPane formPane;

	@FXML
	private JFXButton close;

	@FXML
	private void handleExitClicked() {
		exit ex = new exit();
		ex.exitClicked();
	}

	@FXML
	private void setHover(javafx.scene.input.MouseEvent event) {
		exit.setStyle("-fx-background-color: red;");
	}

	@FXML
	private void setDefault(javafx.scene.input.MouseEvent event) {
		exit.setStyle("-fx-background-color:  #4183D7;");
	}
	 private String encryptLdapPassword(String algorithm, String password) {
	        String sEncrypted = password;
	        if ((password != null) && (password.length() > 0)) {
	            boolean bMD5 = algorithm.equalsIgnoreCase("MD5");
	            boolean bSHA = algorithm.equalsIgnoreCase("SHA")
	                    || algorithm.equalsIgnoreCase("SHA1")
	                    || algorithm.equalsIgnoreCase("SHA-1");
	            if (bSHA || bMD5) {
	                String sAlgorithm = "MD5";
	                if (bSHA) {
	                    sAlgorithm = "SHA";
	                }
	                try {
	                    MessageDigest md = MessageDigest.getInstance(sAlgorithm);
	                    md.update(password.getBytes("UTF-8"));
	                    sEncrypted = "{" + sAlgorithm + "}" + (new BASE64Encoder()).encode(md.digest());
	                } catch (Exception e) {
	                    sEncrypted = null;
	                   
	                }
	            }
	        }
	        return sEncrypted;
	    }
	public boolean loginfromLDAP(String passw,String username) throws NamingException
	{
		
		Properties initialProperties = new Properties();
		initialProperties.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		initialProperties.put(Context.PROVIDER_URL, "ldap://localhost:10389");
		initialProperties.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
		initialProperties.put(Context.SECURITY_CREDENTIALS, "secret");
		DirContext  context = new InitialDirContext(initialProperties);
		
		String searchFilter="(objectClass=inetOrgPerson)";
		String[] requiredAttributes={"sn","cn","employeeNumber" , "userPassword" };
		SearchControls controls=new SearchControls();
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		controls.setReturningAttributes(requiredAttributes);
		NamingEnumeration users=context.search("ou=users,o=itradeit", searchFilter, controls);
		SearchResult searchResult=null;
		String commonName=null;
		String surName=null;
		String employeeNum=null;
		String userp=null;
		while(users.hasMore())
		{
			
			searchResult=(SearchResult) users.next();
			Attributes attr=searchResult.getAttributes();
			
			commonName=attr.get("cn").get(0).toString();
			/**surName=attr.get("sn").get(0).toString();***/

			/***employeeNum=attr.get("employeeNumber").get(0).toString();***/
			/***System.out.println("Name = "+commonName);
			System.out.println("Surname  = "+surName);
			System.out.println("Employee number = "+employeeNum);*****/
			Attribute pwd = attr.get("userPassword");
	        String pass= new String((byte[])pwd.get());
	       /**** System.out.println("=> userPassword : " + pass);
			System.out.println("---------------------------"+attr.get("userpassword").get(0).toString()+"---------------");****/
			String passwsha=encryptLdapPassword("SHA",passw);
			if (pass.equals(passwsha) && commonName.equals(username)){
				
				return true;
			}
			
		}
		return false;
	}

	@FXML
	public void HandleLogin(MouseEvent event) throws IOException, NamingException {

		String usernametext = username.getText();
		String passwordtext = password.getText();
		if (usernametext.equals("") || passwordtext.equals("")) {
			nav.showAlert(Alert.AlertType.WARNING, "Warning", null, "Please fill all the inputs!!");
			username.requestFocus();
		} else {
			String jndiName = "SLTS_server-ear/SLTS_server-ejb/UserService!tn.esprit.SLTS_server.services.UserServiceRemote";
			Context context = new InitialContext();
			UserServiceRemote service = (UserServiceRemote) context.lookup(jndiName);
			User us = new User();
			us.setPassword(encryptLdapPassword("SHA",passwordtext));
			us.setLogin(usernametext);
			User user = service.login(us);
			/***System.out.println(user);*////

			if (user == null || (!loginfromLDAP(passwordtext, usernametext))) {
				/**	if (user == null){**/
						/**System.out.println("not found");**/
			
		
				System.out.println("not found");

				nav.showAlert(Alert.AlertType.ERROR, "Error", null, "Username or password incorrect");

			} else {
				HomeController.idcurrentuser=user.getId();
				if (user instanceof Admin) {
					
					
					HomeController.role="Admin";
					
				}
				else if (user instanceof Trader){
				
					HomeController.role="Trader";
				}
				else if (user instanceof Customer){
					
					HomeController.role="Customer";
				}
				Notifications.create().title("ITradeIt").text("Welcome again ! " + usernametext).darkStyle()
				.showInformation();
		Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage2.hide();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(nav.getHome()));
		try {
			loader.load();
		} catch (Exception e) {
			Logger.getLogger(LoginController.class.getName()).log(Level.
					  SEVERE, null, e);
		}

		HomeController hc = loader.getController();
	
		Parent p = loader.getRoot();
		Stage stage = new Stage();
		Scene pp = new Scene(p);
		pp.setFill(javafx.scene.paint.Color.TRANSPARENT);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setScene(pp);
		stage.setTitle("Home");
		stage.getIcons().add(nav.applicationIcon);
		mouseDrag md = new mouseDrag();
		md.setDragged(p, stage);
		stage.show();
			}
		}

	}

	@FXML
	void forgotpasswordgo(ActionEvent event) {
		
		  blur.setEffect(new GaussianBlur(10)); new
		  FadeInRightTransition(stpane).play(); AnchorPane pane = null; try {
		  pane = FXMLLoader.load(getClass().getResource(nav.getForgetPassword()));
		  } catch (IOException ex) {
		  Logger.getLogger(LoginController.class.getName()).log(Level.
		  SEVERE, null, ex); } formPane.getChildren().setAll(pane);
}

	@FXML
	void closeClicked(ActionEvent event) {
		blur.setEffect(null);
		new FadeOutLeftTransition(stpane).play();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
//do something
		
	}

}
