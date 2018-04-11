package tn.esprit.sltsclient.Utils;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.zavtech.morpheus.yahoo.YahooField;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class News  extends RecursiveTreeObject<News> {
	public StringProperty titlee;
	public StringProperty description;
	public StringProperty url;
	public StringProperty datepub;
	//this.index = new SimpleStringProperty(index);
	public News(String titlee, String description, String url, String datepub) {
		
		this.titlee = new SimpleStringProperty(titlee);
		this.description = new SimpleStringProperty(description);
		this.url = new SimpleStringProperty(url);
		this.datepub = new SimpleStringProperty(datepub);
	}


	

}
