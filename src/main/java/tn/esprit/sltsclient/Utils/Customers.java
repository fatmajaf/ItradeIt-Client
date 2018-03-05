package tn.esprit.sltsclient.Utils;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Customers extends RecursiveTreeObject<Customers> {
 StringProperty name;

	 StringProperty login;
	 StringProperty phone;
	 StringProperty address;
	 StringProperty nationality;
	 StringProperty birthdate;
	 StringProperty creationdate;
	 StringProperty activee;
	 StringProperty email;
	StringProperty risk;
	 StringProperty id;
	 StringProperty idTr;
	  StringProperty nameTr;
	 StringProperty company;
	public Customers(String name,  String login, String phone, String nationality,
			String address, String birthdate, String creationdate, String activee, String email, String risk, String id, String idTr, String nameTr,String company) {
		this.name = new SimpleStringProperty(name);
		
		this.login = new SimpleStringProperty(login);
		this.phone = new SimpleStringProperty(phone);
        this.address = new SimpleStringProperty(address);
		this.nationality = new SimpleStringProperty(nationality);
		this.birthdate = new SimpleStringProperty(birthdate);
		this.creationdate = new SimpleStringProperty(creationdate);
		this.activee = new SimpleStringProperty(activee);
		this.email = new SimpleStringProperty(email);
		
		this.risk = new SimpleStringProperty(risk);
		this.id = new SimpleStringProperty(id);
		this.idTr = new SimpleStringProperty(idTr);
		this.nameTr = new SimpleStringProperty(nameTr);
		this.company = new SimpleStringProperty(company);
	}
	public StringProperty getName() {
		return name;
	}
	public void setName(StringProperty name) {
		this.name = name;
	}
	public StringProperty getLogin() {
		return login;
	}
	public void setLogin(StringProperty login) {
		this.login = login;
	}
	public StringProperty getPhone() {
		return phone;
	}
	public void setPhone(StringProperty phone) {
		this.phone = phone;
	}
	public StringProperty getAddress() {
		return address;
	}
	public void setAddress(StringProperty address) {
		this.address = address;
	}
	public StringProperty getNationality() {
		return nationality;
	}
	public void setNationality(StringProperty nationality) {
		this.nationality = nationality;
	}
	public StringProperty getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(StringProperty birthdate) {
		this.birthdate = birthdate;
	}
	public StringProperty getCreationdate() {
		return creationdate;
	}
	public void setCreationdate(StringProperty creationdate) {
		this.creationdate = creationdate;
	}
	public StringProperty getActivee() {
		return activee;
	}
	public void setActivee(StringProperty activee) {
		this.activee = activee;
	}
	public StringProperty getEmail() {
		return email;
	}
	public void setEmail(StringProperty email) {
		this.email = email;
	}
	public StringProperty getRisk() {
		return risk;
	}
	public void setRisk(StringProperty risk) {
		this.risk = risk;
	}
	public StringProperty getId() {
		return id;
	}
	public void setId(StringProperty id) {
		this.id = id;
	}
	public StringProperty getIdTr() {
		return idTr;
	}
	public void setIdTr(StringProperty idTr) {
		this.idTr = idTr;
	}
	public StringProperty getNameTr() {
		return nameTr;
	}
	public void setNameTr(StringProperty nameTr) {
		this.nameTr = nameTr;
	}
	public StringProperty getCompany() {
		return company;
	}
	public void setCompany(StringProperty company) {
		this.company = company;
	}
	
	

}