package tn.esprit.sltsclient.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.*;
import java.io.IOException;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import tn.esprit.SLTS_server.persistence.Forum;
import tn.esprit.SLTS_server.services.IForumService;
import tn.esprit.sltsclient.Utils.Navigation;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import org.controlsfx.control.Rating;

public class RowController1 extends ListCell<Forum> {
	@FXML
	private AnchorPane cell;

	@FXML
	private Label question;

	@FXML
	private Rating rating;
	@FXML
	private Button lik;

	@FXML
	private Button dislik;
	@FXML
	private Button repondre;
	FXMLLoader mLLoader;
	@FXML
	private Label nlik;

	@FXML
	private Label nbredi;
	static int idd;

	@Override
	protected void updateItem(Forum f, boolean empty) {

		if (empty || f == null) {

			setText(null);
			setGraphic(null);

		} else {
			if (mLLoader == null) {
				mLLoader = new FXMLLoader(getClass().getResource("/tn/esprit/sltsclient/Views/row.fxml"));
				mLLoader.setController(this);

				try {
					mLLoader.load();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			// lik.setOnAction(event->(f.setLik(f.getLik()+1)));
			
			f.setRate((f.getRate() + rating.getRating()) / 2);
			lik.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					if(f.getLik()==null){
						f.setLik(1);
						lik.setDisable(true);
						dislik.setDisable(false);
					}
					else{
					f.setLik(f.getLik() + 1);
					lik.setDisable(true);
					dislik.setDisable(false);

				}}
			});
			dislik.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					if (f.getDislike()==null){
						f.setDislike(1);
						lik.setDisable(false);
						dislik.setDisable(true);
					}
					else{
					f.setDislike(f.getDislike() + 1);	
					lik.setDisable(false);
					dislik.setDisable(true);
				}}
			});
			repondre.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					idd = f.getId();
					repondre.getScene().getWindow().hide();

					Navigation nav = new Navigation();
					Stage stage = new Stage();
					Parent root = null;
					try {
						root = FXMLLoader.load(getClass().getResource(nav.getReponse()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.show();
				}
			});

			question.setText(f.getQuestion());
			rating.setRating(f.getRate());
			// nlik.setText(f.getLik());
			// nbredi.setText(f.getDislike());
			String jndiName = "SLTS_server-ear/SLTS_server-ejb/ForumService!tn.esprit.SLTS_server.services.IForumService";
			Context context = null;
			try {
				context = new InitialContext();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			IForumService proxy = null;
			try {
				proxy = (IForumService) context.lookup(jndiName);
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			proxy.updatQuestion(f);
			setText(null);
			setGraphic(cell);

		}

	}

}
