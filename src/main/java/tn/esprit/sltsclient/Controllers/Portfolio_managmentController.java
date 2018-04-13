/*
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.sltsclient.Controllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.beans.*;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ResourceBundle;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.SLTS_server.persistence.Portfolio;
import tn.esprit.SLTS_server.services.Portfolioservice;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import java.sql.Date;
import java.util.*;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.*;
import javafx.scene.chart.PieChart;

/**
 * FXML Controller class
 *
 * @author kaies oukhay
 */
public class Portfolio_managmentController implements Initializable {
	String idnew;

	@FXML
	private Label id;
	@FXML
	private Label id_user;
	@FXML
	private Label date_creation;
	@FXML
	private Label currency;
	@FXML
	private JFXTextField cash;
	@FXML
	private JFXTextField recheche;
	@FXML
	private Button update;
	@FXML
	private TableColumn<?, ?> idcol;
	@FXML
	private TableColumn<?, ?> usercol;
	@FXML
	private TableColumn<?, ?> datecol;
	@FXML
	private TableColumn<?, ?> currencycol;
	@FXML
	private TableColumn<?, ?> cashcol;
	@FXML
	private TableView<Portfolio> table1;
	@FXML
	private PieChart pie;
	ObservableList<Portfolio> data = FXCollections.observableArrayList();
	ObservableList dataStat = FXCollections.observableArrayList();

	/**
	 * Initializes the controller class.
	 */
	@SuppressWarnings("restriction")
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		table1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
					Portfolio ev = table1.getItems().get(table1.getSelectionModel().getSelectedIndex());
					System.out.println("dsfg");

					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setTitle("Delete");
					alert.setHeaderText("Are you sure to delete this portfolio");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {

						try {
							delete();
						} catch (SQLException | NamingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});

		// if(re.getType())

		// setAlignment(Pos.CENTER);

