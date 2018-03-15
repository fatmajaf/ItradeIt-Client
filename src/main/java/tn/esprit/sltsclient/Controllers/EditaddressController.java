/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.sltsclient.Controllers;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import tn.esprit.SLTS_server.persistence.Address;
import tn.esprit.SLTS_server.persistence.User;
import tn.esprit.SLTS_server.services.UserServiceRemote;
import tn.esprit.sltsclient.Utils.Navigation;
import webservicefatma.GlobalWeatherDelegate;

import com.jfoenix.controls.JFXComboBox;
/**
 * FXML Controller class
 *
 * @author AGORA
 */
public class EditaddressController implements Initializable {

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

	    @FXML
	    private JFXComboBox<String> countries;

	    @FXML
	    private JFXComboBox<String> cities;

	    @FXML
	    private JFXTextField street;

	    @FXML
	    private JFXTextField zipcode;

	    @FXML
	    private JFXTextField nationality;
	    Navigation nav= new Navigation();

	    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        nav.numbersCheck(zipcode);
        ObservableList<String> combocountries = FXCollections.observableArrayList();

		String[] locales = Locale.getISOCountries();

		for (String countryCode : locales) {

			Locale obj = new Locale("", countryCode);

			System.out.println("Country Code = " + obj.getCountry() + ", Country Name = " + obj.getDisplayCountry());
			combocountries.add(obj.getDisplayCountry(Locale.ENGLISH));

		}
		countries.setItems(combocountries);
		
		

       
    } 
    
    public void setData(String idJ, String country, String cityy, String zipcodee, String streett)
    {
        ide.setText(idJ);
        
        countries.setValue(country);
        ObservableList<String> combocities = FXCollections.observableArrayList();
		GlobalWeatherDelegate gwd = new GlobalWeatherDelegate();
		List<String> citiesli = gwd.getCities(countries.getValue());
		for (String city : citiesli) {
			combocities.add(city);
		}
		cities.setItems(combocities);
		cities.setValue(cityy);
		nationality.setText(countries.getValue());
		zipcode.setText(zipcodee);
		street.setText(streett);
		
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

   
            @FXML
    private void editClicked(ActionEvent event) throws NamingException{
               System.out.println("cliked");
               System.out.println("ideeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"+ide.getText());
       if(zipcode.getText().equals("")||street.getText().equals("")||cities.getValue().equals("")||countries.getValue().equals("")){
           nav.showAlert(Alert.AlertType.WARNING, "Attention", null, "all inputs are required !!");
       } 
       else{
    	   context= new InitialContext();
    	   service = (UserServiceRemote) context.lookup(jndiName);
    	 
    	 User  user = service.findUserById(Integer.parseInt(ide.getText()));
    	 
    	Address address= new Address();
    	address.setId(user.getAddress().getId());
    	address.setSountry(countries.getValue());
    	address.setState(cities.getValue());
    	address.setZipcode(Integer.parseInt(zipcode.getText()));
    	user.setAddress(address);
    	service.updateUser(user);
          
               nav.showAlert(Alert.AlertType.INFORMATION, "Success", null, "Update done successfully ..");
           
       }
    }
    
    
}
