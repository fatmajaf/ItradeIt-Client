package tn.esprit.sltsclient.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.google.gson.Gson;

public class JavaCurrencyConversionExample {

    public static void main(String args[]) {

    	 /* CurrencyUnit dollar = Monetary.getCurrency("USD");
    	    CurrencyUnit euro = Monetary.getCurrency("EUR");
    	    CurrencyUnit real = Monetary.getCurrency("BRL");

    	    MonetaryAmount money = Money.of(9, euro);
    	    MonetaryAmount money2 = Money.of(10, dollar);
    	    MonetaryAmount money3 = Money.of(11, real);

    	    List<MonetaryAmount> resultAsc = Stream.of(money, money2, money3)
    	            .sorted(MonetaryFunctions
    	                    .sortCurrencyUnit()).collect(Collectors.toList());//[BRL 11, EUR 9, USD 10]
    	    List<MonetaryAmount> resultDesc = Stream.of(money, money2, money3)
    	            .sorted(MonetaryFunctions
    	                    .sortCurrencyUnitDesc()).collect(Collectors.toList());//[USD 10, EUR 9, BRL 11]
    	    
    	    
    	    */
    	    
        CurrencyConvert convert = new CurrencyConvert();
        Map<String, String> countries = new HashMap<>();
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            countries.put(l.getDisplayCountry(Locale.ENGLISH), iso);
           
        }
        System.out.println(countries);
          String fromCountry = "United States";
                    //String toCountry = "New Zealand";
          String toCountry = "Switzerland";
          
                    String fromCurrency = Currency.getInstance(new Locale("",countries.get(fromCountry))).getCurrencyCode();
                    String toCurrency  = Currency.getInstance(new Locale("",countries.get(toCountry))).getCurrencyCode();
    double rate = convert.convert(fromCurrency,toCurrency);
                            rate = rate * Double.parseDouble("1");
                            System.out.println(rate);
    	    

    }
   
    private static Double findExchangeRateAndConvert(String from, String to, int amount) {
        try {
            //Yahoo Finance API
            URL url = new URL("http://finance.yahoo.com/d/quotes.csv?f=l1&s="+ from + to + "=X");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = reader.readLine();
            if (line.length() > 0) {
                return Double.parseDouble(line) * amount;
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}