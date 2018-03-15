/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.sltsclient.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import tn.esprit.SLTS_server.persistence.Customer;
import tn.esprit.SLTS_server.persistence.Trader;
import tn.esprit.SLTS_server.persistence.User;
import tn.esprit.SLTS_server.services.UserServiceRemote;
import tn.esprit.sltsclient.Utils.FadeInTransition;
import tn.esprit.sltsclient.Utils.Mail;
import tn.esprit.sltsclient.Utils.MailConstruction;
import tn.esprit.sltsclient.Utils.Navigation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import javafx.scene.control.TitledPane;
import javafx.scene.control.MenuItem;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.*;
import javafx.scene.control.TreeTableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.jfoenix.controls.RecursiveTreeItem;
import javafx.scene.control.TreeItem;
import javafx.fxml.FXMLLoader;
import tn.esprit.sltsclient.Utils.Customers;
/**
 * FXML Controller class
 *
 * @author Fatma Jaafar
 */
public class UsermanagementController implements Initializable {

	  @FXML
	    private VBox hboxcards1;

	    @FXML
	    private AnchorPane emp1, rootpane;

	    @FXML
	    private Label id1;

	    @FXML
	    private Label role1,nbnotactivecustomers, nbbannedtr;

	    @FXML
	    private Label creadate1;

	    @FXML
	    private Label phone1;

	    @FXML
	    private Label text1;

	    @FXML
	    private Label nati1;

	    @FXML
	    private AnchorPane emp3;

	    @FXML
	    private Label id3;

	    @FXML
	    private Label role3;

	    @FXML
	    private Label creadate3;

	    @FXML
	    private Label phone3;

	    @FXML
	    private Label text3;

	    @FXML
	    private Label nati3;

	    @FXML
	    private VBox hboxcards2;

	    @FXML
	    private AnchorPane emp2;

	    @FXML
	    private Label id2;

	    @FXML
	    private Label role2;

	    @FXML
	    private Label creadate2;

	    @FXML
	    private Label phone2;

	    @FXML
	    private Label text2;

	    @FXML
	    private Label nati2;

	    @FXML
	    private AnchorPane emp4;

	    @FXML
	    private Label id4;

	    @FXML
	    private Label role4;

	    @FXML
	    private Label creadate4;

	    @FXML
	    private Label phone4;

	    @FXML
	    private Label text4;

	    @FXML
	    private Label nati4;

	    @FXML
	    private Pagination pagination;

	    @FXML
	    private JFXTextField search;

	    @FXML
	    private JFXRadioButton viewcustomers;

	    @FXML
	    private JFXRadioButton viewtraders;

	    @FXML
	    private JFXRadioButton cardsviewstyle;

	    @FXML
	    private JFXRadioButton tableviewstyle;

	    @FXML
	    private TitledPane ltraderslistPane;

	    @FXML
	    private JFXTreeTableView<Traders> tableviewtraders;

	    @FXML
	    private TitledPane customerslistPane;

	  @FXML
	    private JFXTreeTableView<Customers> tableviewcustomers;
	  
	  @FXML
	    private MenuItem activatecustomer;

	    @FXML
	    private MenuItem refuseactivatecutomer;
	    @FXML
	    private MenuItem bantrader;
	    @FXML
	    private MenuItem deletetrader;
	    @FXML
	    private MenuItem activatetrader;
	    @FXML
	    private MenuItem deletecustomer;

	   Navigation nav= new Navigation();

	String viewset = "";
	String viewset2 = "";
	String jndiName = "SLTS_server-ear/SLTS_server-ejb/UserService!tn.esprit.SLTS_server.services.UserServiceRemote";
	List<User> listeuserr;
	UserServiceRemote service;
	Context context;
	String roleuser = null;
	/**getnbcust
	 *  */
	
	public void nbnotactivatedCustomers(){
	nbnotactivecustomers.setText(service.getNbCustomersNotActivated().toString());
	}
	public void NbbannedTrader(){
		nbbannedtr.setText(service.getNbBannedTraders().toString());
		}
	
