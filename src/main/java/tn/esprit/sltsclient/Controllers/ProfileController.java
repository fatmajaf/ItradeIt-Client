/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.sltsclient.Controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.ScrollPane;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javafx.scene.control.TextInputDialog;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBuilder;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.Duration;
import tn.esprit.SLTS_server.persistence.Bond;
import tn.esprit.SLTS_server.persistence.Comment;
import tn.esprit.SLTS_server.persistence.Customer;
import tn.esprit.SLTS_server.persistence.Trader;
import tn.esprit.SLTS_server.persistence.TradingExchange;
import tn.esprit.SLTS_server.persistence.User;
import tn.esprit.SLTS_server.services.CommentServiceRemote;
import tn.esprit.SLTS_server.services.UserServiceRemote;
import tn.esprit.sltsclient.Utils.BadWordFilter;
import tn.esprit.sltsclient.Utils.Customers;
import tn.esprit.sltsclient.Utils.FadeInRightTransition;
import tn.esprit.sltsclient.Utils.FadeInTransition;
import tn.esprit.sltsclient.Utils.FadeOutLeftTransition;
import tn.esprit.sltsclient.Utils.Navigation;
import tn.esprit.sltsclient.Utils.SentimentAnalysisWithCount;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.text.DateFormatSymbols;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import tn.esprit.sltsclient.Utils.Trades;
import twitter4j.TwitterException;
import javafx.scene.control.Separator;
import javafx.scene.chart.PieChart;
import javafx.event.EventHandler;

/**
 * FXML Controller class
 *
 * @author AGORA
 */
public class ProfileController implements Initializable {
	HashMap<String, Integer> map;
	   @FXML
	    private JFXTextArea tweets;
	   @FXML
	    private Label positiveimp;

	    @FXML
	    private Label negativeimp;
	@FXML
    private AnchorPane twitterfeedanchor;
	@FXML
	private AnchorPane analysis;
	@FXML
	private PieChart chart1;

	@FXML
	private PieChart chart2;

	@FXML
	private PieChart chart3;
	@FXML
	private Label comment1;

	@FXML
	private Label comment2;

	@FXML
	private Label namecommenter1;

	@FXML
	private Label namecommenter2;
	@FXML
	private Label id1;

	@FXML
	private Label id2;

	@FXML
	private Label id3;

	@FXML
	private Label id4;
	@FXML
	private Label comment3;
	@FXML
	private Label comment4;
	@FXML
	private Label namecommenter3;
	@FXML
	private Label namecommenter4;
	@FXML
	private Pagination pagination;

	@FXML
	private JFXTextField commentadd;
	@FXML
	private ImageView commentsupp1;

	@FXML
	private ImageView commentsupp2;

	@FXML
	private ImageView commentsupp4;

	@FXML
	private ImageView commentsupp3;
	@FXML
	private ImageView commentedit1;

	@FXML
	private ImageView commentedit2;

	@FXML
	private ImageView commededit3;

	@FXML
	private ImageView commentedit4;

    @FXML
    private Label nbbannedcomments;
	List<Comment> comments;
	String jndiNamec = "SLTS_server-ear/SLTS_server-ejb/CommentService!tn.esprit.SLTS_server.services.CommentServiceRemote";
	Context contextc;
	CommentServiceRemote servicecommenr;
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
	private ImageView photo;
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
	private Label active;
	@FXML
	Label companynamegal;
	@FXML
	private ImageView back;
	@FXML
	private AnchorPane rootpane;

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
	Navigation nav = new Navigation();
	// nv
	@FXML
	private ImageView latesttradenotfound;
	@FXML
	private ImageView rightfl;
	@FXML
	private ImageView leftfl;
	@FXML
	private ImageView im;
	List<Customer> customerslistfortradr;
	int lengthcust;
	int indexgal;
	Customer[] tableau;

	@FXML
	void gorightgal(MouseEvent event) {
		indexgal++;
		if (indexgal >= lengthcust) {
			indexgal = 0;
		}
		companynamegal.setText(tableau[indexgal].getCompany().getName());
		new FadeInRightTransition(im).play();
		im.setVisible(true);
	}

