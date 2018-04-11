package tn.esprit.sltsclient.Utils;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.zavtech.morpheus.yahoo.YahooField;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StatsT  extends RecursiveTreeObject<StatsT> {
	public StringProperty marketcap;
	public StringProperty petrailing;
	public StringProperty pricesalesratio;
	public StringProperty pricebookratio;
	public StringProperty fiscalyearend;
	public StringProperty mostrecentquarter;
	public StringProperty profitmargin;
	public StringProperty operatingmargin;
	public StringProperty returnonasset;
	public StringProperty returnonequities;
	public StringProperty revenuetim;
	public StringProperty revenuepershare;
	public StringProperty revenuegrowthqtly;
	public StringProperty grossprfit;
	public StringProperty ebitdatim;
	public StringProperty epsdiluted;
	public StringProperty earningsgrowthqtly;
	public StringProperty beta;
	public StringProperty cashmrq;
	public StringProperty cashpershare;
	public StringProperty debtmrq;
	public StringProperty debtoverequitymrq;
	public StringProperty curretratio;
	public StringProperty bookvaluepershare;
	public StringProperty operatingcashflow;
	public StringProperty leveredfreecashflow;
	public StringProperty adv3month;
	public StringProperty adv10day;
	public StringProperty sharesoutstanding;
	public StringProperty sharesfloat;
	public StringProperty ownerpersentinsider;
	public StringProperty ownerpercentinstitution;
	public StringProperty sharesshort;
	public StringProperty sharesshortratio;
	public StringProperty sharesshortprior;
	public StringProperty dividendfwd;
	public StringProperty dividendfwdyield;
	public StringProperty dividendtrailing;
	public StringProperty dividendtrailingyield;
	public StringProperty dividendpayoutratio;
	public StringProperty dividendpaydate;
	public StringProperty dividendexdate;
	public StringProperty lastsplitdate;
	public StatsT(String marketcap, String petrailing, String pricesalesratio,
			String pricebookratio, String fiscalyearend, String mostrecentquarter,
			String profitmargin, String operatingmargin, String returnonasset,
			String returnonequities, String revenuetim, String revenuepershare,
			String revenuegrowthqtly, String grossprfit, String ebitdatim,
			String epsdiluted, String earningsgrowthqtly, String beta, String cashmrq,
			String cashpershare, String debtmrq, String debtoverequitymrq,
			String curretratio, String bookvaluepershare, String operatingcashflow,
			String leveredfreecashflow, String adv3month, String adv10day,
			String sharesoutstanding, String sharesfloat, String ownerpersentinsider,
			String ownerpercentinstitution, String sharesshort, String sharesshortratio,
			String sharesshortprior, String dividendfwd, String dividendfwdyield,
			String dividendtrailing, String dividendtrailingyield, String dividendpayoutratio,
			String dividendpaydate, String dividendexdate, String lastsplitdate) {
		super();
	
		this.marketcap = new SimpleStringProperty(marketcap);
		this.petrailing = new SimpleStringProperty(petrailing);
		this.pricesalesratio = new SimpleStringProperty(pricesalesratio);
		this.pricebookratio = new SimpleStringProperty(pricebookratio);
		this.fiscalyearend = new SimpleStringProperty(fiscalyearend);
		this.mostrecentquarter = new SimpleStringProperty(mostrecentquarter);
		this.profitmargin = new SimpleStringProperty(profitmargin);
		this.operatingmargin = new SimpleStringProperty(operatingmargin);
		this.returnonasset = new SimpleStringProperty(returnonasset);
		this.returnonequities = new SimpleStringProperty(returnonequities);
		this.revenuetim = new SimpleStringProperty(revenuetim);
		this.revenuepershare = new SimpleStringProperty(revenuepershare);
		this.revenuegrowthqtly = new SimpleStringProperty(revenuegrowthqtly);
		this.grossprfit = new SimpleStringProperty(grossprfit);
		this.ebitdatim = new SimpleStringProperty(ebitdatim);
		this.epsdiluted = new SimpleStringProperty(epsdiluted);
		this.earningsgrowthqtly = new SimpleStringProperty(earningsgrowthqtly);
		this.beta = new SimpleStringProperty(beta);
		this.cashmrq = new SimpleStringProperty(cashmrq);
		this.cashpershare = new SimpleStringProperty(cashpershare);
		this.debtmrq = new SimpleStringProperty(debtmrq);
		this.debtoverequitymrq = new SimpleStringProperty(debtoverequitymrq);
		this.curretratio = new SimpleStringProperty(curretratio);
		this.bookvaluepershare = new SimpleStringProperty(bookvaluepershare);
		this.operatingcashflow = new SimpleStringProperty(operatingcashflow);
		this.leveredfreecashflow = new SimpleStringProperty(leveredfreecashflow);
		this.adv3month = new SimpleStringProperty(adv3month);
		this.adv10day = new SimpleStringProperty(adv10day);
		this.sharesoutstanding = new SimpleStringProperty(sharesoutstanding);
		this.sharesfloat = new SimpleStringProperty(sharesfloat);
		this.ownerpersentinsider = new SimpleStringProperty(ownerpersentinsider);
		this.ownerpercentinstitution = new SimpleStringProperty(ownerpercentinstitution);
		this.sharesshort = new SimpleStringProperty(sharesshort);
		this.sharesshortratio = new SimpleStringProperty(sharesshortratio);
		this.sharesshortprior = new SimpleStringProperty(sharesshortprior);
		this.dividendfwd = new SimpleStringProperty(dividendfwd);
		this.dividendfwdyield = new SimpleStringProperty(dividendfwdyield);
		this.dividendtrailing = new SimpleStringProperty(dividendtrailing);
		this.dividendtrailingyield =new SimpleStringProperty( dividendtrailingyield);
		this.dividendpayoutratio = new SimpleStringProperty(dividendpayoutratio);
		this.dividendpaydate = new SimpleStringProperty(dividendpaydate);
		if (dividendexdate==null){
			this.dividendexdate= new SimpleStringProperty("NAN");
		}
		else{
		this.dividendexdate = new SimpleStringProperty(dividendexdate);}
		if(lastsplitdate==null){
			this.lastsplitdate=new SimpleStringProperty("NAN");
		}
		else{
		this.lastsplitdate = new SimpleStringProperty(lastsplitdate);}
	}
	
	
	
	

}
