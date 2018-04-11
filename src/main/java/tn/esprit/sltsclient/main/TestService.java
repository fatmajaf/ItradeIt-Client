package tn.esprit.sltsclient.main;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import yahoofinance.quotes.fx.FxQuote;
import yahoofinance.quotes.fx.FxSymbols;



public class TestService {

	public static void main(String[] args) throws IOException, NamingException {

	/*
		Stock tesla = YahooFinance.get("TSLA", true);
		System.out.println(tesla.getHistory());
		
		String[] symbols = new String[] {"INTC", "BABA", "TSLA", "AIR.PA", "YHOO"};
		// Can also be done with explicit from, to and Interval parameters
		Map<String, Stock> stocks = YahooFinance.get(symbols, true);
		Stock intel = stocks.get("INTC");
		Stock airbus = stocks.get("AIR.PA");
		//
		System.out.println(intel.getHistory());
		System.out.println(airbus);*/
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		from.add(Calendar.MONTH, -6); // from 5 years ago
System.out.println(from);
		//Stock google = YahooFinance.get("GOOG", from, to, Interval.MONTHLY);
Stock google = YahooFinance.get("GOOG", true);
		/*System.out.println(google.getHistory());
		google.print();
		System.out.println("book value "+google.getStats().getBookValuePerShare());
		System.out.println("shares "+google.getStats().getSharesFloat());
		System.out.println("price sales"+google.getStats().getPriceSales());
		System.out.println("symbol "+google.getStats().getSymbol());*/
		List<HistoricalQuote> listehq= new ArrayList<HistoricalQuote>();
		listehq = google.getHistory();
		System.out.println("hhdjdjdjjd");
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
	//System.out.println("symbol "+google.getHistory());
		//FxQuote usdeur = YahooFinance.getFx(FxSymbols.USDEUR);
		//FxQuote usdgbp = YahooFinance.getFx("USDGBP=X");
		//System.out.println(usdeur);
		//System.out.println(usdgbp);
		
		//Stock google1 = YahooFinance.get("GOOG",true);
		//google1.print();
		
		//System.out.println(google1.getHistory());
		
		
	}
	
	
}
