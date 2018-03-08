/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.sltsclient.Controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import tn.esprit.SLTS_server.persistence.Bond;
import tn.esprit.SLTS_server.persistence.Instrument;
import tn.esprit.SLTS_server.persistence.User;
import javafx.scene.chart.PieChart;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyEvent;
import java.math.RoundingMode;
import java.net.URL;
import java.security.MessageDigest;
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

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.scene.control.TreeTableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.scene.control.TreeItem;
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
import tn.esprit.sltsclient.Utils.Instruments;
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
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.scene.layout.AnchorPane;
/**
 * FXML Controller class
 *
 * @author AGORA
 */
public class instrumentmanagementController implements Initializable {


    @FXML
    private JFXTreeTableView<Instruments> tableInstrumentAll;

    @FXML
    private PieChart piechart;

    @FXML
    private AnchorPane att;

   
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

		tableInstrumentAll.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                   // Instrument ev = tableInstrumentAll.getSelectionModel().getSelectedItem().getValue().
                    Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
                    Optional<ButtonType> result=alert.showAndWait();
                    if(result.get()==ButtonType.OK){
                    	try {
							deleteInstrument();
						} catch (NamingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }
                }}}
                    
                    );
		
		
		// fill in comboBox
				
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
				
			
				settableviewleaves();
	}
	

	private ObservableList<Bond> getInstrument() throws NamingException, IOException {

		//String jndiName = "SLTS_server-ear/SLTS_server-ejb/InstrumentService!instrumentServices.InstrumentServiceRemote";
		context = new InitialContext();
		InstrumentServiceRemote InstProxy = (InstrumentServiceRemote) context.lookup(jndiName);
		ObservableList<Bond> InstList = FXCollections.observableArrayList(InstProxy.findAllBonds());
		return InstList;
	}
	
	 private void settableviewleaves() {
	       
		    JFXTreeTableColumn<Instruments, String> parvalue = new JFXTreeTableColumn<>("start");
		        parvalue.setPrefWidth(150);
		       parvalue.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Instruments, String>, ObservableValue<String>>() {
		            @Override
		            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Instruments, String> param) {
		                return param.getValue().getValue().parvalue;
		            }});
		       JFXTreeTableColumn<Instruments, String> saleprice = new JFXTreeTableColumn<>("start");
		       saleprice.setPrefWidth(150);
		       saleprice.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Instruments, String>, ObservableValue<String>>() {
		            @Override
		            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Instruments, String> param) {
		                return param.getValue().getValue().saleprice;
		            }});
		       JFXTreeTableColumn<Instruments, String> couponrate = new JFXTreeTableColumn<>("start");
		       couponrate.setPrefWidth(150);
		       couponrate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Instruments, String>, ObservableValue<String>>() {
		            @Override
		            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Instruments, String> param) {
		                return param.getValue().getValue().saleprice;
		            }});
		       JFXTreeTableColumn<Instruments, String> availablecoupon = new JFXTreeTableColumn<>("start");
		       availablecoupon.setPrefWidth(150);
		       availablecoupon.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Instruments, String>, ObservableValue<String>>() {
		            @Override
		            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Instruments, String> param) {
		                return param.getValue().getValue().availablecoupon;
		            }});
		       JFXTreeTableColumn<Instruments, String> issuer = new JFXTreeTableColumn<>("start");
		       issuer.setPrefWidth(150);
		       issuer.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Instruments, String>, ObservableValue<String>>() {
		            @Override
		            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Instruments, String> param) {
		                return param.getValue().getValue().issuer;
		            }});
		       JFXTreeTableColumn<Instruments, String> id = new JFXTreeTableColumn<>("start");
		       id.setPrefWidth(150);
		       id.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Instruments, String>, ObservableValue<String>>() {
		            @Override
		            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Instruments, String> param) {
		                return param.getValue().getValue().id;
		            }});
		       JFXTreeTableColumn<Instruments, String> idissuer = new JFXTreeTableColumn<>("start");
		       idissuer.setPrefWidth(150);
		       idissuer.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Instruments, String>, ObservableValue<String>>() {
		            @Override
		            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Instruments, String> param) {
		                return param.getValue().getValue().idissuer;
		            }});
		       
		   	
		   	
		        
		        ObservableList<Instruments> leaves = FXCollections.observableArrayList();
		        //InstProxy.findAllBonds()
		   
		     
		     
		     List<Bond>bonds=proxy.findAllBonds();
		        for (Bond le : bonds) {
		        	String issuerrr="t";
		        	String idiss="k";
		            if(le.getInstrumentIssuer()!=null)
		            {
		            	issuerrr= le.getInstrumentIssuer().getFirstName()+" "+le.getInstrumentIssuer().getLastName();
		            	idiss=le.getInstrumentIssuer().getId().toString();
		            }
		           Instruments ltable= new Instruments(le.getParvalue().toString(), le.getSaleprice().toString(), le.getCouponrate().toString(), le.getAvailablecoupon().toString(),issuerrr,le.getId().toString(),idiss);
		            leaves.add(ltable);
		        }
		       
		        final TreeItem<Instruments> root;
		        root = new RecursiveTreeItem<Instruments>(leaves, RecursiveTreeObject::getChildren);
		        tableInstrumentAll.getColumns().setAll(parvalue,saleprice,couponrate,availablecoupon,issuer);
		        tableInstrumentAll.setRoot(root);
		        tableInstrumentAll.setShowRoot(false);

		        tableInstrumentAll.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		       
  }
	 private void deleteInstrument() throws NamingException{
			 proxy = (InstrumentServiceRemote) context.lookup(jndiName);
			if(tableInstrumentAll.getSelectionModel().getSelectedItem()!= null)	{
			Integer r = Integer.parseInt(tableInstrumentAll.getSelectionModel().getSelectedItem().getValue().id.getValue().toString());
			proxy.delete(r);
			
		}}
	 
	 private void setpiechart(){
	        /*AttendanceService as= new AttendanceService();
	        int nbRH= as.getNbHoursPerDep("Euro");
	        int nbFinance = as.getNbHoursPerDep("Dinar");
	        int nbCRM= as.getNbHoursPerDep("Dollar");
	        
	        
	        
	        */
	        
	   /*  ObservableList<PieChart.Data> pieChartData =
	                FXCollections.observableArrayList(
	                new PieChart.Data("Euro", nbRH),
	                new PieChart.Data("Dollar", nbCRM),
	             
	                new PieChart.Data("Dinar", nbFinance));

	        piechart = new PieChart(pieChartData);
	        piechart.setTitle("Attendance Per Department");
	        final Label caption = new Label("");
	        caption.setTextFill(Color.DARKORANGE);
	        caption.setStyle("-fx-font: 24 arial;");

	        for (final PieChart.Data data : piechart.getData()) {
	            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
	                    new EventHandler<MouseEvent>() {
	                        @Override public void handle(MouseEvent e) {
	                            caption.setTranslateX(e.getSceneX());
	                            caption.setTranslateY(e.getSceneY());
	                            caption.setText(String.valueOf(data.getPieValue()) 
	                                + "%");
	                        }
	                    });
	        }
	        
	                att.getChildren().addAll(piechart, caption);
	      //  ((Group) scene.getRoot()).getChildren().addAll(chart, caption);
	    
	    */
	    }
	    
	
}