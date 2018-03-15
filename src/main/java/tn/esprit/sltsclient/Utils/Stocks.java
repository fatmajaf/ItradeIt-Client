package tn.esprit.sltsclient.Utils;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Stocks extends RecursiveTreeObject<Stocks> {

	public StringProperty adjclose;
	public StringProperty close;
	public StringProperty date;
	public StringProperty highh;
	public StringProperty low;
	public StringProperty open;
	public StringProperty volume;
	
	 
	public Stocks(String adjclose, String close , String date , String highh, String low, String open, String volume) {
		
		this.adjclose = new SimpleStringProperty(adjclose);
		this.close = new SimpleStringProperty(close);
		this.date = new SimpleStringProperty(date);
		this.highh = new SimpleStringProperty(highh);
		this.low= new SimpleStringProperty(low);
		this.open = new SimpleStringProperty(volume);
		
	}
	
	

}