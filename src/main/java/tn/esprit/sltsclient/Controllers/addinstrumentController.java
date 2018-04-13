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
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.security.MessageDigest;
import java.util.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import org.patriques.AlphaVantageConnector;
import org.patriques.ForeignExchange;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.exchange.CurrencyExchange;
import org.patriques.output.exchange.data.CurrencyExchangeData;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sun.misc.BASE64Encoder;
import webservicefatma.GlobalWeatherDelegate;

public class addinstrumentController implements Initializable {

	@FXML
	private JFXComboBox<String> instrumentType;

	@FXML
	private JFXDatePicker maturityDate;

	@FXML
	private JFXDatePicker callabilityPeriod;

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
	private JFXButton btnSave;

	/****************************
	 * sprint 2
	 ************************************************/
	@FXML
	private JFXRadioButton PrincipleValueRadioId;

	@FXML
	private JFXRadioButton futureValueRadioId;
	@FXML
	private JFXTextField valueId;

	@FXML
	private JFXComboBox<String> fromCurrencyComboId;

	@FXML
	private JFXComboBox<String> toCurrencyComboId;

	@FXML
	private JFXTextField initialAmmountId;

	@FXML
	private JFXTextField fromCurrencySymbolId;

	@FXML
	private JFXTextField toCurrencySymbolId;

	@FXML
	private JFXTextField convertedCurrencyId;

	@FXML
	private Label dateConversionId;

	@FXML
	private JFXComboBox<String> typeOfPaymentComboId;

	@FXML
	private JFXTextField amountId;

	// combo box
	ObservableList<String> types = FXCollections.observableArrayList("Bi Annual", "Annually", "Semi Annually",
			"Quarterly", "Monthly", "Continuous Compounding");

	ObservableList<String> currencies = FXCollections.observableArrayList("AFA", "ALL", "DZD", "ARS", "AWG", "AUD",
			"BSD", "BHD", "AFA", "BDT", "BBD", "BZD", "BMD", "BTN", "BOB", "BWP", "BRL", "GBP", "BND", "BIF", "XOF",
			"XAF", "KHR", "CAD", "CVE", "KYD", "CLP", "CNY", "COP", "KMF", "CRC", "HRK", "CUP", "CYP", "CZK", "DKK",
			"DJF", "DOP", "XCD", "EGP", "SVC", "EEK", "ETB", "EUR", "FKP", "GMD", "GHC", "GIP", "XAU", "GTQ", "GNF",
			"GYD", "HTG", "HNL", "HKD", "HUF", "ISK", "INR", "IDR", "IQD", "ILS", "JMD", "JPY", "JOD", "KZT", "KES",
			"KRW", "KWD", "LAK", "LVL", "LBP", "LSL", "LRD", "LYD", "LTL", "MOP", "MKD", "MGF", "MWK", "MYR", "MVR",
			"MTL", "MRO", "MUR", "MXN", "MDL", "MNT", "MAD", "MZM", "MMK", "NAD", "NPR", "ANG", "NZD", "NIO", "NGN",
			"KPW", "NOK", "OMR", "XPF", "PKR", "XPD", "PAB", "PGK", "PYG", "PEN", "PHP", "XPT", "PLN", "QAR", "ROL",
			"RUB", "WST", "STD", "SAR", "SCR", "SLL", "XAG", "SGD", "SKK", "SIT", "SBD", "SOS", "ZAR", "LKR", "SHP",
			"SDD", "SRG", "SZL", "SEK", "CHF", "SYP", "TWD", "TZS", "THB", "TOP", "TTD", "TND", "TRL", "USD", "AED",
			"UGX", "UAH", "UYU", "VUV", "VEB", "VND", "YER", "YUM", "ZMK", "ZWD", "TRY");

	// end combo box

	/***************************
	 * sprint 2
	 ************************************************/

	// combo box
	ObservableList<String> options = FXCollections.observableArrayList("Corporation", "Investment Trust", "Domestic",
			"Foreign Government");
	// end combo box

