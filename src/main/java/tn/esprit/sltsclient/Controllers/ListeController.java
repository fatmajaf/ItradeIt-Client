/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.sltsclient.Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.json.JSONException;
import org.json.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import tn.esprit.SLTS_server.persistence.Forum;
import tn.esprit.SLTS_server.persistence.Portfolio;
import tn.esprit.SLTS_server.services.IForumService;
import tn.esprit.sltsclient.Utils.JsonReader;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author kaies oukhay
 */
public class ListeController implements Initializable {
	@FXML
	private Label usd;
	@FXML
	private Label eur;
	@FXML
	private ListView<Forum> liste;
	@FXML
	private TextArea question;
	@FXML
	private Button add;

	ObservableList<Forum> data = FXCollections.observableArrayList();

	/**
	 * Initializes the controller class.
	 */
	@SuppressWarnings("restriction")
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		JSONObject json;
		try {
			json = JsonReader.readJsonFromUrl("http://data.fixer.io/api/latest?access_key=dd762bad4ea3bc28add8b7adccf678a6&base=EUR");
			   
			   Float usd=Float.parseFloat(json.getJSONObject("rates").getString("USD"));
		        Float eur=Float.parseFloat(json.getJSONObject("rates").getString("EUR"));
		        Float gbp=Float.parseFloat(json.getJSONObject("rates").getString("GBP"));
		        Float inr=Float.parseFloat(json.getJSONObject("rates").getString("INR"));
		        Float aud=Float.parseFloat(json.getJSONObject("rates").getString("AUD"));
		        Float cad=Float.parseFloat(json.getJSONObject("rates").getString("CAD"));
		        Float sgd=Float.parseFloat(json.getJSONObject("rates").getString("SGD"));
		        Float chf=Float.parseFloat(json.getJSONObject("rates").getString("CHF"));
		        Float myr=Float.parseFloat(json.getJSONObject("rates").getString("MYR"));
		        Float jpy=Float.parseFloat(json.getJSONObject("rates").getString("JPY"));
		        Float cny=Float.parseFloat(json.getJSONObject("rates").getString("CNY"));
		        Float tnd=Float.parseFloat(json.getJSONObject("rates").getString("TND"));
		        
		        this.eur.setText(String.valueOf(eur));
		        this.usd.setText(String.valueOf(usd));
		
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
	       
		
		   
		   String jndiName = "SLTS_server-ear/SLTS_server-ejb/ForumService!tn.esprit.SLTS_server.services.IForumService";
		Context context;
		try {
			context = new InitialContext();

			IForumService proxy = (IForumService) context.lookup(jndiName);
			data = FXCollections.observableArrayList(proxy.findAll());
			System.out.println(data);

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		liste.setItems(data);
		liste.setCellFactory(new Callback<ListView<Forum>, javafx.scene.control.ListCell<Forum>>() {

			@Override
			public ListCell<Forum> call(ListView<Forum> param) {
				// TODO Auto-generated method stub
				return new RowController1();

			}

		});

		// liste.setOnMouseClicked(new EventHandler<MouseEvent>() )

	}

	@SuppressWarnings("restriction")
	@FXML
	private void add(ActionEvent event) throws NamingException {
		String jndiName = "SLTS_server-ear/SLTS_server-ejb/ForumService!tn.esprit.SLTS_server.services.IForumService";
		Context context = new InitialContext();

		IForumService proxy = (IForumService) context.lookup(jndiName);

		Forum f = new Forum();
		System.out.println("hi");
		f.setQuestion(question.getText());
		System.out.println(f);

		proxy.addQuestion(f);

		/*
		 * try { String apiKey =
		 * "apikey=Nw06PWKqAYI-LIXcXapn6NolnkBSybfZwZ5cqSdjdY"; String message =
		 * "&message=Votre question a ete ajouter avec success"; String sender =
		 * "&sender=ITraidIt"; String numbers = "&numbers=+216" + 94310084 + "";
		 * // en phase // de test // +216"+telC.getText()+"" // ;//
		 * 
		 * // Send data HttpURLConnection conn = (HttpURLConnection) new
		 * URL("https://api.txtlocal.com/send/?").openConnection(); String data
		 * = apiKey + numbers + message + sender; conn.setDoOutput(true);
		 * conn.setRequestMethod("POST");
		 * conn.setRequestProperty("Content-Length",
		 * Integer.toString(data.length()));
		 * conn.getOutputStream().write(data.getBytes("UTF-8")); final
		 * BufferedReader rd = new BufferedReader(new
		 * InputStreamReader(conn.getInputStream())); final StringBuffer
		 * stringBuffer = new StringBuffer(); String line; while ((line =
		 * rd.readLine()) != null) { stringBuffer.append(line); } rd.close();
		 * 
		 * // return stringBuffer.toString(); } catch (Exception e) {
		 * System.out.println("Error SMS " + e); // return "Error "+e; }
		 */
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("confirmation");
		alert.setHeaderText("votre question a ete ajouter avec success");
		alert.showAndWait();
		question.setText(" ");

	}

}
