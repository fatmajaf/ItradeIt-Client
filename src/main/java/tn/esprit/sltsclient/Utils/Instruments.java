package tn.esprit.sltsclient.Utils;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Instruments extends RecursiveTreeObject<Instruments> {

	public StringProperty parvalue;
	public StringProperty saleprice;
	public StringProperty couponrate;
	public StringProperty availablecoupon;
	
	
	public StringProperty issuer;
	public StringProperty id;
	public StringProperty idissuer;
	
	
	
	
	public Instruments(String parvalue, String saleprice, String couponrate, String availablecoupon, String issuer, String id, String idissuer) {
		this.parvalue = new SimpleStringProperty(parvalue);
		this.saleprice = new SimpleStringProperty(saleprice);
		this.couponrate = new SimpleStringProperty(couponrate);
		this.availablecoupon = new SimpleStringProperty(availablecoupon);
		this.issuer = new SimpleStringProperty(issuer);
		this.id = new SimpleStringProperty(id);
		this.idissuer = new SimpleStringProperty(idissuer);
		
	}
	
	
	

}