		try {
			affichage();
			table1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
				try {
					showPubDetails(newValue);

				} catch (ParseException ex) {
					Logger.getLogger(Portfolio_managmentController.class.getName()).log(Level.SEVERE, null, ex);
				}
			});

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void delete() throws SQLException, NamingException {
		String jndiName = "SLTS_server-ear/SLTS_server-ejb/PortfolioServiceImp!tn.esprit.SLTS_server.service.Portfolioservice";
		Context ctx = new InitialContext();
		Portfolioservice proxy = (Portfolioservice) ctx.lookup(jndiName);
		if (table1.getSelectionModel().getSelectedItem() != null) {
			Portfolio r = table1.getSelectionModel().getSelectedItem();

			proxy.deletePortfolio(r.getId());
			;
			table1.getItems().clear();
			affichage();
			id.setText(" ");
			cash.setText(" ");
			currency.setText(" ");
			date_creation.setText(" ");
		}

	}

	@SuppressWarnings("restriction")
	public void affichage() throws SQLException, NamingException {
		String jndiName = "SLTS_server-ear/SLTS_server-ejb/PortfolioServiceImp!tn.esprit.SLTS_server.service.Portfolioservice";
		Context context = new InitialContext();

		Portfolioservice proxy = (Portfolioservice) context.lookup(jndiName);

		idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
		usercol.setCellValueFactory(new PropertyValueFactory<>("id_user"));
		datecol.setCellValueFactory(new PropertyValueFactory<>("date_creation"));
		currencycol.setCellValueFactory(new PropertyValueFactory<>("currency"));
		cashcol.setCellValueFactory(new PropertyValueFactory<>("cashflow"));
		System.out.println("ddd");

		List<Portfolio> o = proxy.findAllPortfolio();
		for (Portfolio e : o) {
			data.add(e);

		}

		table1.setItems(data);

		// recherche avancee
		FilteredList<Portfolio> filteredData = new FilteredList<>(data, e -> true);
		recheche.setOnKeyReleased(e -> {
			recheche.textProperty().addListener((observable, oldValue, newValue) -> {
				filteredData.setPredicate((Predicate<? super Portfolio>) Portfolio -> {
					if (newValue == null || newValue.isEmpty()) {
						return true;
					}
					String lowerCaseFilter = newValue.toLowerCase();
					/*
					 * if (colocation.getNbChambre().contains(newValue)){ return
					 * true; }
					 */
					if (Portfolio.getCurrency().toLowerCase().contains(CharSequence.class.cast(lowerCaseFilter))) {
						return true;
					}
					return false;

				});
			});
			SortedList<Portfolio> sortedData = new SortedList<>(filteredData);
			sortedData.comparatorProperty().bind(table1.comparatorProperty());
			table1.setItems(sortedData);

		});

	}

	@FXML
	private void update_portfolio(ActionEvent event) throws SQLException, NamingException {

		String jndiName = "SLTS_server-ear/SLTS_server-ejb/PortfolioServiceImp!tn.esprit.SLTS_server.service.Portfolioservice";
		Context ctx = new InitialContext();
		Portfolioservice proxy = (Portfolioservice) ctx.lookup(jndiName);
		if (table1.getSelectionModel().getSelectedItem() != null) {
			Portfolio r = table1.getSelectionModel().getSelectedItem();
			r.setCashflow(Double.parseDouble(cash.getText()));
			proxy.updatePortfolio(r);
			table1.getItems().clear();
			affichage();
			id.setText(" ");
			cash.setText(" ");
			currency.setText(" ");
			date_creation.setText(" ");
		}

	}

	void showPubDetails(Portfolio e) throws ParseException {

		// System.out.println("id of event is " + e.getId_evenement());
		idnew = Integer.toString(e.getId());
		System.out.println(e.getDate_creation());

		SimpleDateFormat inFmt = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat outFmt = new SimpleDateFormat("dd/MM/yyyy");
		String dated = outFmt.format((e.getDate_creation()));
		System.out.println(dated);
		date_creation.setText(dated);

		/*
		 * try { DateTimeFormatter formatter =
		 * DateTimeFormatter.ofPattern("dd/MM/yyyy"); LocalDate date
		 * =LocalDate.parse(dated, formatter); date_creation.setText((date));
		 * 
		 * } catch (DateTimeParseException exc) { // == throw exc; // Rethrow
		 * the exception. }
		 */

		id.setText(String.valueOf(e.getId()));
		cash.setText(String.valueOf(e.getCashflow()));
		currency.setText(e.getCurrency());
	}

	@SuppressWarnings("restriction")
	void event() throws NamingException {
		String jndiName = "SLTS_server-ear/SLTS_server-ejb/PortfolioServiceImp!tn.esprit.SLTS_server.service.Portfolioservice";
		Context context = new InitialContext();

		Portfolioservice proxy = (Portfolioservice) context.lookup(jndiName);

		List<Portfolio> Liste = proxy.findAllPortfolio();

	}

	void Calcul() throws NamingException {
		String jndiName = "SLTS_server-ear/SLTS_server-ejb/TradingExchangeServiceImpl!tn.esprit.SLTS_server.service.TradingExchangeService";
		Context context = new InitialContext();

		Portfolioservice proxy = (Portfolioservice) context.lookup(jndiName);

		Portfolio t = new Portfolio();
		Portfolio t1 = new Portfolio();
		Portfolio t2 = new Portfolio();
		Portfolio t3 = new Portfolio();
		Portfolio t4 = new Portfolio();
		Portfolio t5 = new Portfolio();
		long c = proxy.calculerStatus1();
		long cc = proxy.calculerStatus2();
		long c3 = proxy.calculerStatus3();
		long c4 = proxy.calculerStatus4();
		long c5 = proxy.calculerStatus5();
		long c6 = proxy.calculerStatus6();
		dataStat.addAll(new PieChart.Data("Currency = DZD ", c));
		dataStat.add(new PieChart.Data("Currency = USD ", cc));
		dataStat.add(new PieChart.Data("Currency = EUR ", c3));
		dataStat.add(new PieChart.Data("Currency = CAD ", c4));
		dataStat.add(new PieChart.Data("Currency = BRL ", c5));
		dataStat.add(new PieChart.Data("Currency = TND ", c6));

		pie.setData(dataStat);

		// nbrclurrency.setText("Number of Status0:"+c);
		// nbrstock.setText("Number of Status1: "+cc);
		// Moyenne.setText("Portion: Status1 per Status:"+(long)(cc/(c+cc)));
		// Moyenne.setText("");
	}
}
