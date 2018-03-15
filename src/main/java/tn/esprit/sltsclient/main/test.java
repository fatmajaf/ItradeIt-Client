package tn.esprit.sltsclient.main;



import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import tn.esprit.SLTS_server.persistence.Address;
import tn.esprit.SLTS_server.persistence.Customer;
import tn.esprit.SLTS_server.persistence.Trader;
import tn.esprit.SLTS_server.persistence.TradingExchange;
import tn.esprit.SLTS_server.persistence.User;
import tn.esprit.SLTS_server.services.UserServiceRemote;
import tn.esprit.sltsclient.Controllers.HomeController;

public class test {

	public static void main(String[] args) throws NamingException {
		String jndiName="SLTS_server-ear/SLTS_server-ejb/UserService!tn.esprit.SLTS_server.services.UserServiceRemote";
		Context context=new InitialContext();
	//	UserServiceRemote service = (UserServiceRemote) context.lookup(jndiName);
	//	UserServiceRemote us;
		
		UserServiceRemote service = (UserServiceRemote) context.lookup(jndiName);
		Trader trader= new Trader();
		  trader.setBirthdate(new Date("2014/02/02"));
		    trader.setLastName("ab");
		    trader.setFirstName("ab");
		    trader.setEmail("ab");
		    trader.setLogin("ab");
		    trader.setPassword("ab");
		    Address a= new Address();
		    a.setSountry("jj");
		    a.setState("jj");
		    a.setZipcode(6);
		    trader.setAddress(a);
		    TokenGenerator tg= new TokenGenerator();
		   // System.out.println(tg.generateToken("fa"));
		   // User user= service.findUserById(19);
		   // user.setEmail("jjjjjjjjj");
		   // service.updateUser(user);
		    //service.ajouterUser(trader);
		    List<Customer> customers=service.getalltradercustomersbyid(19);
		    System.out.println(customers);
		  /*  Trader trder=service.findtraderactivelazynbtrades("asc");
			
			
			
			Integer nbcust = trder.getCustomers().size();
			System.out.println(nbcust);
		    
		    */
		  /*  CurrencyConvert convert = new CurrencyConvert();
	        Map<String, String> countries = new HashMap<>();
	        for (String iso : Locale.getISOCountries()) {
	            Locale l = new Locale("", iso);
	            countries.put(l.getDisplayCountry(Locale.ENGLISH), iso);
	           
	        }
		        List<Customer> customerstorefuse= new ArrayList<Customer>();
		        List<Customer> customerstoaccept= new ArrayList<Customer>();
		        List<Customer> customerstodelete= new ArrayList<Customer>();
		    Date currentDate= new Date();
		    List<User> custs= service.findAlldesactiatedCustomers();
		    System.out.println(custs);
		  
			if (!custs.isEmpty()){
				for (User user : custs) {
					if (user instanceof Customer)
					{
						
						Customer c=(Customer)user;
						System.out.println(c.getAddress().getSountry());
						  String fromCountry = c.getAddress().getSountry();
		                    String toCountry = "New Zealand";
		                    String toCurrency  = Currency.getInstance(new Locale("",countries.get(toCountry))).getCurrencyCode();
		                    String fromCurrency = Currency.getInstance(new Locale("",countries.get(fromCountry))).getCurrencyCode();
		                    
		                
		                    double rate = convert.convert(fromCurrency,toCurrency);
		                            rate = rate * Double.parseDouble("1");
		                            System.out.println(rate);
		                            if (rate<1000 )
		                            {
		                            	if ( c.getCreationDate().getMonth()< currentDate.getMonth())
		                            	{customerstorefuse.add(c);}
		                            	else {
		                            		customerstodelete.add(c);
		                            	}
		                            }
		                            else {
		                            	customerstoaccept.add(c);
		                            }
		    	    
							
					}
				}
				
				
			}
			*/
		    
		    
		   // System.out.println(service.findAllUsers());
		//System.out.println();
		
		    //service.ajouterUser(trader);
		    //User u = new User();
		  //  User u= new Trader();
		   // u.setLogin("ab");
		   // u.setPassword("ab");
		  /* User user= service.login(u);
		   System.out.println(user);
		   if (user==null){
			   System.out.println("not found");
		   }
		   else  System.out.println("found");
		   */
		    /*System.out.println("-------------all users---------");
		    List<User> listeuserr=service.findAllUsers();
		    System.out.println(listeuserr);
		    System.out.println("----------all traders------------");
		 List<Trader> users= service.findAllTraders();
		   System.out.println(users);
		   */
		/*   System.out.println("----------all customers------------");
		   List<User> usersc= service.findAllCustomers();
		   for (User user : usersc) {
			   if (user instanceof Customer){
				   Customer c= (Customer)user;
				   System.out.println(c.getRisk());
			   }
			
		}*/
		    
		  // System.out.println(usersc);
		    
		/*    List<User>listeuserr = service.findAllTraders();
	        System.out.println(listeuserr);
	        List<Trader> tradersss = new ArrayList<Trader>();
	        for (User user : listeuserr) {
	        	if (user instanceof Trader)
	        	{
	        		Trader tr= (Trader)user;
	        	System.out.println(tr);
				tradersss.add(tr);
				}
			}
		    */
		   // Integer n=service.getNbCustomersNotActivated();
		   // System.out.println("nn"+service.getalltradercustomersbyid(1));
		  //  System.out.println(service.getnbtradesbytrader(1));
		    
		   
		    
		   /*System.out.println("-----------user by id-----------");
		   User usrfound= service.findUserById(1);
		   System.out.println(usrfound);
		   System.out.println(usrfound.getClass());
		   if (usrfound instanceof Trader ){
			   
			   System.out.println("yesy yes ");  
		   }
		   else System.out.println("nahhh");*/
		  /* System.out.println("-----------delete user by id-----------");
		   //service.deleteUserById(31);
		   System.out.println("-----------delete user by user prob//-----------");
		  /* User u= service.findUserById(33);
		   System.out.println(u);
		   service.deleteUser(u);*/
		  /* System.out.println("----------- Update user by id-----------"); 
		   User uss= service.findUserById(29);
		   uss.setLogin("loooogin29");
		   service.updateUser(uss);*/
		/*   List<User> usersssfound= service.SearchAllUsers("tt");
		   System.out.println(usersssfound);*/
		  
	}

}
