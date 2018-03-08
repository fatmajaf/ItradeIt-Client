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
import tn.esprit.SLTS_server.instrumentServices.InstrumentServiceRemote;
import tn.esprit.SLTS_server.persistence.Address;
import tn.esprit.SLTS_server.persistence.Bond;
import tn.esprit.SLTS_server.persistence.Customer;
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
public class addinstrumentController implements Initializable {


	@FXML
	private JFXComboBox<String> instrumentType;

	@FXML
	private JFXDatePicker maturityDate;

	@FXML
	private JFXDatePicker callabilityPeriod;

	@FXML
	private JFXTextField parValue;

	@FXML
	private JFXTextField couponRate;

	@FXML
	private JFXComboBox<String> currency;

	@FXML
	private Label addlabel1;
	@FXML
	private Label addlabel2;

	@FXML
	private JFXComboBox<String> instrumentIsserType;

	@FXML
	private JFXComboBox<Customer> instrumentIssuer;

	@FXML
	private JFXComboBox<String> instrumentBackround;

	@FXML
	private JFXRadioButton collableType;

	@FXML
	private JFXRadioButton convertibleType;

	@FXML
	private JFXTextField salePrice;
    @FXML
    private JFXButton btnSave;
 // combo box
 	ObservableList<String> options = FXCollections.observableArrayList("Corporation", "Investment Trust", "Domestic",
 			"Foreign Government");
 	// end combo box

 	// combo box
 	ObservableList<String> optionsTypeInstrument = FXCollections.observableArrayList("Bond", "Option", "Future");
 	// end combo box

 	// combo box
 	ObservableList<String> optionsDevise = FXCollections.observableArrayList("Dinar", "Euro", "Dollar");
 	// end combo box

 	// combo box
 	ObservableList<String> optionsInstrumentBackround = FXCollections.observableArrayList("Corporate", "Government",
 			"Asset-Backed Securities");
 	// end combo box

 	ObservableList<Customer> optionsCustomersIssuers = FXCollections.observableArrayList();

    @FXML
    void emailval(KeyEvent event) {

    }

    @FXML
    void saveinstrumentClicked(ActionEvent event) {
    	Bond bond = new Bond();

		bond.setBackroundType(instrumentBackround.getValue());
		bond.setCurrency(currency.getValue());
		bond.setInstrumentIssuerType(instrumentIsserType.getValue());

		Date maturitydate = java.sql.Date.valueOf(maturityDate.getValue());
		bond.setMaturitydate(maturitydate);
		bond.setCouponrate(Double.parseDouble(couponRate.getText()));
		bond.setParvalue(Double.parseDouble(parValue.getText()));
		bond.setSaleprice(Double.parseDouble(salePrice.getText()));
		bond.setAvailablecoupon(Float.parseFloat(salePrice.getText()) / Float.parseFloat(parValue.getText()));

		if (collableType.isSelected() == true) {
			bond.setCallable(1);
		}
		if (convertibleType.isSelected() == true) {
			bond.setConvertible(1);
		}
		bond.setInstrumentIssuer(instrumentIssuer.getValue());
		proxy.create(bond);

    }
	/**
	 * Initializes the controller class.
	 */
    String jndiName = "SLTS_server-ear/SLTS_server-ejb/InstrumentService!tn.esprit.SLTS_server.instrumentServices.InstrumentServiceRemote";
    String jndiNameusr = "SLTS_server-ear/SLTS_server-ejb/UserService!tn.esprit.SLTS_server.services.UserServiceRemote";
    Context context;
    InstrumentServiceRemote proxy;
    UserServiceRemote service;
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// fill in comboBox
				instrumentIsserType.setItems(options);
				instrumentType.setItems(optionsTypeInstrument);
				currency.setItems(optionsDevise);
				instrumentBackround.setItems(optionsInstrumentBackround);
				try {
					context = new InitialContext();
				} catch (NamingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					proxy = (InstrumentServiceRemote) context.lookup(jndiName);
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(HomeController.idcurrentuser+"kkkkkkkkkkkkkkkkkkkkkkkkkkkk");
				try {
					service = (UserServiceRemote) context.lookup(jndiNameusr);
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				List<Customer> customerss=service.getalltradercustomersbyid(HomeController.idcurrentuser);
				for (Customer customer : customerss) {
					optionsCustomersIssuers.add(customer);
					
				}
				instrumentIssuer.setItems(optionsCustomersIssuers);
		
	}

	
}