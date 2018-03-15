/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.sltsclient.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import tn.esprit.SLTS_server.persistence.Bond;
import tn.esprit.SLTS_server.persistence.Customer;
import tn.esprit.SLTS_server.persistence.Trader;
import tn.esprit.SLTS_server.persistence.TradingExchange;
import tn.esprit.SLTS_server.persistence.User;
import tn.esprit.SLTS_server.services.UserServiceRemote;
import tn.esprit.sltsclient.Utils.Customers;
import tn.esprit.sltsclient.Utils.FadeInRightTransition;
import tn.esprit.sltsclient.Utils.FadeInTransition;
import tn.esprit.sltsclient.Utils.FadeOutLeftTransition;
import tn.esprit.sltsclient.Utils.Navigation;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.text.DateFormatSymbols;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.AnchorPane;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import tn.esprit.sltsclient.Utils.Trades;
import javafx.scene.control.Separator;
/**
 * FXML Controller class
 *
 * @author AGORA
 */
public class ProfileController implements Initializable {
    
     public static Integer iduserprofile;
     String jndiName = "SLTS_server-ear/SLTS_server-ejb/UserService!tn.esprit.SLTS_server.services.UserServiceRemote";
 	User user;
 	UserServiceRemote service;
 	Context context;
 	  @FXML
 	    private Label bondlabelll;

 	    @FXML
 	    private Separator separateuur;
 	  @FXML
 	    private Label latesttradecust;

 	    @FXML
 	    private Label latesttradedatecreation;

 	    @FXML
 	    private Label latesttradecouponrate;

 	    @FXML
 	    private Label latesttradematuritydate;

 	    @FXML
 	    private Label latesttradesaleprice, labelcompany;

 	    @FXML
 	    private Label latesttradeparvalue;
    @FXML
    private Tab tab1, tab2, tab3;
    @FXML
    private Label name;

    @FXML
    private Label email, cash;

    @FXML
    private Label role;

    @FXML
    private Label typetrader;

    @FXML
    private Label phone;

    @FXML
    private Label address;

    @FXML
    private Label bdate;

    @FXML
    private Label since;

    @FXML
    private Label nationality;

    @FXML
    private Label banned;

    @FXML
    private Label active,companynamegal;
    @FXML
    private ImageView back;
    @FXML
    private AnchorPane  rootpane;

    @FXML
    private JFXTreeTableView<Customers> customerstableview;

    @FXML
    private JFXTreeTableView<Trades> traderstableview;
    @FXML
    private BarChart<String, Integer> bc;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    XYChart.Series<String, Integer> series = new XYChart.Series<>();
    private ObservableList<String> monthNames = FXCollections.observableArrayList();
    Navigation nav= new Navigation();
    //nv
    @FXML
    private ImageView rightfl,latesttradenotfound;

