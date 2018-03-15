/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.sltsclient.Controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import tn.esprit.SLTS_server.persistence.Customer;
import tn.esprit.SLTS_server.persistence.Trader;
import tn.esprit.SLTS_server.persistence.User;
import tn.esprit.SLTS_server.services.UserServiceRemote;
import tn.esprit.sltsclient.Utils.Navigation;
import tn.esprit.sltsclient.main.CurrencyConvert;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import javafx.scene.input.MouseEvent;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import javafx.scene.control.TreeTableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.sltsclient.Utils.*;
import javafx.beans.value.ObservableValue;
import javafx.util.Callback;
import javafx.scene.control.TreeItem;
/**
 * FXML Controller class
 *
 * @author AGORA
 */
public class HomeManagementController implements Initializable {
	  @FXML
	    private Label nameactivetrader;

	    @FXML
	    private Label acctivetradergetnbtrades;

	    @FXML
	    private Label activetradernbcustomers;

	    @FXML
	    private Label lazytradername;

	    @FXML
	    private Label lazytradernbtrades;

	    @FXML
	    private Label lazytradernbcustomers;

	    @FXML
	    private Label newestcustomername;

	    @FXML
	    private Label newestcustomertrader;

	    @FXML
	    private Label moreinfountleave;

	    @FXML
	    private Label untreatedleavesnb;

	    @FXML
	    private Label nbbanned;

	    @FXML
	    private Label customerstoapprove;

	    @FXML
	    private Label customerstorefuse;

	    @FXML
	    private Label moreinfountleave1;

	    @FXML
	    private Label customerstodelete;

	    @FXML
	    private JFXTextField symbole;

	    @FXML
	    private JFXButton daily1;

	    @FXML
	    private JFXButton daily5;

	    @FXML
	    private JFXButton yearly1;

	    @FXML
	    private JFXButton yearly5;

	    @FXML
	    private JFXButton monthly6;

	    @FXML
	    private JFXButton monthly3;

	    @FXML
	    private JFXButton btngo;

	    @FXML
	    private Label symbolyf;

	    @FXML
	    private Label nameyf;

	    @FXML
	    private Label currencyyf;

	    @FXML
	    private Label stockexchangeyf;

    @FXML
    private JFXTreeTableView<Stocks> tableviewstocks;

	    String jndiName = "SLTS_server-ear/SLTS_server-ejb/UserService!tn.esprit.SLTS_server.services.UserServiceRemote";
	     Context context;
		UserServiceRemote service;
		List<User>custs;
		List<User> liste;
		Navigation nav= new Navigation();
	    @FXML
	    void untleavemoreinfo(MouseEvent event) {

	    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	try {

			populateactivetrader();
    		liste=service.findAllCustomers();
    		custs= service.findAlldesactiatedCustomers();
			populatelazytrader();
			newestcustomerpopulate();
			 populatenotifications();
		} catch (NamingException e) {
			 Logger.getLogger(HomeManagementController.class.getName()).log(Level.
					  SEVERE, null, e); 
		}

    	suggestionspopulate();
       
    }
	private void populateactivetrader() throws NamingException {
		context = new InitialContext();
		service = (UserServiceRemote) context.lookup(jndiName);
		Trader trader=service.findtraderactivelazynbtrades("desc");
		nameactivetrader.setText(trader.getFirstName()+" "+trader.getLastName());
		
		
		Integer nbcust = trader.getCustomers().size();
		activetradernbcustomers.setText(nbcust.toString());
		acctivetradergetnbtrades.setText(service.getnbtradesforactivelazytrader("desc").toString());
		
	}    
	private void populatelazytrader() {
		
		Trader trader=service.findtraderactivelazynbtrades("asc");
		lazytradername.setText(trader.getFirstName()+" "+trader.getLastName());
		
		
		Integer nbcust = trader.getCustomers().size();
		lazytradernbcustomers.setText(nbcust.toString());
		lazytradernbtrades.setText(service.getnbtradesforactivelazytrader("asc").toString());
		
	}    
	private void newestcustomerpopulate(){
		 
		if (!liste.isEmpty() && liste.get(0) instanceof Customer )
		
			
			{Customer customer=(Customer) liste.get(0);
			newestcustomername.setText(customer.getFirstName()+" "+customer.getLastName());
			newestcustomertrader.setText(customer.getTrader().getFirstName()+" "+customer.getTrader().getLastName());
			}
			
	
	
	}
	