	/**/
	
	
/***/
	public void populateusers() throws NamingException {
		context = new InitialContext();
		service = (UserServiceRemote) context.lookup(jndiName);
		listeuserr = service.findAllUsers();
	}

	public int itemsPerPage() {
		return 4;
	}

	public AnchorPane createPage(int pageIndex) {

		AnchorPane box = new AnchorPane();
		System.out.println("page Index   :" + pageIndex);

		int page = pageIndex * itemsPerPage();
		System.out.println("page    :" + page);

		for (int i = page; i < page + itemsPerPage(); i = i + 4) {
			int j = i + 1;
			int k = i + 2;
			int r = i + 3;
			System.out.println("iiiii  " + i);
			System.out.println("j  " + j);
			System.out.println("k  " + k);
			System.out.println("r  " + r);
			if (i >= listeuserr.size()) {
				emp1.setVisible(false);
				emp2.setVisible(false);
				emp3.setVisible(false);
				emp4.setVisible(false);
				break;
			} else {
				emp1.setVisible(true);
				emp2.setVisible(true);
				emp3.setVisible(true);
				emp4.setVisible(true);
				text1.setText(listeuserr.get(i).getFirstName() + " " + listeuserr.get(i).getLastName());
				id1.setText(listeuserr.get(i).getId().toString());
				
				if (listeuserr.get(i) instanceof Trader) {
					roleuser = "Trader";
				} else if (listeuserr.get(i) instanceof Customer) {
					roleuser = "Customer";
				}
				role1.setText(roleuser);
				nati1.setText(listeuserr.get(i).getNationality());
				creadate1.setText(listeuserr.get(i).getNationality().toString());
				phone1.setText(listeuserr.get(i).getPhone().toString());


			}
			if (j >= listeuserr.size()) {
				emp2.setVisible(false);
				emp3.setVisible(false);
				emp4.setVisible(false);
				break;
			}

			else

			{
				emp2.setVisible(true);
				emp3.setVisible(true);
				emp4.setVisible(true);

				text2.setText(listeuserr.get(i + 1).getFirstName() + " " + listeuserr.get(i + 1).getLastName());
				id2.setText(listeuserr.get(i + 1).getId().toString());
				
				if (listeuserr.get(i + 1) instanceof Trader) {
					roleuser = "Trader";
				} else if (listeuserr.get(i + 1) instanceof Customer) {
					roleuser = "Customer";
				}
				role2.setText(roleuser);
				nati2.setText(listeuserr.get(i + 1).getNationality());
				creadate2.setText(listeuserr.get(i + 1).getNationality());
				phone2.setText(listeuserr.get(i + 1).getPhone().toString());

			}
			if (k >= listeuserr.size()) {
				emp3.setVisible(false);
				emp4.setVisible(false);

				break;
			}

			else {

				emp3.setVisible(true);
				emp4.setVisible(true);

				text3.setText(listeuserr.get(i + 2).getFirstName() + " " + listeuserr.get(i + 2).getLastName());

				id3.setText(listeuserr.get(i + 2).getId().toString());
				
				if (listeuserr.get(i + 2) instanceof Trader) {
					roleuser = "Trader";
				} else if (listeuserr.get(i + 2) instanceof Customer) {
					roleuser = "Customer";
				}
				role3.setText(roleuser);
				nati3.setText(listeuserr.get(i + 2).getNationality());
				creadate3.setText(listeuserr.get(i + 2).getNationality().toString());
				phone3.setText(listeuserr.get(i + 2).getPhone().toString());

			}
			if (r >= listeuserr.size()) {

				emp4.setVisible(false);

				break;
			}

			else {

				emp4.setVisible(true);

				text4.setText(listeuserr.get(i + 3).getFirstName() + " " + listeuserr.get(i + 2).getLastName());
				id4.setText(listeuserr.get(i + 3).getId().toString());
				
				if (listeuserr.get(i + 3) instanceof Trader) {
					roleuser = "Trader";
				} else if (listeuserr.get(i + 3) instanceof Customer) {
					roleuser = "Customer";
				}
				role4.setText(roleuser);
				nati4.setText(listeuserr.get(i + 3).getNationality());
				creadate4.setText(listeuserr.get(i + 3).getNationality().toString());
				phone4.setText(listeuserr.get(i + 3).getPhone().toString());
			}
		}
		return box;
	}

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

