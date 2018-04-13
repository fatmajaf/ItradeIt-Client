/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.sltsclient.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import tn.esprit.SLTS_server.services.Portfolioservice;


/**
 * FXML Controller class
 *
 * @author kaies oukhay
 */
public class Delete_portfolioController implements Initializable {

    @FXML
    private JFXTextField id;
    @FXML
    private JFXButton delete;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    

    @FXML
    private void delete(ActionEvent event) throws NamingException {
    	 String jndiName = "SLTS_server-ear/SLTS_server-ejb/PortfolioServiceImp!tn.esprit.SLTS_server.service.Portfolioservice";
    		Context context = new InitialContext();
    		
    		Portfolioservice proxy=(Portfolioservice) context.lookup(jndiName);
    		proxy.deletePortfolio(Integer.valueOf(id.getText()));
    }
    
}
