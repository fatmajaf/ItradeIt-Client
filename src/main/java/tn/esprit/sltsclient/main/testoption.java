package tn.esprit.sltsclient.main;

import java.io.File;
import java.time.LocalDate;
import java.util.Set;

import com.zavtech.morpheus.frame.DataFrame;
import com.zavtech.morpheus.frame.DataFrameSource;
import com.zavtech.morpheus.viz.chart.Chart;
import com.zavtech.morpheus.yahoo.YahooField;
import com.zavtech.morpheus.yahoo.YahooFinance;
import com.zavtech.morpheus.yahoo.YahooQuoteHistorySource;

import tn.esprit.SLTS_server.util.OptionsYahoo;

public class testoption {

	public static void main(String[] args) {
		OptionsYahoo o= new OptionsYahoo();
		DataFrame<String,YahooField> optionQuotes = o.optionquotes("BLK", "all");
		//optionQuotes
		//System.out.println(optionQuotes.toString());
		//Select rows representing CALL options
	
	/*	DataFrame<String,YahooField> calls = optionQuotes.rows().select(row -> {
		    final String type = row.getValue(YahooField.OPTION_TYPE);
		System.out.println(row.getValue(YahooField.TICKER).toString());
		System.out.println(row.getValue(YahooField.PX_BID).toString());

		    return type.equalsIgnoreCase("Call");
		    
		});*/
	/*	optionQuotes.cols().forEach(
				x->{
					System.out.println(x.colKeys().toArray().toString());
					System.out.println(x.getValue(0).toString());
				}
				);
		optionQuotes.rows().forEach(
				
x-> {System.out.println("Index : "+x.key());
	System.out.println("Tiker : "+x.getValue(YahooField.TICKER).toString());
	System.out.println("option type : "+x.getValue(YahooField.OPTION_TYPE));
	System.out.println("expiry date  : "+x.getValue(YahooField.EXPIRY_DATE));
	System.out.println("strike price : "+x.getValue(YahooField.PX_STRIKE));
	System.out.println("last price : "+x.getValue(YahooField.PX_LAST));
	System.out.println("change : "+x.getValue(YahooField.PX_CHANGE));
	System.out.println("change % : "+x.getValue(YahooField.PX_CHANGE_PERCENT));
	System.out.println("bid  : "+x.getValue(YahooField.PX_BID));
	System.out.println("ask : "+x.getValue(YahooField.PX_ASK));
	System.out.println("volume : "+x.getValue(YahooField.PX_VOLUME));
	System.out.println("open interest : "+x.getValue(YahooField.OPEN_INTEREST));
	System.out.println("implied volatility : "+x.getValue(YahooField.IMPLIED_VOLATILITY));

System.out.println("------------------------------");
	});
		System.out.println(optionQuotes.rowCount());
//	optionQuotes.out().print();
		//System.out.println(calls);
		
		*/
		 String ticker = "BLK";
	        YahooQuoteHistorySource source = DataFrameSource.lookup(YahooQuoteHistorySource.class);
	        DataFrame<LocalDate,YahooField> closePrices = source.read(options -> {
	            options.withStartDate(LocalDate.now().minusYears(10));
	            options.withEndDate(LocalDate.now());
	            options.withDividendAdjusted(true);
	            options.withTicker(ticker);
	        }).cols().select(YahooField.PX_CLOSE);
	        Chart.create().asSwing().withAreaPlot(closePrices, false, chart -> {
	            chart.plot().axes().domain().label().withText("Date");
	            chart.plot().axes().range(0).label().withText("Close Price");
	            chart.title().withText(ticker + ": Close Prices (Last 10 Years)");
	           
	            chart.legend().off();
	            
	            chart.writerPng(new File("../morpheus-docs/docs/images/yahoo/blk_prices_1.png"), 700, 400, true);
	            chart.show(200, 200);
	        });

	

	}

}
