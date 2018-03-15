package tn.esprit.sltsclient.main;

import java.security.MessageDigest;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import sun.misc.BASE64Encoder;

public class Test1 {
	  
	 
	 public static void main (String[] args) 
	  {
	   
		  try
		  {
			  Hashtable<String, String> ldapEnv = new Hashtable<>();
			  ldapEnv.put( Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			  ldapEnv.put(Context.PROVIDER_URL, "ldap://localhost:10389");
			  ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
			  ldapEnv.put(Context.SECURITY_PRINCIPAL , "uid=admin,ou= system");
			  ldapEnv.put(Context.SECURITY_CREDENTIALS, "secret");
			  DirContext context = new InitialDirContext(ldapEnv);
			  
			  Attributes attributes =new BasicAttributes();
			  Attribute attribute =new BasicAttribute("objectClass");
			  attribute.add("inetOrgPerson");
			  attributes.put(attribute);
			  Attribute sn =new BasicAttribute("sn");
			  sn.add("fa");
			  Attribute cn =new BasicAttribute("cn");
			  cn.add("fa");
			  
			  attributes.put(sn);
			  attributes.put(cn);
			 attributes.put("telephoneNumber", "85296");



	          attributes.put(new BasicAttribute("userpassword", encryptLdapPassword("SHA","fa")));
			 context.createSubcontext("employeeNumber=7 ,ou=users,o=itradeit",attributes);
			  
			  System.out.println(" success");
		 }
		  catch (Exception e) {
			// TODO: handle exception
		}
	  }




	private static String encryptLdapPassword(String algorithm, String _password) {
	        String sEncrypted = _password;
	        if ((_password != null) && (_password.length() > 0)) {
	            boolean bMD5 = algorithm.equalsIgnoreCase("MD5");
	            boolean bSHA = algorithm.equalsIgnoreCase("SHA")
	                    || algorithm.equalsIgnoreCase("SHA1")
	                    || algorithm.equalsIgnoreCase("SHA-1");
	            if (bSHA || bMD5) {
	                String sAlgorithm = "MD5";
	                if (bSHA) {
	                    sAlgorithm = "SHA";
	                }
	                try {
	                    MessageDigest md = MessageDigest.getInstance(sAlgorithm);
	                    md.update(_password.getBytes("UTF-8"));
	                    sEncrypted = "{" + sAlgorithm + "}" + (new BASE64Encoder()).encode(md.digest());
	                } catch (Exception e) {
	                    sEncrypted = null;
	                  
	                }
	            }
	        }
	        System.out.println(sEncrypted);
	        return sEncrypted;
	    }
}