    @FXML
    private ImageView leftfl,im;
    List<Customer> customerslistfortradr;
    int lengthcust;
    int indexgal;
    Customer[] tableau;
    @FXML
    void gorightgal(MouseEvent event) {
    	indexgal++;
    	if (indexgal>= lengthcust){
    		indexgal=0;
    	}
      companynamegal.setText(tableau[indexgal].getCompany().getName());
      new FadeInRightTransition(im).play();
      im.setVisible(true);
 }
    @FXML
    void goleftgal(MouseEvent event) {
    	indexgal--;
    	if (indexgal < 0){
    		indexgal=lengthcust-1;
    	}
      companynamegal.setText(tableau[indexgal].getCompany().getName());
      new FadeOutLeftTransition(im).play();
      im.setVisible(true);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
        // Get an array with the English month names.
        // Get an array with the English month names.
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        // Convert it to a list and add it to our ObservableList of months.
        monthNames.addAll(Arrays.asList(months));

        // Assign the month names as categories for the horizontal axis.
        xAxis.setCategories(monthNames);
        
     System.out.println("im in profile ,  the id is "+iduserprofile);
     try {
		context = new InitialContext();
	} catch (NamingException e) {
// TODO Auto-generated catch block
		e.printStackTrace();
	}
		try {
			service = (UserServiceRemote) context.lookup(jndiName);
		} catch (NamingException e) {
	// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user = service.findUserById(iduserprofile);
		if (user instanceof Customer){
		    System.out.println("its a customer");
		    labelcompany.setText("Company");
		    setdatacustomer();
		    setchartDatacustomerprofile();
		    tab2.setText("Trades");
		    tab1.setText("");
		    tab1.setDisable(true);
		    tab3.setText("Infos");
		    tab3.setDisable(false);
		    rightfl.setVisible(false);
		    leftfl.setVisible(false);
		    settableviewTradesforcustomerprofile();
		    
		    
        }
		else {  System.out.println("its a Trader");
		labelcompany.setText("Companies");
		 settableviewCustomers();
		 setdatatrader();
		 setchartDatatraderprofile() ;
		 tab1.setText("Customers");
		 tab2.setText("Trades");
		 tab2.setDisable(false);
		 tab3.setText("");
		 tab3.setDisable(true);
		 settableviewTradesforTraderprofile();
		 latesttradepopulate();
		 customerslistfortradr = service.getalltradercustomersbyid(iduserprofile);
	     lengthcust=customerslistfortradr.size();
	   indexgal=0;
	   tableau= new Customer[lengthcust];
	   for (int i = 0; i<lengthcust; i++) {
		tableau[i]=customerslistfortradr.get(i);
		
	}
	   if(lengthcust<1){
		   companynamegal.setText("no companies");
		   leftfl.setVisible(false);
		   rightfl.setVisible(false);
 }
	   else {
		   companynamegal.setText(tableau[indexgal].getCompany().getName());
	   }
		}
		
		
    }  
private void latesttradepopulate(){
    	
    	List<TradingExchange> tes= service.getalltradesforcustomersbytrader(iduserprofile);
    	if (tes.size()>=1)
    	{latesttradedatecreation.setText("Creation date: " +tes.get(0).getCreationDate().toString());
    	Bond bond= (Bond) tes.get(0).getInstrument();
    	latesttradecouponrate.setText("Coupon rate "+bond.getCouponrate().toString());
    	latesttradecust.setText("Customer :"+tes.get(0).getUser().getFirstName()+" "+tes.get(0).getUser().getLastName());
    	latesttradematuritydate.setText("Maturity date: "+bond.getMaturitydate().toString());
    	latesttradeparvalue.setText("Par value : "+bond.getParvalue().toString());
    	latesttradesaleprice.setText("Sales price : "+bond.getSaleprice().toString());
    	latesttradenotfound.setVisible(false);
    	bondlabelll.setVisible(true);
   		separateuur.setVisible(true);
    	}
    	else {
    		bondlabelll.setVisible(false);
       		separateuur.setVisible(false);
    		latesttradedatecreation.setVisible(false);
    		latesttradecouponrate.setVisible(false);
    		latesttradecust.setVisible(false);
    		latesttradematuritydate.setVisible(false);
    		latesttradeparvalue.setVisible(false);
    		latesttradesaleprice.setVisible(false);
    		latesttradenotfound.setVisible(true);
    	}
    	
    }
    public void setchartDatacustomerprofile() {

List<TradingExchange>listete=service.getalltrades(iduserprofile);
   int[] monthCounter = new int[12];
  
   for (TradingExchange t : listete) {
	   int month = t.getCreationDate().getMonth()-1;
	   monthCounter[month]+=1;
			   
	   
}
   System.out.println(monthCounter);
series.setName("Number of trades");
   
   // Create a XYChart.Data object for each month. Add it to the series.
   for (int i = 0; i < monthCounter.length; i++) {
       series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
   }
   
   
   
   
   
   bc.getData().addAll(series);
   
}
    

   private void setdatatrader(){
	   Trader trader=  (Trader)user;
	   name.setText(trader.getFirstName()+" "+trader.getLastName());
	   email.setText(trader.getEmail());
	   role.setText("Trader");
	   typetrader.setText(trader.getTradertype());
	   phone.setText(Integer.toString(trader.getPhone()));
	   address.setText(trader.getAddress().getSountry()+" "+trader.getAddress().getState()+" "+trader.getAddress().getStreet()+" "+trader.getAddress().getZipcode());
	   bdate.setText(trader.getBirthdate().toString());
	   nationality.setText(trader.getNationality());
	   since.setText(trader.getCreationDate().toString());
	   String activ;
       String bann;
       if(trader.getIsactive()==0){
       	 activ="Not active";
       }
       else{
       	activ="Active account";
       }
       if(trader.getIsbanned()==1){
         	 bann="Banned for the moment";
         }
         else{
         	bann="Not banned";
         }
       active.setText(activ);
       banned.setText(bann);
       
	   
   }
   public void setchartDatatraderprofile() {

	   List<TradingExchange>listete=service.getalltradesforcustomersbytrader(iduserprofile);
	      int[] monthCounter = new int[12];
	     
	      for (TradingExchange t : listete) {
	   	   int month = t.getCreationDate().getMonth()-1;
	   	   monthCounter[month]+=1;
	   			   
	   	   
	   }
	      System.out.println(monthCounter);
	   series.setName("Number of trades");
	      
	      // Create a XYChart.Data object for each month. Add it to the series.
	      for (int i = 0; i < monthCounter.length; i++) {
	          series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
	      }
	      
	      
	      
	      
	      
	      bc.getData().addAll(series);
	      
	   }

   private void setdatacustomer(){
	   
	   Customer customer=  (Customer)user;
	   name.setText(customer.getFirstName()+" "+customer.getLastName());
	   cash.setText(customer.getMoney().toString());
	   email.setText(customer.getEmail());
	   role.setText("Trader");
	   typetrader.setText(customer.getTrader().getFirstName()+" "+customer.getLastName());
	   phone.setText(Integer.toString(customer.getPhone()));
	   address.setText(customer.getAddress().getSountry()+" "+customer.getAddress().getState()+" "+customer.getAddress().getStreet()+" "+customer.getAddress().getZipcode());
	   bdate.setText(customer.getBirthdate().toString());
	   nationality.setText(customer.getNationality());
	   since.setText(customer.getCreationDate().toString());
	   companynamegal.setText(customer.getCompany().getName());
	   String activ;
       String bann;
       if(customer.getIsactive()==0){
       	 activ="Not active";
       }
       else{
       	activ="Active account";
       }
      
       active.setText(activ);
       banned.setText("Risk : "+customer.getRisk().toString());
       List<TradingExchange>tes=service.getalltrades(iduserprofile);
       System.out.println("siiiiiiiiiiiiiiiiiiiiiiiiiiiiiiize"+tes.size());
       if (tes.size()>=1)
   	{latesttradedatecreation.setText("Creation date: " +tes.get(0).getCreationDate().toString());
   	Bond bond= (Bond) tes.get(0).getInstrument();
   	latesttradecouponrate.setText("Coupon rate "+bond.getCouponrate().toString());
   	latesttradecust.setText("Customer :"+tes.get(0).getUser().getFirstName()+" "+tes.get(0).getUser().getLastName());
   	latesttradematuritydate.setText("Maturity date: "+bond.getMaturitydate().toString());
   	latesttradeparvalue.setText("Maturity date: "+bond.getParvalue().toString());
   	latesttradesaleprice.setText("Maturity date: "+bond.getSaleprice().toString());
   	latesttradenotfound.setVisible(false);
   	bondlabelll.setVisible(true);
		separateuur.setVisible(true);
   	}
   	else {
   		bondlabelll.setVisible(false);
   		separateuur.setVisible(false);
   		latesttradedatecreation.setVisible(false);
   		latesttradecouponrate.setVisible(false);
   		latesttradecust.setVisible(false);
   		latesttradematuritydate.setVisible(false);
   		latesttradeparvalue.setVisible(false);
   		latesttradesaleprice.setVisible(false);
   		latesttradenotfound.setVisible(true);
   	}
       
	   
   }
   private void settableviewTradesforTraderprofile() {
       JFXTreeTableColumn<Trades, String> idcustomer = new JFXTreeTableColumn<>("id customer");
      idcustomer.setPrefWidth(150);
      idcustomer.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Trades, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Trades, String> param) {
              return param.getValue().getValue().idcustomer;
          }});
      
       JFXTreeTableColumn<Trades, String> customername = new JFXTreeTableColumn<>("customer name");
      customername.setPrefWidth(150);
      customername.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Trades, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Trades, String> param) {
              return param.getValue().getValue().customername;
          }});
      
      
        JFXTreeTableColumn<Trades, String> creationdate = new JFXTreeTableColumn<>("Creation date");
      creationdate.setPrefWidth(150);
      creationdate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Trades, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Trades, String> param) {
              return param.getValue().getValue().creationdate;
          }});
      
      JFXTreeTableColumn<Trades, String> status = new JFXTreeTableColumn<>("status");
     status.setPrefWidth(150);
      status.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Trades, String>, ObservableValue<String>>() {
          @Override
          public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Trades, String> param) {
              return param.getValue().getValue().status;
          }});
 
      ObservableList<Trades> atts = FXCollections.observableArrayList();
      List<TradingExchange> customers= service.getalltradesforcustomersbytrader(iduserprofile);
      for (TradingExchange t : customers) {
    	  Trades atable;
          String namea= t.getUser().getFirstName()+" "+t.getUser().getLastName();
          String statust="";
         
          if (t.getStatus()!=null){
        	  statust=t.getStatus().toString();
          }
          
           atable = new tn.esprit.sltsclient.Utils.Trades(t.getUser().getId().toString(),namea,t.getCreationDate().toString(), statust );
           atts.add(atable);
          final TreeItem<Trades> root;
          root = new RecursiveTreeItem<Trades>(atts, RecursiveTreeObject::getChildren);
          traderstableview.getColumns().setAll(idcustomer, customername, creationdate, status);
          traderstableview.setRoot(root);
          traderstableview.setShowRoot(false);
      }

}
  //cust
  	 private void settableviewCustomers() {
           JFXTreeTableColumn<Customers, String> name = new JFXTreeTableColumn<>("Name");
          name.setPrefWidth(150);
          name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
              @Override
              public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
                  return param.getValue().getValue().getName();
              }});
          
           JFXTreeTableColumn<Customers, String> login = new JFXTreeTableColumn<>("Login");
          login.setPrefWidth(150);
          login.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
              @Override
              public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
                  return param.getValue().getValue().getLogin();
              }});
          
          
            JFXTreeTableColumn<Customers, String> email = new JFXTreeTableColumn<>("Email");
          email.setPrefWidth(150);
          email.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
              @Override
              public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
                  return param.getValue().getValue().getEmail();
              }});
          
          JFXTreeTableColumn<Customers, String> phone = new JFXTreeTableColumn<>("Phone");
          phone.setPrefWidth(150);
          phone.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
              @Override
              public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
                  return param.getValue().getValue().getPhone();
              }});
          
          JFXTreeTableColumn<Customers, String> nationality = new JFXTreeTableColumn<>("Nationality");
          nationality.setPrefWidth(150);
          nationality.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
              @Override
              public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
                  return param.getValue().getValue().getNationality();
              }});
          
          JFXTreeTableColumn<Customers, String> address = new JFXTreeTableColumn<>("Address");
          address.setPrefWidth(150);
          address.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
              @Override
              public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
                  return param.getValue().getValue().getAddress();
              }});
          
          JFXTreeTableColumn<Customers, String> birthdate = new JFXTreeTableColumn<>("Birth Date");
          birthdate.setPrefWidth(150);
          birthdate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
              @Override
              public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
                  return param.getValue().getValue().getBirthdate();
              }});
          
          JFXTreeTableColumn<Customers, String> creationdate = new JFXTreeTableColumn<>("Since");
          creationdate.setPrefWidth(150);
          creationdate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
              @Override
              public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
                  return param.getValue().getValue().getCreationdate();
              }});
          
          JFXTreeTableColumn<Customers, String> activee = new JFXTreeTableColumn<>("Active");
          activee.setPrefWidth(150);
          activee.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
              @Override
              public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
                  return param.getValue().getValue().getActivee();
              }});
          
          JFXTreeTableColumn<Customers, String> risk = new JFXTreeTableColumn<>("Risk");
         risk.setPrefWidth(150);
          risk.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
              @Override
              public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
                  return param.getValue().getValue().getRisk();
              }});
          
          JFXTreeTableColumn<Customers, String> id = new JFXTreeTableColumn<>("id");
          id.setPrefWidth(150);
          id.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
               @Override
               public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
                   return param.getValue().getValue().getRisk();
               }});
          JFXTreeTableColumn<Customers, String> idTr = new JFXTreeTableColumn<>("idtr");
          idTr.setPrefWidth(150);
          idTr.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
               @Override
               public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
                   return param.getValue().getValue().getIdTr();
               }});
          JFXTreeTableColumn<Customers, String> nameTr = new JFXTreeTableColumn<>("Trader");
          nameTr.setPrefWidth(150);
          nameTr.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
               @Override
               public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
                   return param.getValue().getValue().getNameTr();
               }});
          JFXTreeTableColumn<Customers, String> company = new JFXTreeTableColumn<>("Comapny");
          company.setPrefWidth(150);
          company.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
               @Override
               public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
                   return param.getValue().getValue().getCompany();
               }});
          ObservableList<Customers> atts = FXCollections.observableArrayList();
         
          List<Customer> customers= service.getalltradercustomersbyid(iduserprofile);
              for (Customer t : customers) {
              Customers atable;
              String namea= t.getFirstName()+" "+t.getLastName();
              String activ;
              String bann;
              if(t.getIsactive()==0){
              	 activ="Not active";
              }
              else{
              	activ="Active";
              }
              String companyn = "";
              if (t.getCompany()!=null){
              	companyn=t.getCompany().getName();
              
              }
           
             
              String addr=t.getAddress().getSountry()+" "+t.getAddress().getState()+" "+t.getAddress().getStreet()+" "+t.getAddress().getZipcode();
              String traderCustid= t.getTrader().getId().toString();
              String traderCustname= t.getTrader().getFirstName()+" "+t.getTrader().getLastName();
            
               atable = new tn.esprit.sltsclient.Utils.Customers(namea, t.getLogin(),Integer.toString(t.getPhone()), t.getNationality(),
       				addr, t.getBirthdate().toString(), t.getCreationDate().toString(), activ, t.getEmail(), 
      				t.getRisk().toString(), t.getId().toString(),traderCustid, traderCustname, companyn);
               
               
              atts.add(atable);
              
              
              final TreeItem<Customers> root;
              root = new RecursiveTreeItem<Customers>(atts, RecursiveTreeObject::getChildren);
              customerstableview.getColumns().setAll(name, login, phone, nationality, address, birthdate, creationdate, activee, email, risk, company);
              customerstableview.setRoot(root);
              customerstableview.setShowRoot(false);
          }
              
              
             
             
    
}
  	 
  	
  	 private void setNode(Node node) {
	    	rootpane.getChildren().clear();
	        rootpane.setOpacity(0);
	        new FadeInTransition(rootpane).play();
	        rootpane.getChildren().clear();
	        rootpane.getChildren().add((Node) node);
	    }
    @FXML
    void backClicked(MouseEvent event) throws IOException {
    	  AnchorPane usman = FXMLLoader.load(getClass().getResource(nav.getUsermanagement()));
        
        
       setNode(usman);
    }
    
    
    
    //trades , customer profile 
    private void settableviewTradesforcustomerprofile() {
    	JFXTreeTableColumn<Trades, String> couponrate = new JFXTreeTableColumn<>("couponrate");
        couponrate.setPrefWidth(150);
         couponrate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Trades, String>, ObservableValue<String>>() {
             @Override
             public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Trades, String> param) {
                 return param.getValue().getValue().couponrate;
             }});
         JFXTreeTableColumn<Trades, String> parvalue = new JFXTreeTableColumn<>("Par value");
         parvalue.setPrefWidth(150);
          parvalue.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Trades, String>, ObservableValue<String>>() {
              @Override
              public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Trades, String> param) {
                  return param.getValue().getValue().parvalue;
              }});
          JFXTreeTableColumn<Trades, String> salesprice = new JFXTreeTableColumn<>("Sale price");
          salesprice.setPrefWidth(150);
           salesprice.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Trades, String>, ObservableValue<String>>() {
               @Override
               public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Trades, String> param) {
                   return param.getValue().getValue().salesprice;
               }});
           JFXTreeTableColumn<Trades, String> maturitydate = new JFXTreeTableColumn<>("Maturity date");
           maturitydate.setPrefWidth(150);
            maturitydate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Trades, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Trades, String> param) {
                    return param.getValue().getValue().maturitydate;
                }});
        JFXTreeTableColumn<Trades, String> idcustomer = new JFXTreeTableColumn<>("id customer");
       idcustomer.setPrefWidth(150);
       idcustomer.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Trades, String>, ObservableValue<String>>() {
           @Override
           public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Trades, String> param) {
               return param.getValue().getValue().idcustomer;
           }});
       
        JFXTreeTableColumn<Trades, String> customername = new JFXTreeTableColumn<>("customer name");
       customername.setPrefWidth(150);
       customername.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Trades, String>, ObservableValue<String>>() {
           @Override
           public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Trades, String> param) {
               return param.getValue().getValue().customername;
           }});
       
       
         JFXTreeTableColumn<Trades, String> creationdate = new JFXTreeTableColumn<>("Creation date");
       creationdate.setPrefWidth(150);
       creationdate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Trades, String>, ObservableValue<String>>() {
           @Override
           public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Trades, String> param) {
               return param.getValue().getValue().creationdate;
           }});
       
       JFXTreeTableColumn<Trades, String> status = new JFXTreeTableColumn<>("status");
      status.setPrefWidth(150);
       status.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Trades, String>, ObservableValue<String>>() {
           @Override
           public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Trades, String> param) {
               return param.getValue().getValue().status;
           }});
  
       ObservableList<Trades> atts = FXCollections.observableArrayList();
       List<TradingExchange> lte= service.getalltrades(iduserprofile);
       for (TradingExchange t : lte) {
     	  Trades atable;
           String namea= t.getUser().getFirstName()+" "+t.getUser().getLastName();
           String statust="";
          
           if (t.getStatus()!=null){
         	  statust=t.getStatus().toString();
           }
           Bond bond= (Bond) t.getInstrument();
           
            atable = new tn.esprit.sltsclient.Utils.Trades(t.getUser().getId().toString(),namea,t.getCreationDate().toString(), statust, bond.getCouponrate().toString(), bond.getParvalue().toString(), bond.getSaleprice().toString(), bond.getMaturitydate().toString() );
            atts.add(atable);
           final TreeItem<Trades> root;
           root = new RecursiveTreeItem<Trades>(atts, RecursiveTreeObject::getChildren);
           traderstableview.getColumns().setAll(idcustomer, customername, creationdate, status, couponrate, parvalue, salesprice, maturitydate);
           traderstableview.setRoot(root);
           traderstableview.setShowRoot(false);
       }

 }

}