	private void populatenotifications(){
		
		
		untreatedleavesnb.setText(Integer.toString(custs.size()));	
		Long nbb=service.getNbBannedTraders();
		nbbanned.setText(nbb.toString());
		
	}
	public String getcountrycodefromcountry(String country){
		String currentcountrycode="";	
		String[] locales = Locale.getISOCountries();

		for (String countryCode : locales) {

			Locale obj = new Locale("", countryCode);

			System.out.println("Country Code = " + obj.getCountry() + ", Country Name = " + obj.getDisplayCountry());
			if (obj.getDisplayCountry().equals(country)){
				currentcountrycode=obj.getCountry();
				return currentcountrycode;
                          }

		}
		return currentcountrycode;
		
	}
	
	public void suggestionspopulate(){
	    
		 CurrencyConvert convert = new CurrencyConvert();
	        Map<String, String> countries = new HashMap<>();
	        for (String iso : Locale.getISOCountries()) {
	            Locale l = new Locale("", iso);
	            countries.put(l.getDisplayCountry(Locale.ENGLISH), iso);
	           
	        }
		        List<Customer> customerstorefus= new ArrayList<>();
		        List<Customer> customerstoaccept= new ArrayList<>();
		        List<Customer> customerstodelet= new ArrayList<>();
		    Date currentDate= new Date();
		    List<User> custs= service.findAlldesactiatedCustomers();
		   
		  
			if (!custs.isEmpty()){
				for (User user : custs) {
					if (user instanceof Customer)
					{
						
						Customer c=(Customer)user;
						System.out.println(c.getAddress().getSountry());
						  String fromCountry = c.getAddress().getSountry();
		                    String toCountry = "New Zealand";
		                    String toCurrency  = Currency.getInstance(new Locale("",countries.get(toCountry))).getCurrencyCode();
		                    String fromCurrency = Currency.getInstance(new Locale("",countries.get(fromCountry))).getCurrencyCode();
		                    
		                
		                    double rate = convert.convert(fromCurrency,toCurrency);
		                            rate = rate * Double.parseDouble("1");
		                            System.out.println(rate);
		                            if (rate<1000 )
		                            {
		                            	if ( c.getCreationDate().getMonth()< currentDate.getMonth() && c.getCreationDate().getYear() < currentDate.getYear())
		                            	{customerstorefus.add(c);}
		                            	else {
		                            		customerstodelet.add(c);
		                            	}
		                            }
		                            else {
		                            	customerstoaccept.add(c);
		                            }
		    	    
							
					}
				}
				
				
			}
			Integer nbapprove=customerstoaccept.size();
			customerstoapprove.setText(nbapprove.toString());
			
			Integer nbarefuse=customerstorefus.size();
			customerstorefuse.setText(nbarefuse.toString());
			
			Integer nbdelete=customerstodelet.size();
			customerstodelete.setText(nbdelete.toString());
		
	}
public void searchforStocksbysymbol(String period , int num){
	Stock stock = null;
	if (period.equals("none") && num==0 )
	 {
		try {
			stock = YahooFinance.get(symbole.getText(),true);
		} catch (IOException ex) {
			nav.showAlert(Alert.AlertType.WARNING, "Warning", null, "Symbol not found!!");
			symbole.requestFocus();
     }}
	else {
		try {
			Calendar from = Calendar.getInstance();
			Calendar to = Calendar.getInstance();
			if(period.equals("Month")){
			from.add(Calendar.MONTH, -num);
			stock= YahooFinance.get("GOOG", from, to, Interval.MONTHLY);}
			else if (period.equals("Year")){
				from.add(Calendar.YEAR, -num);
				stock= YahooFinance.get("GOOG", from, to, Interval.WEEKLY);}
			else if (period.equals("Day")){
				from.add(Calendar.DAY_OF_YEAR, -num);
				stock= YahooFinance.get("GOOG", from, to, Interval.DAILY);}
			
			
		} catch (IOException ex) {
			nav.showAlert(Alert.AlertType.WARNING, "Warning", null, "Symbol not found!!");
			symbole.requestFocus();
     }
	}
		
		if (stock!=null)
		{System.out.println("currency"+stock.getCurrency());
		System.out.println("name"+stock.getName());
		System.out.println("stock exchange"+stock.getStockExchange());
		System.out.println("symbol "+stock.getSymbol());
		
		symbolyf.setText(stock.getSymbol());
		nameyf.setText(stock.getName());
		stockexchangeyf.setText(stock.getStockExchange());
		currencyyf.setText(stock.getCurrency());
     pupulatetableviewstocks(stock);}

}
	 @FXML
	    void btngoclicked(ActionEvent event)  {
           searchforStocksbysymbol("none",0);

	    }

	 
	    private void pupulatetableviewstocks(Stock stock) {
	    	 JFXTreeTableColumn<Stocks, String> adjclose = new JFXTreeTableColumn<>("Adj close");
	         adjclose.setPrefWidth(150);
	        adjclose.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
	             @Override
	             public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> param) {
	                 return param.getValue().getValue().adjclose;
	             }});
	        
	        JFXTreeTableColumn<Stocks, String> close = new JFXTreeTableColumn<>("Close");
	        close.setPrefWidth(150);
	        close.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
	             @Override
	             public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> param) {
	                 return param.getValue().getValue().close;
	             }});
	        
	        JFXTreeTableColumn<Stocks, String> date = new JFXTreeTableColumn<>("Date");
	        date.setPrefWidth(150);
	        date.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
	             @Override
	             public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> param) {
	                 return param.getValue().getValue().date;
	             }});
	        
	        JFXTreeTableColumn<Stocks, String> highh = new JFXTreeTableColumn<>("High");
	        highh.setPrefWidth(150);
	        highh.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
	             @Override
	             public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> param) {
	                 return param.getValue().getValue().highh;
	             }});
	        JFXTreeTableColumn<Stocks, String> low = new JFXTreeTableColumn<>("Low");
	        low.setPrefWidth(150);
	        low.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
	             @Override
	             public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> param) {
	                 return param.getValue().getValue().low;
	             }});
	        JFXTreeTableColumn<Stocks, String> open = new JFXTreeTableColumn<>("Open");
	        open.setPrefWidth(150);
	        open.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
	             @Override
	             public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> param) {
	                 return param.getValue().getValue().open;
	             }});
	        JFXTreeTableColumn<Stocks, String> volume = new JFXTreeTableColumn<>("Volume");
	        volume.setPrefWidth(150);
	        volume.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
	             @Override
	             public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> param) {
	                 return param.getValue().getValue().volume;
	             }});
	        
	         
	        
	        
	        /////
	        
	        
	        
	        ObservableList<Stocks> leaves = FXCollections.observableArrayList();
	    	List<HistoricalQuote> listehq=new ArrayList<HistoricalQuote>();
			try {
				listehq = stock.getHistory();
				
			} catch (IOException e) {
			
				e.printStackTrace();
			}
			System.out.println("hhdjdjdjjd");
			for (HistoricalQuote historicalQuote : listehq) {
				System.out.println("symbol "+historicalQuote.getSymbol());
				System.out.println("adj close"+historicalQuote.getAdjClose());
				System.out.println("close " +historicalQuote.getClose());
				System.out.println("date "+historicalQuote.getDate().getTime());
				System.out.println("high"+historicalQuote.getHigh());
				System.out.println("low "+historicalQuote.getLow());
				System.out.println("open "+historicalQuote.getOpen());
				System.out.println("volume "+historicalQuote.getVolume());
				
				
			}
	     
	         for (HistoricalQuote historicalQuote : listehq) {
	        	 System.out.println("ttttttttttttttttttttttttttttttttttttttttttt");
	            System.out.println(historicalQuote.getClose());
	            System.out.println("ttttttttttttttttttttttttttttttttttttttttttt");
	            if (historicalQuote.getClose()!=null)
	            {
	            	System.out.println("nn");
	                System.out.println(historicalQuote.getClose());
	            	Stocks ltable=new Stocks(historicalQuote.getAdjClose().toString(), historicalQuote.getClose().toString(), historicalQuote.getDate().getTime().toString(), historicalQuote.getHigh().toString(), historicalQuote.getLow().toString(), historicalQuote.getOpen().toString(), historicalQuote.getVolume().toString());
	             leaves.add(ltable);}
	         }
	        //leaves.add(new Leavess("2012", "kkkk", "hjj", "jjj", "jjjj"));   leaves.add(new Leavess("2012", "kkkk", "hjj", "jjj", "jjjj"));

	         final TreeItem<Stocks> root;
	         root = new RecursiveTreeItem<Stocks>(leaves, RecursiveTreeObject::getChildren);
	         tableviewstocks.getColumns().setAll(adjclose,close,date,highh,low,open,volume);
	         tableviewstocks.setRoot(root);
	         tableviewstocks.setShowRoot(false);

	      
	        
	        //////
		
	}
		@FXML
	    void daily1Clicked(ActionEvent event) {
			if(symbole.getText().equals("")){
	    		nav.showAlert(Alert.AlertType.WARNING, "Warning", null, "Symbol missing!!");
				symbole.requestFocus();
	    	}
	    	else {
	    		searchforStocksbysymbol("Day", 1);
	    		
	    	}

	    }

	    @FXML
	    void daily5clicked(ActionEvent event) {
	    	if(symbole.getText().equals("")){
	    		nav.showAlert(Alert.AlertType.WARNING, "Warning", null, "Symbol missing!!");
				symbole.requestFocus();
	    	}
	    	else {
	    		searchforStocksbysymbol("Day", 5);
	    		
	    	}

	    }

	    @FXML
	    void monthly3clicked(ActionEvent event) {
	    	if(symbole.getText().equals("")){
	    		nav.showAlert(Alert.AlertType.WARNING, "Warning", null, "Symbol missing!!");
				symbole.requestFocus();
	    	}
	    	else {
	    		searchforStocksbysymbol("Month", 3);
	    		
	    	}

	    }

	    @FXML
	    void monthly6clicked(ActionEvent event) {
	    	if(symbole.getText().equals("")){
	    		nav.showAlert(Alert.AlertType.WARNING, "Warning", null, "Symbol missing!!");
				symbole.requestFocus();
	    	}
	    	else {
	    		searchforStocksbysymbol("Month", 6);
	    		
	    	}

	    }

	   

	    @FXML
	    void yearly1clicked(ActionEvent event) {
	    	if(symbole.getText().equals("")){
	    		nav.showAlert(Alert.AlertType.WARNING, "Warning", null, "Symbol missing!!");
				symbole.requestFocus();
	    	}
	    	else {
	    		searchforStocksbysymbol("Year", 1);
	    		
	    	}

	    }

	    @FXML
	    void yearly5clicked(ActionEvent event) {
	    	if(symbole.getText().equals("")){
	    		nav.showAlert(Alert.AlertType.WARNING, "Warning", null, "Symbol missing!!");
				symbole.requestFocus();
	    	}
	    	else {
	    		searchforStocksbysymbol("Year", 5);
	    		
	    		
	    	}

	    }
	    
	    
    
}
