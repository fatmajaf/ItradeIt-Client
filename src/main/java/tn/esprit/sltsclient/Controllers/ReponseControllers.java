package tn.esprit.sltsclient.Controllers;

import java.lang.reflect.Proxy;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import tn.esprit.SLTS_server.persistence.Forum;
import tn.esprit.SLTS_server.persistence.Portfolio;
import tn.esprit.SLTS_server.persistence.Reponse;
import tn.esprit.SLTS_server.services.IForumService;
import tn.esprit.SLTS_server.services.IReponseService;
import javafx.scene.control.TableColumn;

public class ReponseControllers implements Initializable {

	@FXML
	private TextArea reponse;

	@FXML
	private Button repondre;

	@FXML
	private Label question;
	@FXML
	private TableView<Reponse> show_answer;
	@FXML
	private TableColumn<?, ?> usercol;

	@FXML
	private TableColumn<?, ?> reponsecol;
	ObservableList<Reponse> data = FXCollections.observableArrayList();

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		String jndiName = "SLTS_server-ear/SLTS_server-ejb/ForumService!tn.esprit.SLTS_server.service.IForumService";
		Context context;
		try {
			context = new InitialContext();

			IForumService proxy = (IForumService) context.lookup(jndiName);
			Forum f = proxy.searchbyid(RowController1.idd);
			question.setText(f.getQuestion());

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	/*	try {
			affichage();
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}

	@FXML
	void repondre(ActionEvent event) throws NamingException {
		String jndiName = "SLTS_server-ear/SLTS_server-ejb/ReponseService!tn.esprit.SLTS_server.services.IReponseService";
		Context context = new InitialContext();
		IReponseService proxy = (IReponseService) context.lookup(jndiName);
		Reponse r = new Reponse();
		r.setRep(reponse.getText());

		String jndiName1 = "SLTS_server-ear/SLTS_server-ejb/ForumService!tn.esprit.SLTS_server.service.IForumService";
		Context context1 = new InitialContext();

		IForumService proxy1 = (IForumService) context1.lookup(jndiName1);
		r.setIdquestion(proxy1.searchbyid(RowController1.idd));

		proxy.addAnswer(r);
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("confirmation");
		alert.setHeaderText("votre reponse a ete ajouter avec success");
		alert.showAndWait();
		reponse.setText(" ");
		


	}

	@SuppressWarnings("restriction")
	public void affichage() throws SQLException, NamingException {
		String jndiName = "SLTS_server-ear/SLTS_server-ejb/ReponseService!tn.esprit.SLTS_server.services.IReponseService";
		Context context = new InitialContext();
		IReponseService proxy = (IReponseService) context.lookup(jndiName);

		reponsecol.setCellValueFactory(new PropertyValueFactory<>("rep"));
		usercol.setCellValueFactory(new PropertyValueFactory<>("iduser"));

		List<Reponse> o = proxy.findAllbyId(RowController1.idd);
		for (Reponse e : o) {
			data.add(e);

		}

		show_answer.setItems(data);

	}
}
