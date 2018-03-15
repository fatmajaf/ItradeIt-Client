package tn.esprit.sltsclient.main;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;


public class QueryPasswordAttributeTest {

  /**
   * @param args
   */
  public static void main(String[] args) {

   
    
    Hashtable<String, String> environment = new Hashtable<String, String>();
    environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    environment.put(Context.PROVIDER_URL, "ldap://localhost:10389");
    environment.put(Context.SECURITY_AUTHENTICATION, "simple");
    environment.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
	environment.put(Context.SECURITY_CREDENTIALS, "secret");

    DirContext dirContext = null;
    NamingEnumeration<?> results = null;
    
    try {
      dirContext = new InitialDirContext(environment);

      /**
       * Retrieve the specific attributes 
       */
      SearchControls controls = new SearchControls();
      controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
      controls.setReturningAttributes(new String [] { 
          "nsRole", 
          "userPassword",
                "uid",
                "objectClass",
                "givenName",
                "sn",
                "cn"
      });

      //Get entries having objectclass=person
      String base = "ou=users,o=itradeit";
      String filter = "(objectclass=person)";
      results = dirContext.search(base, filter, controls);

      while (results.hasMore()) {
        SearchResult searchResult = (SearchResult) results.next();
        Attributes attributes = searchResult.getAttributes();
        
        NamingEnumeration<? extends Attribute> attrs = attributes.getAll();

        while (attrs.hasMore()) {
          System.out.println(attrs.next());
        }

        //Password string depends on LDAP password policy
        Attribute pwd = attributes.get("userPassword");
        String pass= new String((byte[])pwd.get());
        System.out.println("=> userPassword : " + pass);
        System.out.println();
      }

    } catch (NameNotFoundException e) {
      e.printStackTrace();
    } catch (NamingException e) {
      e.printStackTrace();
    } finally {
      if (results != null) {
        try {
          results.close();
        } catch (Exception e) {
        }
      }

      if (dirContext != null) {
        try {
          dirContext.close();
        } catch (Exception e) {
        }
      }
    }

  }

}
   