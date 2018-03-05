package tn.esprit.sltsclient.Utils;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Trades extends RecursiveTreeObject<Trades> {

	public StringProperty idcustomer;
	public StringProperty customername;
	public StringProperty creationdate;
	public StringProperty status;
	
	
	public StringProperty couponrate;
	public StringProperty parvalue;
	public StringProperty salesprice;
	public StringProperty maturitydate;
	
	
	
	public Trades(String idcustomer,  String customername, String creationdate, String status) {
		this.idcustomer = new SimpleStringProperty(idcustomer);
		
		this.customername = new SimpleStringProperty(customername);
		this.creationdate = new SimpleStringProperty(creationdate);
        this.status = new SimpleStringProperty(status);
		
	}
	public Trades(String idcustomer,  String customername, String creationdate, String status, String couponrate, String parvalue,String salesprice, String maturitydate) {
		this.idcustomer = new SimpleStringProperty(idcustomer);
		
		this.customername = new SimpleStringProperty(customername);
		this.creationdate = new SimpleStringProperty(creationdate);
        this.status = new SimpleStringProperty(status);
        this.couponrate = new SimpleStringProperty(couponrate);
        this.parvalue= new SimpleStringProperty(parvalue);
        this.salesprice = new SimpleStringProperty(salesprice);
        this.maturitydate = new SimpleStringProperty(maturitydate);
	}


	public StringProperty getIdcustomer() {
		return idcustomer;
	}


	public void setIdcustomer(StringProperty idcustomer) {
		this.idcustomer = idcustomer;
	}


	public StringProperty getCustomername() {
		return customername;
	}


	public void setCustomername(StringProperty customername) {
		this.customername = customername;
	}


	public StringProperty getCreationdate() {
		return creationdate;
	}


	public void setCreationdate(StringProperty creationdate) {
		this.creationdate = creationdate;
	}


	public StringProperty getStatus() {
		return status;
	}


	public void setStatus(StringProperty status) {
		this.status = status;
	}
	public StringProperty getCouponrate() {
		return couponrate;
	}
	public void setCouponrate(StringProperty couponrate) {
		this.couponrate = couponrate;
	}
	public StringProperty getParvalue() {
		return parvalue;
	}
	public void setParvalue(StringProperty parvalue) {
		this.parvalue = parvalue;
	}
	public StringProperty getSalesprice() {
		return salesprice;
	}
	public void setSalesprice(StringProperty salesprice) {
		this.salesprice = salesprice;
	}
	public StringProperty getMaturitydate() {
		return maturitydate;
	}
	public void setMaturitydate(StringProperty maturitydate) {
		this.maturitydate = maturitydate;
	}
	
	
	

}