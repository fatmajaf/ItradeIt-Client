package tn.esprit.sltsclient.Utils;
import com.twilio.*;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


/**
 *
 * @author AGORA
 */
public class Example {
    public static final String ACCOUNT_SID = "ACa29a477f51aca63ccb2516b9aac65f02";
 public static final String AUTH_TOKEN = "2dc43448e964e0bca807cbc03c7ded98";
 
  public static void main(String[] args) {
 Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
 
 Message message = Message
 .creator(new PhoneNumber("+21624240915"), new PhoneNumber("+19123485349"),
 "Ahoy from Twilio!").create();
 
 System.out.println(message.getSid());
 }
}
