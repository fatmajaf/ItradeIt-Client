package tn.esprit.sltsclient.Utils;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.zavtech.morpheus.yahoo.YahooField;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Options  extends RecursiveTreeObject<Options> {
	public StringProperty index;
	public StringProperty ticker;
	public StringProperty optiontype;
	public StringProperty exirydate;
	public StringProperty strikeprice;
	public StringProperty lastprice;
	public StringProperty change;
	public StringProperty changepercent;
	public StringProperty bid;
	public StringProperty ask;
	public StringProperty volume;
	public StringProperty openinterest;
	public StringProperty impvolatility;
	public Options(String index, String ticker, String optiontype, String exirydate,
			String strikeprice, String lastprice, String change, String changepercent,
			String bid, String ask, String volume, String openinterest,
			String impvolatility) {
		super();
		this.index = new SimpleStringProperty(index);
		this.ticker =new SimpleStringProperty(ticker) ;
		this.optiontype = new SimpleStringProperty(optiontype);
		this.exirydate = new SimpleStringProperty(exirydate);
		this.strikeprice = new SimpleStringProperty(strikeprice);
		this.lastprice = new SimpleStringProperty(lastprice);
		this.change = new SimpleStringProperty(change);
		this.changepercent = new SimpleStringProperty(changepercent);
		this.bid =new SimpleStringProperty(bid) ;
		this.ask = new SimpleStringProperty(ask);
		this.volume = new SimpleStringProperty(volume);
		this.openinterest = new SimpleStringProperty(openinterest);
		this.impvolatility = new SimpleStringProperty(impvolatility);
	}

}
