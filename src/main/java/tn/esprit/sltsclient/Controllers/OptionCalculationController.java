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
import tn.esprit.SLTS_server.persistence.Company;
import tn.esprit.SLTS_server.persistence.Customer;
import tn.esprit.SLTS_server.persistence.Trader;
import tn.esprit.SLTS_server.persistence.User;
import tn.esprit.SLTS_server.services.UserServiceRemote;
import tn.esprit.sltsclient.Utils.BSOption;
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
import javafx.scene.text.Text;
/**
 * FXML Controller class
 *
 * @author AGORA
 */
public class OptionCalculationController implements Initializable {
	 BSOption optionCall;
	  BSOption optionPut;
	  @FXML
	    private JFXTextField spotprice;

	    @FXML
	    private JFXTextField strikeprice;

	    @FXML
	    private JFXTextField maturity;

	    @FXML
	    private JFXTextField volatility;

	    @FXML
	    private JFXTextField riskfreerate;

	    @FXML
	    private JFXTextField yield;

	    @FXML
	    private Text callprice;

	    @FXML
	    private Text callimpliedvolatility;

	    @FXML
	    private Text putprice;

	    @FXML
	    private Text putimpliedvolatility;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		optionCall= new BSOption(120, 100, 5, 0.005, 0.001, 0.1, 0, 'c');
		optionPut= new BSOption(120, 100, 5, 0.005, 0.001, 0.1, 0, 'p');
	changesmanagement();
		
	}
	@SuppressWarnings("restriction")
	private void changesmanagement() {
		NumberValidator validatornum = new NumberValidator();
		validatornum.setMessage("Input must be numeric");
		strikeprice.getValidators().add(validatornum);
		strikeprice.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
			
			if (strikeprice.validate())
			{
				optionCall.setK(Double.parseDouble(strikeprice.getText()));
				callprice.setText(String.valueOf(optionCall.computePrice()));
				optionPut.setK(Double.parseDouble(strikeprice.getText()));
				putprice.setText(String.valueOf(optionPut.computePrice()));
			}
		});
		
		
		spotprice.getValidators().add(validatornum);
		spotprice.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
				if (spotprice.validate())
				{
					optionCall.setS(Double.parseDouble(spotprice.getText()));
					callprice.setText(String.valueOf(optionCall.computePrice()));
					callimpliedvolatility.setText(String.valueOf(optionCall.ImpliedVol_NewtonRaphson(optionCall.getPrice(), 0.05, 300, Math.pow(10,-3))));
					optionPut.setS(Double.parseDouble(spotprice.getText()));
					putprice.setText(String.valueOf(optionPut.computePrice()));
					putimpliedvolatility.setText(String.valueOf(optionPut.ImpliedVol_NewtonRaphson(optionPut.getPrice(), 0.05, 300, Math.pow(10,-3))));
					
					
				
				}
		});
		maturity.getValidators().add(validatornum);
		maturity.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
			
			if (maturity.validate())
			{
				optionCall.setT(Double.parseDouble(maturity.getText()));
				callprice.setText(String.valueOf(optionCall.computePrice()));
				optionPut.setT(Double.parseDouble(maturity.getText()));
				putprice.setText(String.valueOf(optionPut.computePrice()));
				callimpliedvolatility.setText(String.valueOf(optionCall.ImpliedVol_NewtonRaphson(optionCall.getPrice(), 0.05, 300, Math.pow(10,-3))));
				putimpliedvolatility.setText(String.valueOf(optionPut.ImpliedVol_NewtonRaphson(optionPut.getPrice(), 0.05, 300, Math.pow(10,-3))));
				
				
			}
		});
		volatility.getValidators().add(validatornum);
		volatility.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
			
			if (volatility.validate())
			{
				optionCall.setVol(Double.parseDouble(volatility.getText()));
				callprice.setText(String.valueOf(optionCall.computePrice()));
				optionPut.setVol(Double.parseDouble(volatility.getText()));
				putprice.setText(String.valueOf(optionPut.computePrice()));
				callimpliedvolatility.setText(String.valueOf(optionCall.ImpliedVol_NewtonRaphson(optionCall.getPrice(), 0.05, 300, Math.pow(10,-3))));
				putimpliedvolatility.setText(String.valueOf(optionPut.ImpliedVol_NewtonRaphson(optionPut.getPrice(), 0.05, 300, Math.pow(10,-3))));
				
			}
		});
		riskfreerate.getValidators().add(validatornum);
		riskfreerate.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
			
			if (riskfreerate.validate())
			{
				optionCall.setR(Double.parseDouble(riskfreerate.getText()));
				callprice.setText(String.valueOf(optionCall.computePrice()));
				optionPut.setR(Double.parseDouble(riskfreerate.getText()));
				putprice.setText(String.valueOf(optionPut.computePrice()));
				callimpliedvolatility.setText(String.valueOf(optionCall.ImpliedVol_NewtonRaphson(optionCall.getPrice(), 0.05, 300, Math.pow(10,-3))));
				putimpliedvolatility.setText(String.valueOf(optionPut.ImpliedVol_NewtonRaphson(optionPut.getPrice(), 0.05, 300, Math.pow(10,-3))));
				
			}
			
		});
		yield.getValidators().add(validatornum);
		yield.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
				
			if (yield.validate())
			{
				optionCall.setQ(Double.parseDouble(yield.getText()));
				callprice.setText(String.valueOf(optionCall.computePrice()));
				optionPut.setQ(Double.parseDouble(yield.getText()));
				putprice.setText(String.valueOf(optionPut.computePrice()));
				callimpliedvolatility.setText(String.valueOf(optionCall.ImpliedVol_NewtonRaphson(optionCall.getPrice(), 0.05, 300, Math.pow(10,-3))));
				putimpliedvolatility.setText(String.valueOf(optionPut.ImpliedVol_NewtonRaphson(optionPut.getPrice(), 0.05, 300, Math.pow(10,-3))));
				
			}
		});
	
	
	}
	
   
}
