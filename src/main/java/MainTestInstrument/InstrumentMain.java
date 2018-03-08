package MainTestInstrument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import tn.esprit.SLTS_server.instrumentServices.InstrumentServiceRemote;
import tn.esprit.SLTS_server.persistence.Bond;
import tn.esprit.SLTS_server.persistence.Instrument;
import tn.esprit.SLTS_server.persistence.TradingExchange;
import tn.esprit.SLTS_server.persistence.User;
import tn.esprit.SLTS_server.services.UserServiceRemote;



public class InstrumentMain {

	public static void main(String[] args) throws NamingException {
		
    	String jndiName = "SLTS_server-ear/SLTS_server-ejb/InstrumentService!tn.esprit.SLTS_server.instrumentServices.InstrumentServiceRemote";
		//String jndiName2="SLTS_server-ear/SLTS_server-ejb/TradingExchangeService!tradingExchangeService.TradingExchangeServiceRemote";
		String jndiName3="SLTS_server-ear/SLTS_server-ejb/UserService!tn.esprit.SLTS_server.services.UserServiceRemote";
		
		Context context = new InitialContext();
		Context context3 = new InitialContext();
		
		InstrumentServiceRemote proxy = (InstrumentServiceRemote) context.lookup(jndiName);
		List<Bond>bonds=proxy.findAllBonds();
		System.out.println(bonds);
	//	TradingExchange proxy2=(TradingExchange) context.lookup(jndiName2);
		/*UserServiceRemote proxy3=(UserServiceRemote) context3.lookup(jndiName3);
		
		Instrument inst = new Instrument();
		Instrument inst2 = new Instrument();
		
		//Instanciation d'un objet detype instrument
		
		inst.setChangee(20);
		
		inst.setCurrency("Dinar");
		inst.setHighh(205);
		inst.setOpen(200);
		inst.setVolume(203);
	//	proxy.create(inst);
		//reste à affecter l id de l instrument issuer
		
		/***************************************************/
		
		//supprimer un instrument à partir de son id
		// proxy.delete(1);
		
		/***************************************************/
		
		//update une valeur de l'instrument à partir de son id
		//proxy.updatee(1, 600);
		// System.out.println(proxy.findAll());
		
		/***************************************************/
		
		//recherche par id
		// Instrument inst2=new Instrument();
		// inst2=proxy.find(1);
		//System.out.println(inst2.toString());
		
		/****************************************************/
		// Trading exchange 
		
		/*TradingExchange tradeX=new TradingExchange();
		tradeX.setStatus(5) ;
		tradeX.setCreationDate(null);*/
		//System.out.println(tradeX.toString());
	    //tradeX.setInstrument(inst2);
		//System.out.println(tradeX.toString());
		//System.out.println(tradeX.getInstrument().getId());
		//proxy2.create(tradeX);
		
		/****************************************************/
		//affichage avant l'ajout d'un instrument avec la clé étrangere d el'utlisateur
		
		//System.out.println(proxy.findAll());
		
		/*
		//instanciation du user pour tester
		User asma = new User();
		asma.setLogin("asmalogin");
		asma.setNationality("tunisienne");
		asma.setFirstName("aa");
		//proxy3.ajouterUser(asma);
		
		/****************************************************/
		
		//affectation du user à l'instrument
		//proxy.InstrumentUser(1, 1);
		
		
		/****************************************************/
		
		
		// gestion des bonds
		/*
		Bond bond =new Bond();
		SimpleDateFormat date= new SimpleDateFormat("DD/MM/YYYY");
		Date maturityDate;
		try {
			maturityDate = date.parse("50/8/2014");
			bond.setMaturitydate(maturityDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		proxy.create(bond);
		
		proxy.delete(4);
		
		*/
	}
	
	
	
	
	


}
