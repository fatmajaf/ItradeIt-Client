/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.sltsclient.Controllers;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import tn.esprit.sltsclient.Utils.FadeInTransition;
import tn.esprit.sltsclient.Utils.Navigation;
import tn.esprit.sltsclient.Utils.mouseDrag;
import tn.esprit.sltsclient.Utils.time;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import tn.esprit.sltsclient.Utils.exit;
/**
 * 
 * FXML Controller class
 *
 * @author AGORA
 */
public class HomeController implements Initializable {
	public static int idcurrentuser;
	public static String role;
	 exit ex = new exit();
    
	 @FXML
	    private JFXDrawer drawer;

	    @FXML
	    private JFXHamburger hamburger;

	    @FXML
	    private AnchorPane rootPane;

	    @FXML
	    private Label Username;

	    @FXML
	    private Label emailUser;

	    @FXML
	    private ImageView imageUser;

	    @FXML
	    private Label date;

	    @FXML
	    private Label timee;

	    @FXML
	    private Label exit;

	    @FXML
	    private Label minimize;
	  Navigation nav= new Navigation();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	 System.out.println("iddddddddddddd"+idcurrentuser);
    	 
    	  bindToTime();
          time time = new time();
          date.setText(time.date());
    	   HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
           transition.setRate(-1);
           
           hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
               transition.setRate(transition.getRate() * -1);
               transition.play();

               if (drawer.isShown()) {
            	   drawer.toBack();
                   drawer.close();
                   
               } else {
            	   drawer.toFront();
                   drawer.open();
               }

           });
           try {
        	   Navigation nav= new Navigation();
        	   
        		   
               AnchorPane usermanagement = FXMLLoader.load(getClass().getResource(nav.getUsermanagement()));
               AnchorPane addtrader = FXMLLoader.load(getClass().getResource(nav.getAddtrader()));
               AnchorPane addcustomer =FXMLLoader.load(getClass().getResource(nav.getAddcustomer()));
               AnchorPane homeman = FXMLLoader.load(getClass().getResource(nav.getHomemmanagement()));
               AnchorPane profileuser = FXMLLoader.load(getClass().getResource(nav.getProfileuser()));
               AnchorPane optioncalculation = FXMLLoader.load(getClass().getResource(nav.getOptionscalculation()));
               AnchorPane optionshares = FXMLLoader.load(getClass().getResource(nav.getOptionsshares()));
               //asma
               AnchorPane addinstrument = FXMLLoader.load(getClass().getResource(nav.getAddinstrument()));
               AnchorPane instrumentmanagement = FXMLLoader.load(getClass().getResource(nav.getInstrumentman()));
               VBox sidePane = null;
        	   if (role.equals("Admin")){
              sidePane = FXMLLoader.load(getClass().getResource(nav.getMenu()));
              setNode(homeman);
        	   }
        	   else if (role.equals("Trader")){
                 sidePane = FXMLLoader.load(getClass().getResource(nav.getMenutrader()));
                 setNode(addcustomer);
            	   }
        	   
              
               for (Node node : sidePane.getChildren()) {
                   if (node.getAccessibleText() != null) {
                       node.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent ev) -> {
                           switch (node.getAccessibleText()) {
                               case "homeMenu":
                                   drawer.close();
                                   setNode(homeman);
                                   break;
                               case "usermenu":
                                   drawer.close();                               
                                   setNode(usermanagement);
                                   break;
                               case "addtrader":
                                   drawer.close();
                                   setNode(addtrader);
                                   break;
                               case "addcustomer":
                                   drawer.close();
                                   setNode(addcustomer);
                                   break;
                                   
                               case "testtrader":
                                   drawer.close();
                                   setNode(addcustomer);
                                   break;  
                               case "profileuser":
                                   drawer.close();
                                   setNode(profileuser);
                                   break;  
                               case "addinstrument":
                                   drawer.close();
                                   setNode(addinstrument);
                                   break;  
                               case "instrumentmanagement":
                                   drawer.close();
                                   setNode(instrumentmanagement);
                                   break;  
                               case "optioncalculation":
                                   drawer.close();
                                   setNode(optioncalculation);
                                   break; 
                               case "optionsshares":
                                   drawer.close();
                                   setNode(optionshares);
                                   break; 
                               case "usersview":
                                   drawer.close();
                                   setNode(usermanagement);
                                   break; 
                                                             
                           }
                       });
                   }

               }
               
               
               drawer.setSidePane(sidePane);
    	}
           catch (IOException ex) {
               Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
           }
    }
    private void setNode(Node node) {
    	rootPane.getChildren().clear();
        rootPane.setOpacity(0);
        new FadeInTransition(rootPane).play();
        rootPane.getChildren().clear();
        rootPane.getChildren().add((Node) node);
    }

    @FXML
    void imageHover(MouseEvent event) {

    }

    @FXML
    void logout(ActionEvent event) throws IOException {
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Do you really want  to log out ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            
        	 Notifications.create().title("Log ut").text("Stay in touch ! ").darkStyle().showInformation();
			 
            
             
        
            
            Parent parent = FXMLLoader.load(getClass().getResource(nav.getLogin()));
            Scene database_scene = new Scene(parent);
            Stage app_stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setScene(database_scene);
            app_stage.setTitle("Login");
            mouseDrag md = new mouseDrag();
            md.setDragged(parent, app_stage);
            app_stage.getIcons().add(nav.applicationIcon);
            app_stage.show();
        } 
    }
    private void bindToTime() {
        Timeline timeline = new Timeline(
        new KeyFrame(Duration.seconds(0),
          new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent actionEvent) {
              Calendar time = Calendar.getInstance();
              SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
              timee.setText(simpleDateFormat.format(time.getTime()));
            }
          }
        ),
        new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        }
    @FXML
    void setDefault(MouseEvent event) {
    	exit.setStyle("-fx-background-color:  #4183D7;");
    }

    @FXML
    void setDefault2(MouseEvent event) {
    	 minimize.setStyle("-fx-background-color:  #4183D7;");
    }

    @FXML
    void setHover(MouseEvent event) {
    	 exit.setStyle("-fx-background-color: red;");
    }

    @FXML
    void setHover2(MouseEvent event) {
    	minimize.setStyle("-fx-background-color: blue;");
    }
    @FXML
    void handleExitClicked(MouseEvent event) {
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Do you really want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
          System.exit(0);
        } 
    }

    @FXML
    void handleMinimizeClicked(MouseEvent event) {
    	 ex.minimizeClicked(event);
    }
}