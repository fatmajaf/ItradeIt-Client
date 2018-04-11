/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.sltsclient.Controllers;

import static tn.esprit.sltsclient.Utils.Example.ACCOUNT_SID;
import static tn.esprit.sltsclient.Utils.Example.AUTH_TOKEN;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import tn.esprit.SLTS_server.persistence.User;
import tn.esprit.SLTS_server.services.UserServiceRemote;
import tn.esprit.sltsclient.Utils.Navigation;
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
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import sun.misc.BASE64Encoder;

/**
 * FXML Controller class
 *
 * @author AGORA
 */
public class ForgotpasswordController implements Initializable {
    Navigation nav = new Navigation();

	@FXML
	private JFXTextField phonenumber;
    @FXML
	private JFXButton sendpass;
	@FXML
	private JFXPasswordField newPass;
    @FXML
    
    
	void sendpassgo(ActionEvent event) throws NamingException {
    	
if (!(phonenumber.getText().matches("[0-9]*")) || newPass.getText().equals("")) {
			nav.showAlert(Alert.AlertType.WARNING, "Warning", null,
					"Error ,number phone is numeric and new password should be filled, please try again");

			phonenumber.setText("");
			phonenumber.requestFocus();

		}

		else {
            String jndiName = "SLTS_server-ear/SLTS_server-ejb/UserService!tn.esprit.SLTS_server.services.UserServiceRemote";
			Context context = new InitialContext();
			UserServiceRemote service = (UserServiceRemote) context.lookup(jndiName);
			User user;
            user = service.findUserByphoneNumber(Integer.parseInt(phonenumber.getText()));
            user.setPassword(newPass.getText());
            String passwsha=encryptLdapPassword("SHA",newPass.getText());
            user.setPassword(passwsha);
            service.updateUser(user);
		
			final String ACCOUNT_SID = "ACa29a477f51aca63ccb2516b9aac65f02";
			final String AUTH_TOKEN = "2dc43448e964e0bca807cbc03c7ded98";
			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

			/*Message.creator(new PhoneNumber("+216" + phonenumber.getText()), new PhoneNumber("+19123485349"),
							"Your login : " + user.getLogin() + " your password:  " + newPass.getText()).create();*/
			Message.creator(new PhoneNumber("+21624240915"), new PhoneNumber("+19123485349"),
					"Yo yo").create();
			changepassLDAP(passwsha,user.getLogin());
			
			nav.showAlert(Alert.AlertType.INFORMATION, "Success", null,
					"In few moments, you will receive a confirmation to change your password");

		}
	}
	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// default implementation ignored

	}
	private String encryptLdapPassword(String algorithm, String _password) {
        String sEncrypted = _password;
        if ((_password != null) && (_password.length() > 0)) {
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
                    md.update(_password.getBytes("UTF-8"));
                    sEncrypted = "{" + sAlgorithm + "}" + (new BASE64Encoder()).encode(md.digest());
                } catch (Exception e) {
                    sEncrypted = null;
                   
                }
            }
        }
        return sEncrypted;
    }
	
	
	
	public boolean changepassLDAP(String passw,String employeeNumber) throws NamingException
	{
		
		Properties initialProperties = new Properties();
		initialProperties.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		initialProperties.put(Context.PROVIDER_URL, "ldap://localhost:10389");
		initialProperties.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
		initialProperties.put(Context.SECURITY_CREDENTIALS, "secret");
		DirContext  context = new InitialDirContext(initialProperties);
		
		
		
		
		 ModificationItem[] mods = new ModificationItem[1];
        
         Attribute mod1 = new BasicAttribute("userpassword", passw);
        
         mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod1);

        context.modifyAttributes("employeeNumber=7"  + ",ou=users,o=itradeit", mods);

        
		return false;
	}
}