	// combo box
	ObservableList<String> optionsTypeInstrument = FXCollections.observableArrayList("Bond", "Option", "Future");
	// end combo box

	// combo box
	ObservableList<String> optionsInstrumentBackround = FXCollections.observableArrayList("Corporate", "Government",
			"Asset-Backed Securities");
	// end combo box

	ObservableList<Customer> optionsCustomersIssuers = FXCollections.observableArrayList();

	@FXML
	void emailval(KeyEvent event) {

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
		currency.setItems(currencies);
		instrumentBackround.setItems(optionsInstrumentBackround);
		typeOfPaymentComboId.setItems(types);
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
		System.out.println(HomeController.idcurrentuser + "kkkkkkkkkkkkkkkkkkkkkkkkkkkk");
		try {
			service = (UserServiceRemote) context.lookup(jndiNameusr);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Customer> customerss = service.getalltradercustomersbyid(HomeController.idcurrentuser);
		for (Customer customer : customerss) {
			optionsCustomersIssuers.add(customer);

		}
		instrumentIssuer.setItems(optionsCustomersIssuers);

		couponRate.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					couponRate.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});

		initialAmmountId.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					initialAmmountId.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});

		amountId.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					amountId.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});

		ToggleGroup values = new ToggleGroup();
		PrincipleValueRadioId.setToggleGroup(values);
		futureValueRadioId.setToggleGroup(values);
		PrincipleValueRadioId.setSelected(true);

		ToggleGroup valuestypes = new ToggleGroup();
		collableType.setToggleGroup(valuestypes);
		convertibleType.setToggleGroup(valuestypes);
		collableType.setSelected(true);

		fromCurrencyComboId.setItems(currencies);
		toCurrencyComboId.setItems(currencies);

	}

	@FXML
	void AdviceCallable(MouseEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/sltsclient/Views/CallableAdvice.fxml"));

			Stage stage = new Stage();
			Scene scene = new Scene(root);
			stage.initStyle(StageStyle.TRANSPARENT);

			stage.setScene(scene);
			stage.show();

		} catch (IOException ex) {
			System.out.println("ho");
		}

	}

	public String toString(Double d) {
		return d.toString();
	}

	@FXML
	void CalculateValue(ActionEvent event) {

		Date date = new Date();
		if (maturityDate.getValue() != null && amountId.getText() != null && typeOfPaymentComboId.getValue() != null
				&& couponRate.getText() != null && java.sql.Date.valueOf(maturityDate.getValue()).after(date)) {
			Date maturitydate = java.sql.Date.valueOf(maturityDate.getValue());

			String jndiName = "SLTS_server-ear/SLTS_server-ejb/InstrumentService!tn.esprit.SLTS_server.instrumentServices.InstrumentServiceRemote";
			InitialContext context = null;
			try {
				context = new InitialContext();
			} catch (NamingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			InstrumentServiceRemote proxy = null;
			try {
				proxy = (InstrumentServiceRemote) context.lookup(jndiName);
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			double interestRate = Float.parseFloat(couponRate.getText());
			double ammount = Float.parseFloat(amountId.getText());
			Date startDate = new Date();
			Integer nbrYears = proxy.calculNumberOfYears(maturitydate, startDate);
			String typeOfPayment = typeOfPaymentComboId.getValue().toLowerCase();

			if (futureValueRadioId.isSelected() == true) {

				float x = proxy.calculFutureValue(ammount, interestRate, nbrYears, typeOfPayment);
				System.out.println(x);
				valueId.setText(toString((double) x));

			} else if (PrincipleValueRadioId.isSelected() == true) {
				float x = proxy.calculPrincipleValue(ammount, interestRate, nbrYears, typeOfPayment);
				System.out.println(x);
				valueId.setText(toString((double) x));

			}
		} else {
			Alert alert = new Alert(AlertType.ERROR, "Please Provide all fields !");
			alert.setTitle("Wrong request");
			alert.setHeaderText("Mathematical functions cannot work without all attributes");
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/tn/esprit/sltsclient/images/logocomplete1.png")); // To
			// add
			// an
			// icon
			// stage.initStyle(StageStyle.TRANSPARENT);
			alert.showAndWait();

		}

	}

	@FXML
	void convertCurrency(ActionEvent event) {

		String apiKey = "50M3AP1K3Y";
		int timeout = 3000;

		// Foreign Exchange example
		AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
		ForeignExchange foreignExchange = new ForeignExchange(apiConnector);
		if (fromCurrencyComboId.getValue() != null && initialAmmountId.getText() != null
				&& toCurrencyComboId.getValue() != null) {
			try {
				String ch1 = (String) fromCurrencyComboId.getValue();
				String ch2 = (String) toCurrencyComboId.getValue();

				CurrencyExchange currencyExchange = foreignExchange.currencyExchangeRate(ch1, ch2);
				CurrencyExchangeData currencyExchangeData = currencyExchange.getData();

				float value = (float) Double.parseDouble(initialAmmountId.getText());
				float newAmmount = value * currencyExchangeData.getExchangeRate();

				fromCurrencySymbolId.setText(currencyExchangeData.getFromCurrencyName());

				toCurrencySymbolId.setText(currencyExchangeData.getToCurrencyName());
				convertedCurrencyId.setText(toString((double) newAmmount));

				LocalDateTime date = currencyExchangeData.getTime();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

				String formatDateTime = date.format(formatter);

				dateConversionId.setText(" The timing of the exchange currency is : " + formatDateTime);

			} catch (AlphaVantageException e) {
				System.out.println("something went wrong");
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR, "Please Provide all fields !");
			alert.setTitle("Wrong request");
			alert.setHeaderText("Choose the right Currency !");
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/tn/esprit/sltsclient/images/logocomplete1.png")); // To
			// add
			// an
			// icon
			alert.showAndWait();

		}

	}

	@FXML
	void saveinstrumentClicked(ActionEvent event) {

		if (maturityDate.getValue() != null && amountId.getText() != null && typeOfPaymentComboId.getValue() != null
				&& couponRate.getText() != null && java.sql.Date.valueOf(maturityDate.getValue()).after(new Date())
				&& currency.getValue() != null && instrumentBackround != null && instrumentIsserType != null
				&& instrumentIssuer != null && instrumentType != null) {
			Bond bond = new Bond();

			bond.setBackroundType(instrumentBackround.getValue());
			bond.setCurrency(currency.getValue());
			bond.setInstrumentIssuerType(instrumentIsserType.getValue());

			Date maturitydate = java.sql.Date.valueOf(maturityDate.getValue());
			bond.setMaturitydate(maturitydate);
			bond.setCouponrate(Double.parseDouble(couponRate.getText()));

			// etudier la condition
			bond.setParvalue(Double.parseDouble(valueId.getText()));
			bond.setSaleprice(Double.parseDouble(amountId.getText()));
			// bond.setAvailablecoupon(Float.parseFloat(salePrice.getText()) /
			// Float.parseFloat(parValue.getText()));

			bond.setTypeOfCouponPayment(typeOfPaymentComboId.getValue());
			bond.setDateOfPublication(new Date());
			bond.setCallabilityperiod(new Date());
			bond.setAvailablecoupon((float) 10);

			if (collableType.isSelected() == true) {
				bond.setCallable(1);
			}
			if (convertibleType.isSelected() == true) {
				bond.setConvertible(1);
			}
			bond.setInstrumentIssuer(instrumentIssuer.getValue());
			proxy.create(bond);

		} else {
			Alert alert = new Alert(AlertType.ERROR, "Please Provide all fields !");
			alert.setTitle("Wrong request");
			alert.setHeaderText("A bond cannot be published without all details.");
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/tn/esprit/sltsclient/images/logocomplete1.png")); // To
			// add
			// an
			// icon
			alert.showAndWait();

		}
	}
}