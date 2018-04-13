/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.sltsclient.Controllers;

import java.beans.EventHandler;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import org.hibernate.annotations.Parent;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import tn.esprit.SLTS_server.persistence.Portfolio;
import tn.esprit.SLTS_server.services.Portfolioservice;
import tn.esprit.sltsclient.Utils.Navigation;

/**
 * FXML Controller class
 *
 * @author kaies oukhay
 */
public class Add_portfolioController implements Initializable {

	@FXML
	private Button add;
	@FXML
	private JFXComboBox<String> currency;
	ObservableList<String> combodeps = FXCollections.observableArrayList("DZD", "USD", "EUR","CAD","BRL","TND");

	@FXML
	private JFXTextField cash;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
		currency.setItems(combodeps);

	}

	@FXML
	private void add_portfolio(ActionEvent event) throws NamingException, MessagingException {
		String jndiName = "SLTS_server-ear/SLTS_server-ejb/PortfolioServiceImp!tn.esprit.SLTS_server.service.Portfolioservice";
		Context context = new InitialContext();

		Portfolioservice proxy = (Portfolioservice) context.lookup(jndiName);

		Portfolio p = new Portfolio();
		
		p.setCashflow(Double.parseDouble(cash.getText()));
		p.setCurrency(currency.getValue());
		p.setDate_creation(new Date());
		
		System.out.println(currency.getValue());

		proxy.addportfolio(p);
		
		
		  String UsernameSender = "yousfioussamaa@gmail.com"; //ur email
	        String Pass = "oussama212";
	        String username = "kaies.oukhay@esprit.tn";

	        Properties props = new Properties();
	        props.put("mail.smtp.auth", true);
	        props.put("mail.smtp.starttls.enable", true);
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.port", "587");

	        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(UsernameSender, Pass);
	            }
	        });

	        try {

	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(UsernameSender));//ur email
	            message.setRecipients(Message.RecipientType.TO,
	                    InternetAddress.parse(username));//u will send to
	            message.setSubject("Portfolio ");
	            message.setText("your portfolio is added");
	            MimeBodyPart messageBodyPart = new MimeBodyPart();
	            Multipart multipart = new MimeMultipart();

	            System.out.println("sending");
	            Transport.send(message);
	            System.out.println("Done");
   
	         } catch (MessagingException e) {
	            e.printStackTrace();
	        }
	}

}
