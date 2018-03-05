/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.sltsclient.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Fatma Jaafar
 */
public class MenuController implements Initializable {
    
    

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton usermanagement;

    @FXML
    private JFXButton btnProfile;

    @FXML
    private JFXButton btnWidgets;

    @FXML
    private JFXButton btnAlerts;

    @FXML
    private JFXButton btnControls;
    @FXML
    private JFXButton btnaddcustomer;

    @FXML
    void exitclicked(ActionEvent event) {

    }

    @FXML
    void logoutclicked(ActionEvent event) {

    }

  

   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
}

