/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.sltsclient.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyEvent;
import java.math.RoundingMode;
import java.net.URL;
import java.security.MessageDigest;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.SLTS_server.persistence.Address;
import tn.esprit.SLTS_server.persistence.Trader;
import tn.esprit.SLTS_server.persistence.User;
import tn.esprit.SLTS_server.services.UserServiceRemote;
import tn.esprit.sltsclient.Utils.Mail;
import tn.esprit.sltsclient.Utils.MailConstruction;
import tn.esprit.sltsclient.main.ListCountry;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import sun.misc.BASE64Encoder;
import webservicefatma.GlobalWeatherDelegate;

/**
 * FXML Controller class
 *
 * @author AGORA
 */
public class AddTraderController implements Initializable {

	@FXML
	private ProgressBar progressPersonal;

	@FXML
	private Label lblComplete, erremailmsg;

	@FXML
	private JFXButton btnClear;

	@FXML
	private JFXButton btnSave;

	@FXML
	private JFXDatePicker arrivalTime;
	@FXML
	private JFXComboBox<String> countries, cities;
	@FXML
	private JFXTextField fname;

	@FXML
	private JFXTextField lname;

	@FXML
	private JFXTextField email;
	@FXML
	private JFXTextField phonenumber;
	@FXML
	private JFXTextField street;

	@FXML
	private JFXTextField zipcode;

	@FXML
	private JFXTextField nationality;

	@FXML
	private JFXDatePicker bdate;

	@FXML
	private JFXTextField login;

	@FXML
	private JFXTextField password;

	@FXML
	private JFXRadioButton level1;

	@FXML
	private JFXRadioButton level3;

	@FXML
	private JFXRadioButton level2;
	private static double progress1 = 0;
	private static double progress2 = 0;
	private static double progress3 = 0;
	private static double progress4 = 0;
	private static double progress5 = 0;
	private static double progress6 = 0;
	private static double progress7 = 0;
	private static double progress8 = 0;
	private static double progress9 = 0;
	private static double progress10 = 0;
	private static double progress11 = 0;
	private static double progress12 = 0;
	private static double progress13 = 0;

	String jndiName = "SLTS_server-ear/SLTS_server-ejb/UserService!tn.esprit.SLTS_server.services.UserServiceRemote";
	UserServiceRemote service;
	Context context;

	String leveltrader = "";

	@FXML
	void clearFields(ActionEvent event) {

	}