			try {
				populateusers();
			} catch (NamingException e) {
			
				 Logger.getLogger(UsermanagementController.class.getName()).log(Level.
						  SEVERE, null, e);
				 }
			
		
		nbnotactivatedCustomers();
		NbbannedTrader();
		ToggleGroup group = new ToggleGroup();
		viewcustomers.setToggleGroup(group);
		viewtraders.setToggleGroup(group);

		ToggleGroup group1 = new ToggleGroup();
		cardsviewstyle.setToggleGroup(group1);
		tableviewstyle.setToggleGroup(group1);

		group1.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (group1.getSelectedToggle() != null) {
					RadioButton button2 = (RadioButton) group1.getSelectedToggle();
					
					viewset2 = button2.getText();
					
					if (tableviewstyle.isSelected()) {
						settableviewTraders() ;
						settableviewCustomers();
						hboxcards1.setVisible(false);
						hboxcards2.setVisible(false);
						ltraderslistPane.setVisible(true);
						customerslistPane.setVisible(true);
						pagination.setVisible(false);
						
					} else if (cardsviewstyle.isSelected()) {
						hboxcards1.setVisible(true);
						hboxcards2.setVisible(true);
						ltraderslistPane.setVisible(false);
						customerslistPane.setVisible(false);
						pagination.setVisible(true);
						System.out.println("cardsss");

					}

				}
			}
		});

		
		
		
		
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (group.getSelectedToggle() != null) {

					RadioButton button1 = (RadioButton) group.getSelectedToggle();
				
					viewset = button1.getText();
					if (viewset.equals("Traders")) {

						listeuserr = service.findAllTraders();
					} else {
						listeuserr = service.findAllCustomers();

					}
					double a = Math.ceil((float) listeuserr.size() / 4);
					pagination.setPageCount((int) a);
					pagination.setCurrentPageIndex(0);
					
					pagination.setMaxPageIndicatorCount((int) a);
					

					pagination.setPageFactory((Integer pageIndex) -> {
						if (pageIndex >= listeuserr.size()) {
							return null;
						} else {
							return createPage(pageIndex);
						}
					});

				}
			}
		});

		search.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				
				listeuserr = service.SearchAllUsers(newValue);

				double a = Math.ceil((float) listeuserr.size() / 4);
				pagination.setPageCount((int) a);
				pagination.setCurrentPageIndex(0);
				
				pagination.setMaxPageIndicatorCount((int) a);
				

				pagination.setPageFactory((Integer pageIndex) -> {
					if (pageIndex >= listeuserr.size()) {
						return null;
					} else {
						return createPage(pageIndex);
					}
				});
			}
		});

		double a = Math.ceil((float) listeuserr.size() / 4);
		pagination.setPageCount((int) a);
		pagination.setCurrentPageIndex(0);
		
		pagination.setMaxPageIndicatorCount((int) a);
		

		pagination.setPageFactory((Integer pageIndex) -> {
			if (pageIndex >= listeuserr.size()) {
				return null;
			} else {
				return createPage(pageIndex);
			}
		});

	}
