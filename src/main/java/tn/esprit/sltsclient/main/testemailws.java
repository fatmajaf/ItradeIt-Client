package tn.esprit.sltsclient.main;

import com.cdyne.ws.EmailVerNoTestEmail;
import com.cdyne.ws.EmailVerNoTestEmailSoap;

public class testemailws {

	public static void main(String[] args) {
		EmailVerNoTestEmail service = new EmailVerNoTestEmail();
		EmailVerNoTestEmailSoap port = service.getEmailVerNoTestEmailSoap();
		System.out.println(port.verifyEmail("fatmajaafartn@gmail.com", "").isGoodEmail());
		

	}

}
