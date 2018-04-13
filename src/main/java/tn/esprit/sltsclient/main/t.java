package tn.esprit.sltsclient.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import tn.esprit.SLTS_server.persistence.Customer;
import tn.esprit.SLTS_server.persistence.Trader;
import tn.esprit.SLTS_server.persistence.User;
import tn.esprit.SLTS_server.services.UserServiceRemote;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;

public class t {

	public static void main(String[] args) throws NamingException, IOException {
		Stock goog = YahooFinance.get("GOOG");
		goog.print();
		List<HistoricalQuote>listehq=new ArrayList<HistoricalQuote>(); 
		 listehq=goog.getHistory();
		 for (HistoricalQuote historicalQuote : listehq) {
				System.out.println("symbol "+historicalQuote.getSymbol());
				System.out.println("adj close"+historicalQuote.getAdjClose());
				System.out.println("close " +historicalQuote.getClose());
				System.out.println("date "+historicalQuote.getDate().getTime());
				System.out.println("high"+historicalQuote.getHigh());
				System.out.println("low "+historicalQuote.getLow());
				System.out.println("open "+historicalQuote.getOpen());
				System.out.println("volume "+historicalQuote.getVolume());
				
				
			}

	}

}