//tr
	 private void settableviewTraders() {
         JFXTreeTableColumn<Traders, String> name = new JFXTreeTableColumn<>("Name");
        name.setPrefWidth(150);
        name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Traders, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Traders, String> param) {
                return param.getValue().getValue().name;
            }});
        
         JFXTreeTableColumn<Traders, String> login = new JFXTreeTableColumn<>("Login");
        login.setPrefWidth(150);
        login.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Traders, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Traders, String> param) {
                return param.getValue().getValue().login;
            }});
        
        
          JFXTreeTableColumn<Traders, String> email = new JFXTreeTableColumn<>("Email");
        email.setPrefWidth(150);
        email.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Traders, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Traders, String> param) {
                return param.getValue().getValue().email;
            }});
        
        JFXTreeTableColumn<Traders, String> phone = new JFXTreeTableColumn<>("Phone");
        phone.setPrefWidth(150);
        phone.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Traders, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Traders, String> param) {
                return param.getValue().getValue().phone;
            }});
        
        JFXTreeTableColumn<Traders, String> nationality = new JFXTreeTableColumn<>("Nationality");
        nationality.setPrefWidth(150);
        nationality.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Traders, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Traders, String> param) {
                return param.getValue().getValue().nationality;
            }});
        
        JFXTreeTableColumn<Traders, String> address = new JFXTreeTableColumn<>("Address");
        address.setPrefWidth(150);
        address.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Traders, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Traders, String> param) {
                return param.getValue().getValue().address;
            }});
        
        JFXTreeTableColumn<Traders, String> birthdate = new JFXTreeTableColumn<>("Birth Date");
        birthdate.setPrefWidth(150);
        birthdate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Traders, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Traders, String> param) {
                return param.getValue().getValue().birthdate;
            }});
        
        JFXTreeTableColumn<Traders, String> creationdate = new JFXTreeTableColumn<>("Since");
        creationdate.setPrefWidth(150);
        creationdate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Traders, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Traders, String> param) {
                return param.getValue().getValue().creationdate;
            }});
        
        JFXTreeTableColumn<Traders, String> activee = new JFXTreeTableColumn<>("Active");
        activee.setPrefWidth(150);
        activee.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Traders, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Traders, String> param) {
                return param.getValue().getValue().activee;
            }});
        
        JFXTreeTableColumn<Traders, String> isbanned = new JFXTreeTableColumn<>("Bann");
       isbanned.setPrefWidth(150);
        isbanned.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Traders, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Traders, String> param) {
                return param.getValue().getValue().isbanned;
            }});
        
        JFXTreeTableColumn<Traders, String> tradertype = new JFXTreeTableColumn<>("Type");
        tradertype.setPrefWidth(150);
        tradertype.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Traders, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Traders, String> param) {
                return param.getValue().getValue().tradertype;
            }});
        
        JFXTreeTableColumn<Traders, String> id = new JFXTreeTableColumn<>("Type");
        id.setPrefWidth(150);
       id.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Traders, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Traders, String> param) {
                return param.getValue().getValue().id;
            }});
        ObservableList<Traders> atts = FXCollections.observableArrayList();
      
        
        listeuserr = service.findAllTraders();
        System.out.println(listeuserr);
        List<Trader> tradersss = new ArrayList<Trader>();
        for (User user : listeuserr) {
        	if (user instanceof Trader)
        	{Trader tr= (Trader)user;
        	
			tradersss.add(tr);}
		}
        for (Trader t : tradersss) {
            Traders atable;
            String namea= t.getFirstName()+" "+t.getLastName();
            String activ;
            String bann;
            if(t.getIsactive()==0){
            	 activ="Not active";
            }
            else{
            	activ="Active";
            }
            if (t.getIsbanned()==1){
            	bann="Banned";
            }
            else {
            	bann="Not Banned";
            }
            String addr=t.getAddress().getSountry()+" "+t.getAddress().getState()+" "+t.getAddress().getStreet()+" "+t.getAddress().getZipcode();
            
           
             atable = new Traders(namea, t.getLogin(),Integer.toString(t.getPhone()), t.getNationality(),
     				addr, t.getBirthdate().toString(), t.getCreationDate().toString(), activ, t.getEmail(), bann,
    				t.getTradertype(), t.getId().toString());
             
             
            atts.add(atable);
        }
     
        final TreeItem<Traders> root;
        root = new RecursiveTreeItem<Traders>(atts, RecursiveTreeObject::getChildren);
        tableviewtraders.getColumns().setAll(name, login, phone, nationality, address, birthdate, creationdate, activee, email, isbanned, tradertype);
        tableviewtraders.setRoot(root);
        tableviewtraders.setShowRoot(false);
 search.textProperty().addListener(new ChangeListener<String>() {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        	tableviewtraders.setPredicate(new Predicate<TreeItem<Traders>>() {

               @Override
               public boolean test(TreeItem<Traders> t) {
                  return t.getValue().name.getValue().contains(newValue)|| t.getValue().login.getValue().contains(newValue)|| t.getValue().phone.getValue().contains(newValue)
                                  ||t.getValue().nationality.getValue().contains(newValue);
               }

              
           });
        }
    });
        
    }
	// tab

	class Traders extends RecursiveTreeObject<Traders> {

		StringProperty name;
		StringProperty id;
		StringProperty login;
		StringProperty phone;
		StringProperty address;
		StringProperty nationality;
		StringProperty birthdate;
		StringProperty creationdate;
		StringProperty activee;
		StringProperty email;
		StringProperty isbanned;
		StringProperty tradertype;
		 
		public Traders(String name,String login, String phone, String nationality,
				String address, String birthdate, String creationdate, String activee, String email, String isbanned,
				String tradertype, String id) {
			this.name = new SimpleStringProperty(name);
			
			this.login = new SimpleStringProperty(login);
			this.phone = new SimpleStringProperty(phone);

			this.address = new SimpleStringProperty(address);
			this.nationality = new SimpleStringProperty(nationality);
			this.birthdate = new SimpleStringProperty(birthdate);
			this.creationdate = new SimpleStringProperty(creationdate);

			this.activee = new SimpleStringProperty(activee);
			this.email = new SimpleStringProperty(email);
			this.isbanned = new SimpleStringProperty(isbanned);
			this.tradertype = new SimpleStringProperty(tradertype);
			this.id = new SimpleStringProperty(id);
		}
		
		public StringProperty getId() {
			return id;
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
        
        JFXTreeTableColumn<Customers, String> id = new JFXTreeTableColumn<>("Id");
        id.setPrefWidth(150);
        id.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
             @Override
             public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
                 return param.getValue().getValue().getRisk();
             }});
        JFXTreeTableColumn<Customers, String> idTr = new JFXTreeTableColumn<>("Trader");
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
        JFXTreeTableColumn<Customers, String> company = new JFXTreeTableColumn<>("Company");
        company.setPrefWidth(150);
        company.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
             @Override
             public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
                 return param.getValue().getValue().getCompany();
             }});
        ObservableList<Customers> atts = FXCollections.observableArrayList();
       
        listeuserr = service.findAllCustomers();
       
        List<Customer> tradersss = new ArrayList<Customer>();
        for (User user : listeuserr) {
        	if (user instanceof Customer)
        	{Customer tr= (Customer)user;
        	
			tradersss.add(tr);}
		}
        for (Customer t : tradersss) {
            Customers atable;
            String namea= t.getFirstName()+" "+t.getLastName();
            String activ;
            String bann;
            String companyn = "";
            if(t.getIsactive()==0){
            	 activ="Not active";
            }
            else{
            	activ="Active";
            }
            if (t.getCompany()!=null){
            	companyn=t.getCompany().getName();
            
            }
         
          
           
            String addr=t.getAddress().getSountry()+" "+t.getAddress().getState()+" "+t.getAddress().getStreet()+" "+t.getAddress().getZipcode();
            String traderCustid= t.getTrader().getId().toString();
            String traderCustname= t.getTrader().getFirstName()+" "+t.getTrader().getLastName();
            
             atable = new Customers(namea, t.getLogin(),Integer.toString(t.getPhone()), t.getNationality(),
     				addr, t.getBirthdate().toString(), t.getCreationDate().toString(), activ, t.getEmail(), 
    				t.getRisk().toString(), t.getId().toString(),traderCustid, traderCustname, companyn);
             
             
            atts.add(atable);
        }
       /*(String name,String login, String phone, String nationality,
				String address, String birthdate, String creationdate, String activee, String email, String isbanned,
				String tradertype)*/
        final TreeItem<Customers> root;
        root = new RecursiveTreeItem<>(atts, RecursiveTreeObject::getChildren);
        tableviewcustomers.getColumns().setAll(name, login, phone, nationality, address, birthdate, creationdate, activee, email, risk, nameTr, company);
        tableviewcustomers.setRoot(root);
        tableviewcustomers.setShowRoot(false);
 search.textProperty().addListener(new ChangeListener<String>() {

        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        	tableviewcustomers.setPredicate(new Predicate<TreeItem<Customers>>() {

               @Override
               public boolean test(TreeItem<Customers> t) {
                  Boolean flag = t.getValue().getName().getValue().contains(newValue)|| t.getValue().getLogin().getValue().contains(newValue)|| t.getValue().getPhone().getValue().contains(newValue)
                          ||t.getValue().getNationality().getValue().contains(newValue)||t.getValue().getNameTr().getValue().contains(newValue);
                          
                          
                          
                          return flag;
               }

              
           });
        }
    });
        
    }

	
	
	
	 @FXML
	    void activatecustomerclicked(ActionEvent event) {
         Integer idCust=Integer.parseInt(tableviewcustomers.getSelectionModel().getSelectedItem().getValue().getId().getValue().toString());
		 
		 Customer c=(Customer)service.findUserById(idCust);
		 if(c.getIsactive()==1){
			 nav.showAlert(Alert.AlertType.WARNING, "Warning ", null, "Customer already actibe!!");
		 }
		 else {
			 c.setIsactive(1);
			 service.updateUser(c);
			 Notifications.create().title("ITradeIt ").text("Customer! "+c.getFirstName()+" "+c.getLastName()+"added with success").darkStyle().showInformation();
			 
			  String emailadr= c.getEmail();
		        String recipient= emailadr;
		         Mail mail = new Mail();
		        mail.setMailAddressRecipient(recipient);
		        mail.setPwd("a3outhouBellehminashaitanRajimBeslemmeh123***BESMELLEhYarab552");
		        
		        mail.setMailAddressSender("fatma.jaafar404@gmail.com");
		        mail.setMailSubject("Confirmed Registration ");
		      
		        String msg="Dear:"+c.getFirstName()+" "+c.getLastName()+"\nWelcome to ITrade It \n Your trader "+c.getTrader().getFirstName()+" "+c.getTrader().getLastName()+"\n is ready to earn some money for you \n ITradeIt Team.";
		       
		        mail.setMailObject(msg);
		    

		        MailConstruction mc = new MailConstruction();
		        mc.getMailProperties();

		        mc.getMailMessage( mail);
		        mc.SendMessage();
			 
		 }
		settableviewCustomers();

	    }

	    @FXML
	    void refusecustomerclicked(ActionEvent event) {
	    	
	    	 Integer idCust=Integer.parseInt(tableviewcustomers.getSelectionModel().getSelectedItem().getValue().getId().getValue());
			 System.out.println(idCust);
			 Customer c=(Customer)service.findUserById(idCust);
			 if(c.getIsactive()==2){
				 nav.showAlert(Alert.AlertType.WARNING, "Warning", null, "Customer already desactivated!!");
			 }
			 else {
				 c.setIsactive(2);
				 service.updateUser(c);
				 Notifications.create().title("ITradeIt").text("Customer! "+c.getFirstName()+" "+c.getLastName()+"added with success").darkStyle().showInformation();
				 
				  String emailadr= c.getEmail();
			        String recipient= emailadr;
			         Mail mail = new Mail();
			        mail.setMailAddressRecipient(recipient);
			        mail.setPwd("a3outhouBellehminashaitanRajimBeslemmeh123***BESMELLEhYarab552");
			        
			        mail.setMailAddressSender("fatma.jaafar404@gmail.com");
			        mail.setMailSubject("Refused Registration");
			      
			        String msg="Dear:  "+c.getFirstName()+" "+c.getLastName()+"\n we're sorry to  inform you that you're not accepted in ITradeIt, please contact your trader for more informations\n ITradeIt Team.";
			       
			        mail.setMailObject(msg);
			    

			        MailConstruction mc = new MailConstruction();
			        mc.getMailProperties();


			        mc.getMailMessage( mail);
			        mc.SendMessage();
				 
			 }
			 settableviewCustomers();

	    }
	
	    @FXML
	    void bantraderAction(ActionEvent event) {
	    	Integer idTr=Integer.parseInt(tableviewtraders.getSelectionModel().getSelectedItem().getValue().getId().getValue().toString());
			 System.out.println(idTr);
			Trader c=(Trader)service.findUserById(idTr);
			 if(c.getIsbanned()==1){
				 nav.showAlert(Alert.AlertType.WARNING, "Warning", null, "Trader already banned!!");
			 }
			 else {
				 c.setIsbanned(1);
				 service.updateUser(c);
				 Notifications.create().title("ITradeIt").text("Trader "+c.getFirstName()+" "+c.getLastName()+"is banned").darkStyle().showInformation();
				 
				  String emailadr= c.getEmail();
			        String recipient= emailadr;
			         Mail mail = new Mail();
			        mail.setMailAddressRecipient(recipient);
			        mail.setPwd("a3outhouBellehminashaitanRajimBeslemmeh123***BESMELLEhYarab552");
			        
			        mail.setMailAddressSender("fatma.jaafar404@gmail.com");
			        mail.setMailSubject("Confirmed Registration");
			      
			        String msg="Dear: "+c.getFirstName()+" "+c.getLastName()+"\nWelcome to ITrade It \n You've been banned to trade. Contact administration as soon as possible.\n ITradeIt Team";
			       
			        mail.setMailObject(msg);
			    

			        MailConstruction mc = new MailConstruction();
			        mc.getMailProperties();


			        mc.getMailMessage( mail);
			        mc.SendMessage();
				 
			 }
			
settableviewTraders();
	    }

	    @FXML
	    void activatetraderAction(ActionEvent event) {
	    	Integer idTr=Integer.parseInt(tableviewtraders.getSelectionModel().getSelectedItem().getValue().getId().getValue().toString());
			
			Trader c=(Trader)service.findUserById(idTr);
			 if(c.getIsbanned()==0){
				 nav.showAlert(Alert.AlertType.WARNING, "Warning", null, "Trader already active!!");
			 }
			 else {
				 c.setIsbanned(0);
				 service.updateUser(c);
				 Notifications.create().title("ITradeIt").text("Trader "+c.getFirstName()+" "+c.getLastName()+"is active again.").darkStyle().showInformation();
				 
				  String emailadr= c.getEmail();
			        String recipient= emailadr;
			         Mail mail = new Mail();
			        mail.setMailAddressRecipient(recipient);
			        mail.setPwd("a3outhouBellehminashaitanRajimBeslemmeh123***BESMELLEhYarab552");
			        
			        mail.setMailAddressSender("fatma.jaafar404@gmail.com");
			        mail.setMailSubject("Confirmed Registration");
			      
			        String msg="Dear: "+c.getFirstName()+" "+c.getLastName()+"\nWelcome to ITrade It \n Your Account is active again \n ITradeIt Team";
			       
			        mail.setMailObject(msg);
			    

			        MailConstruction mc = new MailConstruction();
			        mc.getMailProperties();


			        mc.getMailMessage( mail);
			        mc.SendMessage();
				 
			 }
			
      settableviewTraders();
	    }
	    private void setNode(Node node) {
	    	rootpane.getChildren().clear();
	        rootpane.setOpacity(0);
	        new FadeInTransition(rootpane).play();
	        rootpane.getChildren().clear();
	        rootpane.getChildren().add((Node) node);
	    }
	    @FXML
	    void tabletradersclicked(MouseEvent event) throws IOException {
	    	if(event.getClickCount() == 2)
	        {
	            Integer idt = Integer.parseInt(tableviewtraders.getSelectionModel().getSelectedItem().getValue().getId().getValue().toString());
	           
	            ProfileController.iduserprofile=idt;
	            AnchorPane profile = FXMLLoader.load(getClass().getResource(nav.getProfile()));
	           
	        
	            setNode(profile);
	            

	          
	        }
	    }
	    @FXML
	    void tabletcustomersclicked(MouseEvent event) throws IOException {
	    	if(event.getClickCount() == 2)
	        {
	            Integer idt = Integer.parseInt(tableviewcustomers.getSelectionModel().getSelectedItem().getValue().getId().getValue().toString());
	            
	            ProfileController.iduserprofile=idt;
	          
	        
	            rootpane.getChildren().clear();
	            rootpane.setOpacity(0);
	            new FadeInTransition(rootpane).play();
	            
	        
	        FXMLLoader loader = new FXMLLoader();

	            AnchorPane pane = null ;
	            loader.setLocation(getClass().getResource(nav.getProfile()));
	                       try {
	                           pane =loader.load();
	                       } catch (Exception e) {
	                       }
	                    
	              rootpane.getChildren().setAll(pane);
	               
	           
	        }
	    }
	    
	    @FXML
	    void deletetraderAction(ActionEvent event) {
	    	Integer idTr=Integer.parseInt(tableviewtraders.getSelectionModel().getSelectedItem().getValue().getId().getValue());
			
			Trader c=(Trader)service.findUserById(idTr);
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText("Look, a Confirmation Dialog");
			alert.setContentText("Are you ok with this?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
			
			 
				 service.deleteUserById(c.getId());
				 Notifications.create().title("ITradeIt").text("Trader "+c.getFirstName()+" "+c.getLastName()+"is deleted.").darkStyle().showInformation();
				 
				  String emailadr= c.getEmail();
			        String recipient= emailadr;
			         Mail mail = new Mail();
			        mail.setMailAddressRecipient(recipient);
			        mail.setPwd("a3outhouBellehminashaitanRajimBeslemmeh123***BESMELLEhYarab552");
			        
			        mail.setMailAddressSender("fatma.jaafar404@gmail.com");
			        mail.setMailSubject("Profile deleted");
			      
			        String msg="Dear: "+c.getFirstName()+" "+c.getLastName()+"\nYour Account has been deleted \n ITradeIt Team";
			       
			        mail.setMailObject(msg);
			    

			        MailConstruction mc = new MailConstruction();
			        mc.getMailProperties();


			        mc.getMailMessage( mail);
			        mc.SendMessage();
				 
			 
			
      settableviewTraders();
	    }}
	    
	    @FXML
	    void deletecustomerAction(ActionEvent event) {
	    	Integer idTr=Integer.parseInt(tableviewtraders.getSelectionModel().getSelectedItem().getValue().getId().getValue());
			
	    	Customer c=(Customer)service.findUserById(idTr);
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText("Look, a Confirmation Dialog");
			alert.setContentText("Are you ok with this?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
			
			 
				 service.deleteUserById(c.getId());
				 Notifications.create().title("ITradeIt").text("Trader "+c.getFirstName()+" "+c.getLastName()+"is deleted.").darkStyle().showInformation();
				 
				  String emailadr= c.getEmail();
			        String recipient= emailadr;
			         Mail mail = new Mail();
			        mail.setMailAddressRecipient(recipient);
			        mail.setPwd("a3outhouBellehminashaitanRajimBeslemmeh123***BESMELLEhYarab552");
			        
			        mail.setMailAddressSender("fatma.jaafar404@gmail.com");
			        mail.setMailSubject("Profile deleted");
			      
			        String msg="Dear: "+c.getFirstName()+" "+c.getLastName()+"\nYour Account has been deleted \n ITradeIt Team";
			       
			        mail.setMailObject(msg);
			    

			        MailConstruction mc = new MailConstruction();
			        mc.getMailProperties();


			        mc.getMailMessage( mail);
			        mc.SendMessage();
				 
			 
			
      settableviewTraders();
	    }}
	    
}