	@FXML
	void goleftgal(MouseEvent event) {
		indexgal--;
		if (indexgal < 0) {
			indexgal = lengthcust - 1;
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
		try {
			contextc = new InitialContext();
		} catch (NamingException e1) {

			e1.printStackTrace();
		}
		try {
			servicecommenr = (CommentServiceRemote) contextc.lookup(jndiNamec);
		} catch (NamingException e1) {

			e1.printStackTrace();
		}
		// Get an array with the English month names.
		// Get an array with the English month names.
		String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
		// Convert it to a list and add it to our ObservableList of months.
		monthNames.addAll(Arrays.asList(months));

		// Assign the month names as categories for the horizontal axis.
		xAxis.setCategories(monthNames);

		System.out.println("im in profile ,  the id is " + iduserprofile);
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
		if (user.getPhoto()!=null){
			String localUrl = null;
			File file = new File(user.getPhoto());
	        try {
	           localUrl = file.toURI().toURL().toString();
	       } catch (MalformedURLException ex) {
	           Logger.getLogger(ProfileUserController.class.getName()).log(Level.SEVERE, null, ex);
	       }
	       
	        Image image = new Image(localUrl);
	  photo.setImage(image);
		}
		if (user instanceof Customer) {
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

		} else {
			System.out.println("its a Trader");
			labelcompany.setText("Companies");
			settableviewCustomers();
			setdatatrader();
			setchartDatatraderprofile();
			tab1.setText("Customers");
			tab2.setText("Trades");
			tab2.setDisable(false);
			tab3.setText("");
			tab3.setDisable(true);
			settableviewTradesforTraderprofile();
			latesttradepopulate();
			customerslistfortradr = service.getalltradercustomersbyid(iduserprofile);
			lengthcust = customerslistfortradr.size();
			indexgal = 0;
			tableau = new Customer[lengthcust];
			for (int i = 0; i < lengthcust; i++) {
				tableau[i] = customerslistfortradr.get(i);

			}
			if (lengthcust < 1) {
				companynamegal.setText("no companies");
				leftfl.setVisible(false);
				rightfl.setVisible(false);
			} else {
				companynamegal.setText(tableau[indexgal].getCompany().getName());
			}
		}

		preparecomments();
		commentsview();
		twitterfeedview();
	}

	public int itemsPerPage() {
		return 4;
	}

	public void preparecomments() {

		comments = servicecommenr.viewusercomments(user);
nbbannedcomments.setText(servicecommenr.getnbbannedcommentsbycommenterid(iduserprofile).toString());
		try {
			map = SentimentAnalysisWithCount.commentsanalysis(comments);
		} catch (TwitterException | IOException | NamingException e) {

			e.printStackTrace();
		}

		
		setpiecharttwitter();
		setpiechartcomments();
		setpiechartall();
	}

	private void setpiecharttwitter() {

		int nbpositivetwitter = Integer.parseInt(map.get("positivetwitter").toString());
		int nbnegativetwitter = Integer.parseInt(map.get("negativetwitter").toString());
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data("Positive impressions", nbpositivetwitter),
				new PieChart.Data("Negative impressions", nbnegativetwitter));
		chart1 = new PieChart(pieChartData);
		chart1.setTitle("Twitter");
		final Label caption = new Label("");
		caption.setTextFill(Color.DARKORANGE);
		caption.setStyle("-fx-font: 10 arial;");
		chart1.setMaxSize(201, 269);
		chart1.setLayoutY(15);
		for (final PieChart.Data data : chart1.getData()) {
			data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					caption.setTranslateX(e.getSceneX());
					caption.setTranslateY(e.getSceneY());
					caption.setText(String.valueOf(data.getPieValue()) + "%");
				}
			});
		}
		analysis.getChildren().addAll(chart1, caption);
	}

	private void setpiechartcomments() {

		int nbpositivecomments = Integer.parseInt(map.get("positivecomments").toString());
		int nbnegativecomments = Integer.parseInt(map.get("negativecomments").toString());

		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data("Positive impressions", nbpositivecomments),
				new PieChart.Data("Negative impressions", nbnegativecomments));
		chart2 = new PieChart(pieChartData);
		chart2.setTitle("Comments");
		final Label caption = new Label("");
		caption.setTextFill(Color.DARKORANGE);
		caption.setStyle("-fx-font: 10 arial;");
		chart2.setMaxSize(201, 269);
		chart2.setLayoutX(239);
		chart2.setLayoutY(15);
		for (final PieChart.Data data : chart2.getData()) {
			data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					caption.setTranslateX(e.getSceneX());
					caption.setTranslateY(e.getSceneY());
					caption.setText(String.valueOf(data.getPieValue()) + "%");
				}
			});
		}
		analysis.getChildren().addAll(chart2, caption);
	}

	private void setpiechartall() {

		int nbpositivecomments = Integer.parseInt(map.get("positivecomments").toString());
		int nbnegativecomments = Integer.parseInt(map.get("negativecomments").toString());
		int nbpositivetwitter = Integer.parseInt(map.get("positivetwitter").toString());
		int nbnegativetwitter = Integer.parseInt(map.get("negativetwitter").toString());

		int positiveimp = nbpositivecomments + nbpositivetwitter;
		int negativeimp = nbnegativecomments + nbnegativetwitter;
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data("Positive impressions", positiveimp),
				new PieChart.Data("Negative impressions", negativeimp));
	
		chart3 = new PieChart(pieChartData);
		chart3.setTitle("Summary");
		final Label caption = new Label("");
		caption.setTextFill(Color.DARKORANGE);
		caption.setStyle("-fx-font: 10 arial;");
		chart3.setMaxSize(201, 269);
		chart3.setLayoutX(454);
		chart3.setLayoutY(15);
		for (final PieChart.Data data : chart3.getData()) {
			data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					caption.setTranslateX(e.getSceneX());
					caption.setTranslateY(e.getSceneY());
					caption.setText(String.valueOf(data.getPieValue()) + "%");
				}
			});
		}
		analysis.getChildren().addAll(chart3, caption);
		this.positiveimp.setText(String.valueOf(positiveimp));
		this.negativeimp.setText(String.valueOf(negativeimp));
		
	}

	public AnchorPane createPage(int pageIndex) {

		AnchorPane box = new AnchorPane();
		int idcurrent = HomeController.idcurrentuser;

		int page = pageIndex * itemsPerPage();

		for (int i = page; i < page + itemsPerPage(); i = i + 4) {
			int j = i + 1;
			int k = i + 2;
			int r = i + 3;

			if (i >= comments.size()) {
				namecommenter1.setVisible(false);
				comment1.setVisible(false);
				namecommenter2.setVisible(false);
				comment2.setVisible(false);
				namecommenter3.setVisible(false);
				comment3.setVisible(false);
				namecommenter4.setVisible(false);
				comment4.setVisible(false);
				commentsupp1.setVisible(false);
				commentsupp2.setVisible(false);
				commentsupp3.setVisible(false);
				commentsupp4.setVisible(false);
				commentedit1.setVisible(false);
				commentedit2.setVisible(false);
				commededit3.setVisible(false);
				commentedit4.setVisible(false);
				break;
			} else {
				namecommenter1.setVisible(true);
				comment1.setVisible(true);
				namecommenter2.setVisible(true);
				comment2.setVisible(true);
				namecommenter3.setVisible(true);
				comment3.setVisible(true);
				namecommenter4.setVisible(true);
				comment4.setVisible(true);

				namecommenter1.setText(comments.get(i).getCommenter().getFirstName() + " "
						+ comments.get(i).getCommenter().getLastName());
				id1.setText(comments.get(i).getId().toString());

				comment1.setText(BadWordFilter.getCensoredText(comments.get(i).getBody()));
                   if (Integer.parseInt(map.get(comments.get(i).getBody()).toString())==1){
                comment1.setStyle("-fx-background-color:#e6ffe6  ; -fx-alignment: center ; -fx-background-radius: 6 ; -fx-font-size:12 ; -fx-font-weight:bold");
                   }
                   else {
                	   comment1.setStyle("-fx-background-color:#ffe6e6 ; -fx-alignment: center ; -fx-background-radius: 6 ; -fx-font-size:12 ; -fx-font-weight:bold");
                   }
                   if(comments.get(i).getBanned()==1){
                	  comment1.setTextFill(Color.web("#838383"));
                   }
                   else{
                	   comment1.setTextFill(Color.web("#000000"));
                   }
				if (comments.get(i).getCommenter().getId() == idcurrent) {
					commentsupp1.setVisible(true);
					commentedit1.setVisible(true);
				} else {
					commentsupp1.setVisible(false);
					commentedit1.setVisible(false);
				}

			}
			if (j >= comments.size()) {
				namecommenter2.setVisible(false);
				comment2.setVisible(false);
				namecommenter3.setVisible(false);
				comment3.setVisible(false);
				namecommenter4.setVisible(false);
				comment4.setVisible(false);
				break;
			}

			else

			{
				namecommenter2.setVisible(true);
				comment2.setVisible(true);
				namecommenter3.setVisible(true);
				comment3.setVisible(true);
				namecommenter4.setVisible(true);
				comment4.setVisible(true);

				namecommenter2.setText(comments.get(i + 1).getCommenter().getFirstName() + " "
						+ comments.get(i + 1).getCommenter().getLastName());
				id2.setText(comments.get(i + 1).getId().toString());

				comment2.setText(BadWordFilter.getCensoredText(comments.get(i + 1).getBody()));
				 if (Integer.parseInt(map.get(comments.get(i+1).getBody()).toString())==1){
		                comment2.setStyle("-fx-background-color:#e6ffe6  ; -fx-alignment: center ; -fx-background-radius: 6 ; -fx-font-size:12 ; -fx-font-weight:bold");
		                   }
		                   else {
		                	   comment2.setStyle("-fx-background-color:#ffe6e6 ; -fx-alignment: center ; -fx-background-radius: 6 ; -fx-font-size:12 ; -fx-font-weight:bold");
		                   }
				 if(comments.get(i+1).getBanned()==1){
               	  comment2.setTextFill(Color.web("#838383"));
                  }
                  else{
               	   comment2.setTextFill(Color.web("#000000"));
                  }
				if (comments.get(i + 1).getCommenter().getId() == idcurrent) {
					commentsupp2.setVisible(true);
					commentedit2.setVisible(true);
				} else {
					commentsupp2.setVisible(false);
					commentedit2.setVisible(false);
				}

			}
			if (k >= comments.size()) {
				namecommenter3.setVisible(false);
				comment3.setVisible(false);
				namecommenter4.setVisible(false);
				comment4.setVisible(false);

				break;
			}

			else {

				namecommenter3.setVisible(true);
				comment3.setVisible(true);
				namecommenter4.setVisible(true);
				comment4.setVisible(true);

				namecommenter3.setText(comments.get(i + 2).getCommenter().getFirstName() + " "
						+ comments.get(i + 2).getCommenter().getLastName());

				id3.setText(comments.get(i + 2).getId().toString());

				comment3.setText(BadWordFilter.getCensoredText(comments.get(i + 2).getBody()));
				 if (Integer.parseInt(map.get(comments.get(i+2).getBody()).toString())==1){
		                comment3.setStyle("-fx-background-color:#e6ffe6  ; -fx-alignment: center ; -fx-background-radius: 6 ; -fx-font-size:12 ; -fx-font-weight:bold");
		                   }
		                   else {
		                	   comment3.setStyle("-fx-background-color:#ffe6e6 ; -fx-alignment: center ; -fx-background-radius: 6 ; -fx-font-size:12 ; -fx-font-weight:bold");
		                   }
				 if(comments.get(i+2).getBanned()==1){
               	  comment3.setTextFill(Color.web("#838383"));
                  }
                  else{
               	   comment3.setTextFill(Color.web("#000000"));
                  }
				if (comments.get(i + 2).getCommenter().getId() == idcurrent) {
					commentsupp3.setVisible(true);
					commededit3.setVisible(true);
				} else {
					commentsupp3.setVisible(false);
					commededit3.setVisible(false);
				}

			}
			if (r >= comments.size()) {

				namecommenter4.setVisible(false);
				comment4.setVisible(false);

				break;
			}

			else {

				namecommenter4.setVisible(true);
				comment4.setVisible(true);

				namecommenter4.setText(comments.get(i + 3).getCommenter().getFirstName() + " "
						+ comments.get(i + 2).getCommenter().getLastName());
				id4.setText(comments.get(i + 3).getId().toString());

				comment4.setText(BadWordFilter.getCensoredText(comments.get(i + 3).getBody()));
				 if (Integer.parseInt(map.get(comments.get(i+3).getBody()).toString())==1){
		                comment4.setStyle("-fx-background-color:#e6ffe6  ; -fx-alignment: center ; -fx-background-radius: 6 ; -fx-font-size:12 ; -fx-font-weight:bold");
		                   }
		                   else {
		                	   comment4.setStyle("-fx-background-color:#ffe6e6 ; -fx-alignment: center ; -fx-background-radius: 6 ; -fx-font-size:12 ; -fx-font-weight:bold");
		                   }
				 if(comments.get(i+3).getBanned()==1){
               	  comment4.setTextFill(Color.web("#838383"));
                  }
                  else{
               	   comment4.setTextFill(Color.web("#000000"));
                  }
				if (comments.get(i + 3).getCommenter().getId() == idcurrent) {
					commentsupp4.setVisible(true);
					commentedit4.setVisible(true);
				} else {
					commentsupp4.setVisible(false);
					commentedit4.setVisible(false);
				}

			}
		}
		return box;
	}

	@FXML
	void commentaddaction(ActionEvent event) {
		if (commentadd.getText().equals("")){
			nav.showAlert(Alert.AlertType.ERROR, "Error", null, "Empty comment can't be added");	
		}
		else {
		String output = BadWordFilter.getCensoredText(commentadd.getText());

		if (output.contains("*")) {
			nav.showAlert(Alert.AlertType.WARNING, "Warning", null,
					"This comment should be censored for profanity existence.");
		}
		int idcurrent = HomeController.idcurrentuser;
		System.out.println("commenter :" + idcurrent);
		System.out.println("user prof commented " + iduserprofile);
		User commenter = service.findUserById(idcurrent);
		User user = service.findUserById(iduserprofile);
		Comment comment = new Comment();
		comment.setCommenter(commenter);
		comment.setUser(user);
		comment.setCreationDate(new Date());
		comment.setBody(commentadd.getText());
		int a = servicecommenr.addComment(comment);
		comment.setId(a);

		preparecomments();
		commentsview();
		nav.showAlert(Alert.AlertType.INFORMATION, "Success", null, "Comment added successfully..");
		commentadd.setText("");
		}
	}

	void commentsview() {
		if (comments.isEmpty()){
			namecommenter1.setVisible(false);
			comment1.setVisible(false);
			namecommenter2.setVisible(false);
			comment2.setVisible(false);
			namecommenter3.setVisible(false);
			comment3.setVisible(false);
			namecommenter4.setVisible(false);
			comment4.setVisible(false);
			commentsupp1.setVisible(false);
			commentsupp2.setVisible(false);
			commentsupp3.setVisible(false);
			commentsupp4.setVisible(false);
			commentedit1.setVisible(false);
			commentedit2.setVisible(false);
			commededit3.setVisible(false);
			commentedit4.setVisible(false);
			pagination.setVisible(false);
		}
		else{
		double a = Math.ceil((float) comments.size() / 4);
		pagination.setPageCount((int) a);
		pagination.setCurrentPageIndex(0);

		pagination.setMaxPageIndicatorCount((int) a);

		pagination.setPageFactory((Integer pageIndex) -> {
			if (pageIndex >= comments.size()) {
				return null;
			} else {
				return createPage(pageIndex);
			}
		});
		}
		RequiredFieldValidator validator = new RequiredFieldValidator();
		validator.setMessage("Input Required");
		commentadd.getValidators().add(validator);
		commentadd.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
				commentadd.validate();

		});

	}

	@FXML
	void commentsup1clicked(MouseEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Delete a comment");
		alert.setHeaderText("Comment body\t\t: " + comment1.getText());
		alert.setContentText("Do you really want to delete this comment ?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			int resultatreq = servicecommenr.deletecomment(Integer.parseInt(id1.getText()));
			if (resultatreq != 0) {
				nav.showAlert(Alert.AlertType.INFORMATION, "Success", null, "Comment deleted successfully..");
				preparecomments();
				commentsview();
			} else {
				nav.showAlert(Alert.AlertType.ERROR, "Error", null, "We couldn't delete the comment..");
			}
		}
	}

	@FXML
	void commentsup2clicked(MouseEvent event) {

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Delete a comment");
		alert.setHeaderText("Comment body\t\t: " + comment2.getText());
		alert.setContentText("Do you really want to delete this comment ?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			int resultatreq = servicecommenr.deletecomment(Integer.parseInt(id2.getText()));
			if (resultatreq != 0) {
				nav.showAlert(Alert.AlertType.INFORMATION, "Success", null, "Comment deleted successfully..");
				preparecomments();
				commentsview();
			} else {
				nav.showAlert(Alert.AlertType.ERROR, "Error", null, "We couldn't delete the comment..");
			}
		}
	}

	@FXML
	void commentsupp3clicked(MouseEvent event) {

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Delete a comment");
		alert.setHeaderText("Comment body\t\t: " + comment3.getText());
		alert.setContentText("Do you really want to delete this comment ?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			int resultatreq = servicecommenr.deletecomment(Integer.parseInt(id3.getText()));
			if (resultatreq != 0) {
				nav.showAlert(Alert.AlertType.INFORMATION, "Success", null, "Comment deleted successfully..");
				preparecomments();
				commentsview();
			} else {
				nav.showAlert(Alert.AlertType.ERROR, "Error", null, "We couldn't delete the comment..");
			}
		}
	}

	@FXML
	void commentsupp4clicked(MouseEvent event) {

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Delete a comment");
		alert.setHeaderText("Comment body\t\t: " + comment4.getText());
		alert.setContentText("Do you really want to delete this comment ?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			int resultatreq = servicecommenr.deletecomment(Integer.parseInt(id4.getText()));
			if (resultatreq != 0) {
				nav.showAlert(Alert.AlertType.INFORMATION, "Success", null, "Comment deleted successfully..");
				preparecomments();
				commentsview();
			} else {
				nav.showAlert(Alert.AlertType.ERROR, "Error", null, "We couldn't delete the comment..");
			}
		}
	}

	@FXML
	void commentedit1clicked(MouseEvent event) {

		TextInputDialog dialog = new TextInputDialog(comment1.getText());
		dialog.setTitle("Update Comment");
		dialog.setHeaderText("Add new Comment");
		dialog.setContentText("Please edit your comment:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {

			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Update a comment");
			alert.setHeaderText("Comment body\t\t: " + comment2.getText() + "\n New Comment \t\t: " + result.get());
			alert.setContentText("Do you really want to update this comment ?");

			Optional<ButtonType> result1 = alert.showAndWait();
			if (result1.get() == ButtonType.OK) {
				servicecommenr.updatecommentbody(Integer.parseInt(id1.getText()), result.get());

				nav.showAlert(Alert.AlertType.INFORMATION, "Success", null, "Comment updated successfully..");
				preparecomments();
				commentsview();

			}

		} else {
			nav.showAlert(Alert.AlertType.ERROR, "Error", null, "No action has been made");
		}
	}

	@FXML
	void commentedit2clicked(MouseEvent event) {
		TextInputDialog dialog = new TextInputDialog(comment2.getText());
		dialog.setTitle("Update Comment");
		dialog.setHeaderText("Add new Comment");
		dialog.setContentText("Please edit your comment:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {

			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Update a comment");
			alert.setHeaderText("Comment body\t\t: " + comment2.getText() + "\n New Comment \t\t: " + result.get());
			alert.setContentText("Do you really want to update this comment ?");

			Optional<ButtonType> result1 = alert.showAndWait();
			if (result1.get() == ButtonType.OK) {
				servicecommenr.updatecommentbody(Integer.parseInt(id2.getText()), result.get());

				nav.showAlert(Alert.AlertType.INFORMATION, "Success", null, "Comment updated successfully..");
				preparecomments();
				commentsview();

			}

		} else {
			nav.showAlert(Alert.AlertType.ERROR, "Error", null, "No action has been made");
		}
	}

	@FXML
	void commentedit3clicked(MouseEvent event) {
		TextInputDialog dialog = new TextInputDialog(comment3.getText());
		dialog.setTitle("Update Comment");
		dialog.setHeaderText("Add new Comment");
		dialog.setContentText("Please edit your comment:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {

			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Update a comment");
			alert.setHeaderText("Comment body\t\t: " + comment3.getText() + "\n New Comment \t\t: " + result.get());
			alert.setContentText("Do you really want to update this comment ?");

			Optional<ButtonType> result1 = alert.showAndWait();
			if (result1.get() == ButtonType.OK) {
				servicecommenr.updatecommentbody(Integer.parseInt(id3.getText()), result.get());

				nav.showAlert(Alert.AlertType.INFORMATION, "Success", null, "Comment updated successfully..");
				preparecomments();
				commentsview();

			}

		} else {
			nav.showAlert(Alert.AlertType.ERROR, "Error", null, "No action has been made");
		}
	}

	@FXML
	void commentedit4clicked(MouseEvent event) {
		
		TextInputDialog dialog = new TextInputDialog(comment4.getText());
		dialog.setTitle("Update Comment");
		dialog.setHeaderText("Add new Comment");
		dialog.setContentText("Please edit your comment:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {

			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Update a comment");
			alert.setHeaderText("Comment body\t\t: " + comment4.getText() + "\n New Comment \t\t: " + result.get());
			alert.setContentText("Do you really want to update this comment ?");

			Optional<ButtonType> result1 = alert.showAndWait();
			if (result1.get() == ButtonType.OK) {
				servicecommenr.updatecommentbody(Integer.parseInt(id4.getText()), result.get());

				nav.showAlert(Alert.AlertType.INFORMATION, "Success", null, "Comment updated successfully..");
				preparecomments();
				commentsview();

			}
		} else {
			nav.showAlert(Alert.AlertType.ERROR, "Error", null, "No action has been made");
		}

	}
