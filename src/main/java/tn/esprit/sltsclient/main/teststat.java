package tn.esprit.sltsclient.main;

import com.zavtech.morpheus.array.Array;
import com.zavtech.morpheus.frame.DataFrame;
import com.zavtech.morpheus.util.text.SmartFormat;
import com.zavtech.morpheus.util.text.printer.Printer;
import com.zavtech.morpheus.yahoo.YahooField;
import com.zavtech.morpheus.yahoo.YahooFinance;

import tn.esprit.SLTS_server.util.OptionsYahoo;

public class teststat {

	public static void main(String[] args) {
/*		Array<String> tickers = Array.of("AAPL", "ORCL", "BLK", "GS", "NFLX", "AMZN", "FB");
		DataFrame<String,YahooField> stats = yahoo.getStatistics("BLK");
		stats.transpose().out().print(200, formats -> {
		    final SmartFormat smartFormat = new SmartFormat();
		    formats.setPrinter(Object.class, Printer.forObject(smartFormat::format));
		});
		*/
		OptionsYahoo o= new OptionsYahoo();
		DataFrame<String,YahooField> stats=o.getstats("BLK");
		System.out.println("***************");
		stats.rows().forEach(
				x->{
					System.out.println(x.key());
					System.out.println("Market_Cap "+x.getValue(YahooField.MARKET_CAP).toString());
					System.out.println("pe_trailing "+x.getValue(YahooField.PE_TRAILING).toString());
					System.out.println("price sales ration "+x.getValue(YahooField.PRICE_SALES_RATIO).toString());
					System.out.println("PRICE_BOOK_RATIO "+x.getValue(YahooField.PRICE_BOOK_RATIO).toString());
					System.out.println("FISCAL_YEAR_END "+x.getValue(YahooField.FISCAL_YEAR_END).toString());
					System.out.println("MOST_RECENT_QUARTER "+x.getValue(YahooField.MOST_RECENT_QUARTER).toString());
					System.out.println("PROFIT_MARGIN "+x.getValue(YahooField.PROFIT_MARGIN).toString());
					System.out.println("OPERATING_MARGIN "+x.getValue(YahooField.OPERATING_MARGIN).toString());
					System.out.println("RETURN_ON_ASSETS "+x.getValue(YahooField.RETURN_ON_ASSETS).toString());
					System.out.println("RETURN_ON_equities "+x.getValue(YahooField.RETURN_ON_EQUITY).toString());
					System.out.println("REVENUE_TTM "+x.getValue(YahooField.REVENUE_TTM).toString());
					System.out.println("REVENUE_pershare "+x.getValue(YahooField.REVENUE_PER_SHARE).toString());
					System.out.println("REVENUE_GROWTH_QTLY "+x.getValue(YahooField.REVENUE_GROWTH_QTLY).toString());
					System.out.println("GROSS_PROFIT "+x.getValue(YahooField.GROSS_PROFIT).toString());
					System.out.println("EBITDA_TTM "+x.getValue(YahooField.EBITDA_TTM).toString());
					System.out.println("EPS_DILUTED "+x.getValue(YahooField.EPS_DILUTED).toString());
					System.out.println("EARNINGS_GRWOTH_QTLY "+x.getValue(YahooField.EARNINGS_GRWOTH_QTLY).toString());
					System.out.println("beta "+x.getValue(YahooField.BETA).toString());
					System.out.println("CASH_MRQ "+x.getValue(YahooField.CASH_MRQ).toString());
					System.out.println("CASH_PER_SHARE "+x.getValue(YahooField.CASH_PER_SHARE).toString());
					System.out.println("DEBT_MRQ "+x.getValue(YahooField.DEBT_MRQ).toString());
					System.out.println("DEBT_OVER_EQUITY_MRQ "+x.getValue(YahooField.DEBT_OVER_EQUITY_MRQ).toString());
					System.out.println("CURRENT_RATIO "+x.getValue(YahooField.CURRENT_RATIO).toString());
					System.out.println(" BOOK_VALUE_PER_SHARE "+x.getValue(YahooField.BOOK_VALUE_PER_SHARE).toString());
					System.out.println(" OPERATING_CASH_FLOW "+x.getValue(YahooField.OPERATING_CASH_FLOW).toString());
					System.out.println(" LEVERED_FREE_CASH_FLOW  "+x.getValue(YahooField.LEVERED_FREE_CASH_FLOW).toString());
					System.out.println(" ADV_3MONTH  "+x.getValue(YahooField.ADV_3MONTH).toString());
					System.out.println(" ADV_10DAY  "+x.getValue(YahooField.ADV_10DAY).toString());
					System.out.println(" SHARES_OUTSTANDING  "+x.getValue(YahooField.SHARES_OUTSTANDING).toString());
					System.out.println(" SHARES_float  "+x.getValue(YahooField.SHARES_FLOAT).toString());
					System.out.println(" OWNER_PERCENT_INSIDER  "+x.getValue(YahooField.OWNER_PERCENT_INSIDER).toString());
					System.out.println(" OWNER_PERCENT_INSTITUTION  "+x.getValue(YahooField.OWNER_PERCENT_INSTITUTION).toString());
					System.out.println("SHARES_SHORT  "+x.getValue(YahooField.SHARES_SHORT).toString());
					System.out.println("SHARES_SHORT_ratio  "+x.getValue(YahooField.SHARES_SHORT_RATIO).toString());
					System.out.println("SHARES_SHORT_prior  "+x.getValue(YahooField.SHARES_SHORT_PRIOR).toString());
					System.out.println("DIVIDEND_FWD "+x.getValue(YahooField.DIVIDEND_FWD).toString());
					System.out.println("DIVIDEND_FWD_YIELD "+x.getValue(YahooField.DIVIDEND_FWD_YIELD).toString());
					System.out.println("DIVIDEND_TRAILING "+x.getValue(YahooField.DIVIDEND_TRAILING).toString());
					System.out.println("DIVIDEND_TRAILING yield "+x.getValue(YahooField.DIVIDEND_TRAILING_YIELD).toString());
					System.out.println("DIVIDEND_PAYOUT_RATIO "+x.getValue(YahooField.DIVIDEND_PAYOUT_RATIO).toString());
					System.out.println("DIVIDEND_PAY_DATE "+x.getValue(YahooField.DIVIDEND_PAY_DATE).toString());
					System.out.println(" DIVIDEND_EX_DATE "+x.getValue(YahooField.DIVIDEND_EX_DATE).toString());
					System.out.println(" LAST_SPLIT_DATE "+x.getValue(YahooField.LAST_SPLIT_DATE).toString());
				}
				
				);
		

	}

}
