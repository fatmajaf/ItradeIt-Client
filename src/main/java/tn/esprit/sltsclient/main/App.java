package tn.esprit.sltsclient.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.sltsclient.Utils.Navigation;

/**
 *
 * @author Fatma Jaafar
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
    	Navigation nav = new Navigation();
        Parent root = FXMLLoader.load(getClass().getResource(nav.getTest()));
        Scene scene = new Scene(root);        
        stage.setTitle("MyJavaFX");
        stage.setScene(scene);
        stage.show();
    }

    
    public static void main(String[] args) {
        launch(args);
    }
}