	public void fillcountries() {
		ObservableList<String> combocountries = FXCollections.observableArrayList();

		String[] locales = Locale.getISOCountries();

		for (String countryCode : locales) {

			Locale obj = new Locale("", countryCode);

			System.out.println("Country Code = " + obj.getCountry() + ", Country Name = " + obj.getDisplayCountry());
			combocountries.add(obj.getDisplayCountry(Locale.ENGLISH));
			countries.setItems(combocountries);
			countries.setValue(combocountries.get(0));

		}

		System.out.println("Done :DDD");
	}

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		System.out.println("-----------------------------------------------------------------------------------------------------"
				+ HomeController.idcurrentuser);
		fillcountries();
		ToggleGroup group = new ToggleGroup();
		level1.setToggleGroup(group);
		level2.setToggleGroup(group);
		level3.setToggleGroup(group);
		updateProgress();
		inputvalidation();
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (group.getSelectedToggle() != null) {

					RadioButton button1 = (RadioButton) group.getSelectedToggle();
					System.out.println("Button: " + button1.getText());
					leveltrader = button1.getText();

				}
			}
		});

	}

	public static boolean isValid(String email) {
		if (email != null && email.trim().length() > 0)
			return email.matches("^[a-zA-Z0-9\\.\\-\\_]+@([a-zA-Z0-9\\-\\_\\.]+\\.)+([a-zA-Z]{2,4})$");
		return false;
	}

	@FXML
	public void emailval(KeyEvent event) {
		System.out.println(email.getText());
		if (!isValid(email.getText())) {
			email.setFocusColor(Paint.valueOf("red"));
			erremailmsg.setTextFill(Paint.valueOf("red"));
			erremailmsg.setVisible(true);

		} else {
			email.setFocusColor(Paint.valueOf("black"));
			erremailmsg.setVisible(false);

		}

	}

	private void inputvalidation() {
		RequiredFieldValidator validator = new RequiredFieldValidator();
		validator.setMessage("Input Required");
		fname.getValidators().add(validator);
		fname.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
				fname.validate();

		});

		validator.setMessage("Input Required");
		lname.getValidators().add(validator);
		lname.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
				lname.validate();

		});

		validator.setMessage("Input Required");
		street.getValidators().add(validator);
		street.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
				street.validate();

		});

		NumberValidator validatornum = new NumberValidator();
		validatornum.setMessage("Input must be numeric");
		validator.setMessage("Input Required");
		phonenumber.getValidators().add(validatornum);
		phonenumber.getValidators().add(validator);
		phonenumber.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
				phonenumber.validate();

		});
		validatornum.setMessage("Input must be numeric");
		zipcode.getValidators().add(validatornum);

		zipcode.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
				zipcode.validate();

		});
		password.getValidators().add(validator);
		password.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
				password.validate();

		});
		login.getValidators().add(validator);
		login.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
				login.validate();

		});

	}

	@FXML
	void countrychoice(ActionEvent event) {
		System.out.println(countries.getValue());
		ObservableList<String> combocities = FXCollections.observableArrayList();
		GlobalWeatherDelegate gwd = new GlobalWeatherDelegate();
		List<String> citiesli = gwd.getCities(countries.getValue());
		for (String city : citiesli) {
			combocities.add(city);
		}
		cities.setItems(combocities);
		cities.setValue(combocities.get(0));
		nationality.setText(countries.getValue());
	}

	private void updateProgress() {
		DecimalFormat decimalFormat = new DecimalFormat("###.#");
		decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

		// progressPersonal.setProgress(0.00);
		double sum_progress = progress13 + progress12 + progress11 + progress10 + progress1 + progress2 + progress3
				+ progress4 + progress5 + progress6 + progress7 + progress8 + progress9;

		fname.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.isEmpty()) {
					progress1 = 0.05;

				} else {
					progress1 = 0.0;

				}

				double sum = (progress13 + progress12 + progress11 + progress10 + progress1 + progress2 + progress3
						+ progress4 + progress5 + progress6 + progress7 + progress8 + progress9);
				progressPersonal.setProgress(sum);
				lblComplete.setText(decimalFormat.format(sum * 100) + "% complete");
			}
		});

		lname.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.isEmpty()) {
					progress2 = 0.05;

				} else {
					progress2 = 0.0;

				}
				double sum = (progress13 + progress12 + progress11 + progress10 + progress1 + progress2 + progress3
						+ progress4 + progress5 + progress6 + progress7 + progress8 + progress9);
				progressPersonal.setProgress(sum);
				lblComplete.setText(decimalFormat.format(sum * 100) + "% complete");
			}
		});

		email.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.isEmpty()) {
					progress3 = 0.1;

				} else {
					progress3 = 0.0;

				}
				double sum = (progress13 + progress12 + progress11 + progress10 + progress1 + progress2 + progress3
						+ progress4 + progress5 + progress6 + progress7 + progress8 + progress9);
				progressPersonal.setProgress(sum);
				lblComplete.setText(decimalFormat.format(sum * 100) + "% complete");
			}
		});

		countries.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.length() > 1) {
					progress4 = 0.1;

				} else {
					progress4 = 0.0;

				}
				double sum = (progress13 + progress12 + progress11 + progress10 + progress1 + progress2 + progress3
						+ progress4 + progress5 + progress6 + progress7 + progress8 + progress9);
				progressPersonal.setProgress(sum);
				lblComplete.setText(decimalFormat.format(sum * 100) + "% complete");
			}
		});

		cities.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.isEmpty()) {
					progress5 = 0.1;

				} else {
					progress5 = 0.0;

				}
				double sum = (progress13 + progress12 + progress11 + progress10 + progress1 + progress2 + progress3
						+ progress4 + progress5 + progress6 + progress7 + progress8 + progress9);
				progressPersonal.setProgress(sum);
				lblComplete.setText(decimalFormat.format(sum * 100) + "% complete");
			}
		});
		// Course name
		street.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.isEmpty()) {
					progress6 = 0.05;

				} else {
					progress6 = 0.0;

				}
				double sum = (progress13 + progress12 + progress11 + progress10 + progress1 + progress2 + progress3
						+ progress4 + progress5 + progress6 + progress7 + progress8 + progress9);
				progressPersonal.setProgress(sum);
				lblComplete.setText(decimalFormat.format(sum * 100) + "% complete");
			}
		});

		zipcode.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.isEmpty()) {
					progress7 = 0.05;

				} else {
					progress7 = 0.0;

				}
				double sum = (progress13 + progress12 + progress11 + progress10 + progress1 + progress2 + progress3
						+ progress4 + progress5 + progress6 + progress7 + progress8 + progress9);
				progressPersonal.setProgress(sum);
				lblComplete.setText(decimalFormat.format(sum * 100) + "% complete");
			}
		});
		nationality.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.isEmpty()) {
					progress8 = 0.1;

				} else {
					progress8 = 0.0;

				}
				double sum = (progress13 + progress12 + progress11 + progress10 + progress1 + progress2 + progress3
						+ progress4 + progress5 + progress6 + progress7 + progress8 + progress9);
				progressPersonal.setProgress(sum);
				lblComplete.setText(decimalFormat.format(sum * 100) + "% complete");
			}
		});
		phonenumber.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.isEmpty()) {
					progress9 = 0.1;

				} else {
					progress9 = 0.0;

				}
				double sum = (progress13 + progress12 + progress11 + progress10 + progress1 + progress2 + progress3
						+ progress4 + progress5 + progress6 + progress7 + progress8 + progress9);
				progressPersonal.setProgress(sum);
				lblComplete.setText(decimalFormat.format(sum * 100) + "% complete");
			}
		});
		login.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.isEmpty()) {
					progress10 = 0.1;

				} else {
					progress10 = 0.0;

				}
				double sum = (progress13 + progress12 + progress11 + progress10 + progress1 + progress2 + progress3
						+ progress4 + progress5 + progress6 + progress7 + progress8 + progress9);
				progressPersonal.setProgress(sum);
				lblComplete.setText(decimalFormat.format(sum * 100) + "% complete");
			}
		});
		password.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.isEmpty()) {
					progress11 = 0.1;

				} else {
					progress11 = 0.0;

				}
				double sum = (progress13 + progress12 + progress11 + progress10 + progress1 + progress2 + progress3
						+ progress4 + progress5 + progress6 + progress7 + progress8 + progress9);
				progressPersonal.setProgress(sum);
				lblComplete.setText(decimalFormat.format(sum * 100) + "% complete");
			}
		});
		level1.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!oldValue) {
					progress12 = 0.1;

				}
				double sum = (progress13 + progress12 + progress11 + progress10 + progress1 + progress2 + progress3
						+ progress4 + progress5 + progress6 + progress7 + progress8 + progress9);
				progressPersonal.setProgress(sum);
				lblComplete.setText(decimalFormat.format(sum * 100) + "% complete");
			}
		});
		level2.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!oldValue) {
					progress12 = 0.1;

				}

				double sum = (progress13 + progress12 + progress11 + progress10 + progress1 + progress2 + progress3
						+ progress4 + progress5 + progress6 + progress7 + progress8 + progress9);
				progressPersonal.setProgress(sum);
				lblComplete.setText(decimalFormat.format(sum * 100) + "% complete");
			}
		});

		level3.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!oldValue) {
					progress13 = 0.1;

				}
				double sum = (progress13 + progress12 + progress11 + progress10 + progress1 + progress2 + progress3
						+ progress4 + progress5 + progress6 + progress7 + progress8 + progress9);
				progressPersonal.setProgress(sum);
				lblComplete.setText(decimalFormat.format(sum * 100) + "% complete");
			}
		});

	}

	@FXML
	void savetraderclicked(ActionEvent event) throws NamingException {
		Trader trader = new Trader();
		trader.setFirstName(fname.getText());
		trader.setLastName(lname.getText());
		trader.setBirthdate(Date.valueOf(bdate.getValue()));
		trader.setIsactive(0);
		trader.setEmail(email.getText());
		trader.setIsbanned(0);
		trader.setLogin(login.getText());
		trader.setCreationDate(new java.util.Date());
		trader.setNationality(nationality.getText());
		trader.setPassword(encryptLdapPassword("SHA", password.getText()));
		trader.setPhone(Integer.parseInt(phonenumber.getText()));
		trader.setTradertype(leveltrader);
		Address address = new Address();
		address.setSountry(countries.getValue());
		address.setState(cities.getValue());
		address.setStreet(street.getText());
		address.setZipcode(Integer.parseInt(zipcode.getText()));
		trader.setAddress(address);

		context = new InitialContext();
		service = (UserServiceRemote) context.lookup(jndiName);

		service.ajouterUser(trader);
		String emailadr= trader.getEmail();
        String recipient= emailadr;
         Mail mail = new Mail();
        mail.setMailAddressRecipient(recipient);
        mail.setPwd("a3outhouBellehminashaitanRajimBeslemmeh123***BESMELLEhYarab552");
        
        mail.setMailAddressSender("fatma.jaafar404@gmail.com");
        mail.setMailSubject("Confirmed Registration");
      
        String msg="Dear: "+trader.getFirstName()+" "+trader.getLastName()+"\nWelcome to ITrade It \n ITradeIt Team.";
       
        mail.setMailObject(msg);
    

        MailConstruction mc = new MailConstruction();
        mc.getMailProperties();


        mc.getMailMessage( mail);
        mc.SendMessage();

		/// ldp
		    User user= service.login(trader);
			Hashtable<String, String> ldapEnv = new Hashtable<>();
			ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			ldapEnv.put(Context.PROVIDER_URL, "ldap://localhost:10389");
			ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
			ldapEnv.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou= system");
			ldapEnv.put(Context.SECURITY_CREDENTIALS, "secret");
			DirContext context = new InitialDirContext(ldapEnv);

			Attributes attributes = new BasicAttributes();
			Attribute attribute = new BasicAttribute("objectClass");
			attribute.add("inetOrgPerson");
			attributes.put(attribute);
			Attribute sn = new BasicAttribute("sn");
			sn.add(lname.getText());
			Attribute cn = new BasicAttribute("cn");
			cn.add(login.getText());

			attributes.put(sn);
			attributes.put(cn);
			attributes.put("telephoneNumber", phonenumber.getText());

			attributes.put(new BasicAttribute("userpassword", encryptLdapPassword("SHA", password.getText())));
			
			context.createSubcontext("employeeNumber="+user.getId() +",ou=users,o=itradeit", attributes);

			
		
	}

	private String encryptLdapPassword(String algorithm, String password) {
		String sEncrypted = password;
		if ((password != null) && (password.length() > 0)) {
			boolean bMD5 = algorithm.equalsIgnoreCase("MD5");
			boolean bSHA = algorithm.equalsIgnoreCase("SHA") || algorithm.equalsIgnoreCase("SHA1")
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
}