private void twitterfeedview(){
	
    /* 
	    String content = "so this is the story i'm trying to do this shit and i hope it works";
	   String string1;
	   String string2;
	   for (int i=0; i< content.length(); i++){
		   if(i%5==0)
		   {
			 string1= content.substring(0, i);
			 string2 = content.substring(i+1);
			 string1=string1.concat("\n");
			 
			 content=string1.concat(string2);
			 
			   
		   }
	   }
	   final String contenttoanim = content;
	   
	   final Text text = new Text(10, 20, "");
	   
	    
	    final Animation animation = new Transition() {
	        {
	            setCycleDuration(Duration.millis(2000));
	        }
	    
	        protected void interpolate(double frac) {
	            final int length = contenttoanim.length();
	            final int n = Math.round(length * (float) frac);
	           
	            text.setText(contenttoanim.substring(0, n));
	        }
	    
	    };
	    
	    animation.play();

     twitterfeedanchor.getChildren().add(text);
      */
	String content="";
	String positivec="";

	String negativec="";


	for(Map.Entry<String,Integer> mape:map.entrySet())
	    
	    {
	        String t=mape.getKey();
	       
	 	Integer analysis=mape.getValue();
	  
	         if (analysis==1 && (!t.equals("positivecomments")) && (!t.equals("negativecomments")) && (!t.equals("positivetwitter")) && (!t.equals("negativetwitter"))){
	positivec=positivec.concat(t).concat("\n ************** \n");
	        }
	else if (analysis==0 && (!t.equals("positivecomments")) && (!t.equals("negativecomments")) && (!t.equals("positivetwitter")) && (!t.equals("negativetwitter"))){
	negativec=negativec.concat(t).concat("\n ************** \n");
	}
	
	         
	System.out.println(t);
	          
	  System.out.println(analysis);
	        }
	content=content.concat("Positive Impressions : \n ").concat(positivec).concat("\n ---------- \n Negative Impressions : \n").concat(negativec);

tweets.setText(content);
	
}
	private void latesttradepopulate() {

		List<TradingExchange> tes = service.getalltradesforcustomersbytrader(iduserprofile);
		if (tes.size() >= 1) {
			latesttradedatecreation.setText("Creation date: " + tes.get(0).getCreationDate().toString());
			Bond bond = (Bond) tes.get(0).getInstrument();
			latesttradecouponrate.setText("Coupon rate " + bond.getCouponrate().toString());
			latesttradecust.setText(
					"Customer :" + tes.get(0).getUser().getFirstName() + " " + tes.get(0).getUser().getLastName());
			latesttradematuritydate.setText("Maturity date: " + bond.getMaturitydate().toString());
			latesttradeparvalue.setText("Par value : " + bond.getParvalue().toString());
			latesttradesaleprice.setText("Sales price : " + bond.getSaleprice().toString());
			latesttradenotfound.setVisible(false);
			bondlabelll.setVisible(true);
			separateuur.setVisible(true);
		} else {
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

		List<TradingExchange> listete = service.getalltrades(iduserprofile);
		int[] monthCounter = new int[12];

		for (TradingExchange t : listete) {
			int month = t.getCreationDate().getMonth() - 1;
			monthCounter[month] += 1;

		}
		System.out.println(monthCounter);
		series.setName("Number of trades");

		// Create a XYChart.Data object for each month. Add it to the series.
		for (int i = 0; i < monthCounter.length; i++) {
			series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
		}

		bc.getData().addAll(series);

	}

	private void setdatatrader() {
		Trader trader = (Trader) user;
		name.setText(trader.getFirstName() + " " + trader.getLastName());
		email.setText(trader.getEmail());
		role.setText("Trader");
		typetrader.setText(trader.getTradertype());
		phone.setText(Integer.toString(trader.getPhone()));
		address.setText(trader.getAddress().getSountry() + " " + trader.getAddress().getState() + " "
				+ trader.getAddress().getStreet() + " " + trader.getAddress().getZipcode());
		bdate.setText(trader.getBirthdate().toString());
		nationality.setText(trader.getNationality());
		since.setText(trader.getCreationDate().toString());
		String activ;
		String bann;
		if (trader.getIsactive() == 0) {
			activ = "Not active";
		} else {
			activ = "Active account";
		}
		if (trader.getIsbanned() == 1) {
			bann = "Banned for the moment";
		} else {
			bann = "Not banned";
		}
		active.setText(activ);
		banned.setText(bann);

	}

	public void setchartDatatraderprofile() {

		List<TradingExchange> listete = service.getalltradesforcustomersbytrader(iduserprofile);
		int[] monthCounter = new int[12];

		for (TradingExchange t : listete) {
			int month = t.getCreationDate().getMonth() - 1;
			monthCounter[month] += 1;

		}
		System.out.println(monthCounter);
		series.setName("Number of trades");

		// Create a XYChart.Data object for each month. Add it to the series.
		for (int i = 0; i < monthCounter.length; i++) {
			series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
		}

		bc.getData().addAll(series);

	}

	private void setdatacustomer() {

		Customer customer = (Customer) user;
		name.setText(customer.getFirstName() + " " + customer.getLastName());
		cash.setText(customer.getMoney().toString());
		email.setText(customer.getEmail());
		role.setText("Trader");
		typetrader.setText(customer.getTrader().getFirstName() + " " + customer.getLastName());
		phone.setText(Integer.toString(customer.getPhone()));
		address.setText(customer.getAddress().getSountry() + " " + customer.getAddress().getState() + " "
				+ customer.getAddress().getStreet() + " " + customer.getAddress().getZipcode());
		bdate.setText(customer.getBirthdate().toString());
		nationality.setText(customer.getNationality());
		since.setText(customer.getCreationDate().toString());
		companynamegal.setText(customer.getCompany().getName());
		String activ;
	
		if (customer.getIsactive() == 0) {
			activ = "Not active";
		} else {
			activ = "Active account";
		}

		active.setText(activ);
		banned.setText("Risk : " + customer.getRisk().toString());
		List<TradingExchange> tes = service.getalltrades(iduserprofile);
		System.out.println("siiiiiiiiiiiiiiiiiiiiiiiiiiiiiiize" + tes.size());
		if (tes.size() >= 1) {
			latesttradedatecreation.setText("Creation date: " + tes.get(0).getCreationDate().toString());
			Bond bond = (Bond) tes.get(0).getInstrument();
			latesttradecouponrate.setText("Coupon rate " + bond.getCouponrate().toString());
			latesttradecust.setText(
					"Customer :" + tes.get(0).getUser().getFirstName() + " " + tes.get(0).getUser().getLastName());
			latesttradematuritydate.setText("Maturity date: " + bond.getMaturitydate().toString());
			latesttradeparvalue.setText("Maturity date: " + bond.getParvalue().toString());
			latesttradesaleprice.setText("Maturity date: " + bond.getSaleprice().toString());
			latesttradenotfound.setVisible(false);
			bondlabelll.setVisible(true);
			separateuur.setVisible(true);
		} else {
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
		idcustomer.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Trades, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Trades, String> param) {
						return param.getValue().getValue().idcustomer;
					}
				});

		JFXTreeTableColumn<Trades, String> customername = new JFXTreeTableColumn<>("customer name");
		customername.setPrefWidth(150);
		customername.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Trades, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Trades, String> param) {
						return param.getValue().getValue().customername;
					}
				});

		JFXTreeTableColumn<Trades, String> creationdate = new JFXTreeTableColumn<>("Creation date");
		creationdate.setPrefWidth(150);
		creationdate.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Trades, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Trades, String> param) {
						return param.getValue().getValue().creationdate;
					}
				});

		JFXTreeTableColumn<Trades, String> status = new JFXTreeTableColumn<>("status");
		status.setPrefWidth(150);
		status.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Trades, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Trades, String> param) {
						return param.getValue().getValue().status;
					}
				});

		ObservableList<Trades> atts = FXCollections.observableArrayList();
		List<TradingExchange> customers = service.getalltradesforcustomersbytrader(iduserprofile);
		for (TradingExchange t : customers) {
			Trades atable;
			String namea = t.getUser().getFirstName() + " " + t.getUser().getLastName();
			String statust = "";

			if (t.getStatus() != null) {
				statust = t.getStatus().toString();
			}

			atable = new tn.esprit.sltsclient.Utils.Trades(t.getUser().getId().toString(), namea,
					t.getCreationDate().toString(), statust);
			atts.add(atable);
			final TreeItem<Trades> root;
			root = new RecursiveTreeItem<Trades>(atts, RecursiveTreeObject::getChildren);
			traderstableview.getColumns().setAll(idcustomer, customername, creationdate, status);
			traderstableview.setRoot(root);
			traderstableview.setShowRoot(false);
		}

	}

	// cust
	private void settableviewCustomers() {
		JFXTreeTableColumn<Customers, String> name = new JFXTreeTableColumn<>("Name");
		name.setPrefWidth(150);
		name.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
						return param.getValue().getValue().getName();
					}
				});

		JFXTreeTableColumn<Customers, String> login = new JFXTreeTableColumn<>("Login");
		login.setPrefWidth(150);
		login.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
						return param.getValue().getValue().getLogin();
					}
				});

		JFXTreeTableColumn<Customers, String> email = new JFXTreeTableColumn<>("Email");
		email.setPrefWidth(150);
		email.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
						return param.getValue().getValue().getEmail();
					}
				});

		JFXTreeTableColumn<Customers, String> phone = new JFXTreeTableColumn<>("Phone");
		phone.setPrefWidth(150);
		phone.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
						return param.getValue().getValue().getPhone();
					}
				});

		JFXTreeTableColumn<Customers, String> nationality = new JFXTreeTableColumn<>("Nationality");
		nationality.setPrefWidth(150);
		nationality.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
						return param.getValue().getValue().getNationality();
					}
				});

		JFXTreeTableColumn<Customers, String> address = new JFXTreeTableColumn<>("Address");
		address.setPrefWidth(150);
		address.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
						return param.getValue().getValue().getAddress();
					}
				});

		JFXTreeTableColumn<Customers, String> birthdate = new JFXTreeTableColumn<>("Birth Date");
		birthdate.setPrefWidth(150);
		birthdate.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
						return param.getValue().getValue().getBirthdate();
					}
				});

		JFXTreeTableColumn<Customers, String> creationdate = new JFXTreeTableColumn<>("Since");
		creationdate.setPrefWidth(150);
		creationdate.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
						return param.getValue().getValue().getCreationdate();
					}
				});

		JFXTreeTableColumn<Customers, String> activee = new JFXTreeTableColumn<>("Active");
		activee.setPrefWidth(150);
		activee.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
						return param.getValue().getValue().getActivee();
					}
				});

		JFXTreeTableColumn<Customers, String> risk = new JFXTreeTableColumn<>("Risk");
		risk.setPrefWidth(150);
		risk.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
						return param.getValue().getValue().getRisk();
					}
				});

		JFXTreeTableColumn<Customers, String> id = new JFXTreeTableColumn<>("id");
		id.setPrefWidth(150);
		id.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
						return param.getValue().getValue().getRisk();
					}
				});
		JFXTreeTableColumn<Customers, String> idTr = new JFXTreeTableColumn<>("idtr");
		idTr.setPrefWidth(150);
		idTr.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
						return param.getValue().getValue().getIdTr();
					}
				});
		JFXTreeTableColumn<Customers, String> nameTr = new JFXTreeTableColumn<>("Trader");
		nameTr.setPrefWidth(150);
		nameTr.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
						return param.getValue().getValue().getNameTr();
					}
				});
		JFXTreeTableColumn<Customers, String> company = new JFXTreeTableColumn<>("Comapny");
		company.setPrefWidth(150);
		company.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Customers, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Customers, String> param) {
						return param.getValue().getValue().getCompany();
					}
				});
		ObservableList<Customers> atts = FXCollections.observableArrayList();

		List<Customer> customers = service.getalltradercustomersbyid(iduserprofile);
		for (Customer t : customers) {
			Customers atable;
			String namea = t.getFirstName() + " " + t.getLastName();
			String activ;
			
			if (t.getIsactive() == 0) {
				activ = "Not active";
			} else {
				activ = "Active";
			}
			String companyn = "";
			if (t.getCompany() != null) {
				companyn = t.getCompany().getName();

			}

			String addr = t.getAddress().getSountry() + " " + t.getAddress().getState() + " "
					+ t.getAddress().getStreet() + " " + t.getAddress().getZipcode();
			String traderCustid = t.getTrader().getId().toString();
			String traderCustname = t.getTrader().getFirstName() + " " + t.getTrader().getLastName();

			atable = new tn.esprit.sltsclient.Utils.Customers(namea, t.getLogin(), Integer.toString(t.getPhone()),
					t.getNationality(), addr, t.getBirthdate().toString(), t.getCreationDate().toString(), activ,
					t.getEmail(), t.getRisk().toString(), t.getId().toString(), traderCustid, traderCustname, companyn);

			atts.add(atable);

			final TreeItem<Customers> root;
			root = new RecursiveTreeItem<Customers>(atts, RecursiveTreeObject::getChildren);
			customerstableview.getColumns().setAll(name, login, phone, nationality, address, birthdate, creationdate,
					activee, email, risk, company);
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

	// trades , customer profile
	private void settableviewTradesforcustomerprofile() {
		JFXTreeTableColumn<Trades, String> couponrate = new JFXTreeTableColumn<>("couponrate");
		couponrate.setPrefWidth(150);
		couponrate.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Trades, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Trades, String> param) {
						return param.getValue().getValue().couponrate;
					}
				});
		JFXTreeTableColumn<Trades, String> parvalue = new JFXTreeTableColumn<>("Par value");
		parvalue.setPrefWidth(150);
		parvalue.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Trades, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Trades, String> param) {
						return param.getValue().getValue().parvalue;
					}
				});
		JFXTreeTableColumn<Trades, String> salesprice = new JFXTreeTableColumn<>("Sale price");
		salesprice.setPrefWidth(150);
		salesprice.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Trades, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Trades, String> param) {
						return param.getValue().getValue().salesprice;
					}
				});
		JFXTreeTableColumn<Trades, String> maturitydate = new JFXTreeTableColumn<>("Maturity date");
		maturitydate.setPrefWidth(150);
		maturitydate.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Trades, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Trades, String> param) {
						return param.getValue().getValue().maturitydate;
					}
				});
		JFXTreeTableColumn<Trades, String> idcustomer = new JFXTreeTableColumn<>("id customer");
		idcustomer.setPrefWidth(150);
		idcustomer.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Trades, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Trades, String> param) {
						return param.getValue().getValue().idcustomer;
					}
				});

		JFXTreeTableColumn<Trades, String> customername = new JFXTreeTableColumn<>("customer name");
		customername.setPrefWidth(150);
		customername.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Trades, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Trades, String> param) {
						return param.getValue().getValue().customername;
					}
				});

		JFXTreeTableColumn<Trades, String> creationdate = new JFXTreeTableColumn<>("Creation date");
		creationdate.setPrefWidth(150);
		creationdate.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Trades, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Trades, String> param) {
						return param.getValue().getValue().creationdate;
					}
				});

		JFXTreeTableColumn<Trades, String> status = new JFXTreeTableColumn<>("status");
		status.setPrefWidth(150);
		status.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Trades, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Trades, String> param) {
						return param.getValue().getValue().status;
					}
				});

		ObservableList<Trades> atts = FXCollections.observableArrayList();
		List<TradingExchange> lte = service.getalltrades(iduserprofile);
		for (TradingExchange t : lte) {
			Trades atable;
			String namea = t.getUser().getFirstName() + " " + t.getUser().getLastName();
			String statust = "";

			if (t.getStatus() != null) {
				statust = t.getStatus().toString();
			}
			Bond bond = (Bond) t.getInstrument();

			atable = new tn.esprit.sltsclient.Utils.Trades(t.getUser().getId().toString(), namea,
					t.getCreationDate().toString(), statust, bond.getCouponrate().toString(),
					bond.getParvalue().toString(), bond.getSaleprice().toString(), bond.getMaturitydate().toString());
			atts.add(atable);
			final TreeItem<Trades> root;
			root = new RecursiveTreeItem<Trades>(atts, RecursiveTreeObject::getChildren);
			traderstableview.getColumns().setAll(idcustomer, customername, creationdate, status, couponrate, parvalue,
					salesprice, maturitydate);
			traderstableview.setRoot(root);
			traderstableview.setShowRoot(false);
		}

	}

}
