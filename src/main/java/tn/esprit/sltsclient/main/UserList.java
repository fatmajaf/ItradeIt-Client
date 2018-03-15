package tn.esprit.sltsclient.main;



import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class UserList {
	
	
	
	public boolean loginfromLDAP() throws NamingException
	{
		
		Properties initialProperties = new Properties();
		initialProperties.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		initialProperties.put(Context.PROVIDER_URL, "ldap://localhost:10389");
		initialProperties.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
		initialProperties.put(Context.SECURITY_CREDENTIALS, "secret");
		DirContext  context = new InitialDirContext(initialProperties);
		
		String searchFilter="(objectClass=inetOrgPerson)";
		String[] requiredAttributes={"sn","cn","employeeNumber" , "userPassword" };
		SearchControls controls=new SearchControls();
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		controls.setReturningAttributes(requiredAttributes);
		NamingEnumeration users=context.search("ou=users,o=itradeit", searchFilter, controls);
		SearchResult searchResult=null;
		String commonName=null;
		String surName=null;
		String employeeNum=null;
		String userp=null;
		String group=null;
		while(users.hasMore())
		{
			
			searchResult=(SearchResult) users.next();
			Attributes attr=searchResult.getAttributes();
			
			commonName=attr.get("cn").get(0).toString();
			surName=attr.get("sn").get(0).toString();
			
			employeeNum=attr.get("employeeNumber").get(0).toString();
			System.out.println("Name = "+commonName);
			System.out.println("Surname  = "+surName);
			System.out.println("Employee number = "+employeeNum);
			Attribute pwd = attr.get("userPassword");
	        String pass= new String((byte[])pwd.get());
	        System.out.println("=> userPassword : " + pass);
			System.out.println("---------------------------"+attr.get("userpassword").get(0).toString()+"---------------");
			if (pass.equals("{SHA}IVqVYWj3dCElPpR8JDY3HVaqfqE=") && surName.equals("fa")){
				System.out.println("yeees");
				/*String[] attrIDs = {"cn"};
				controls.setReturningAttributes(attrIDs);
				String[] attributes = {"memberOf"};
				controls.setReturningAttributes(attributes);
				NamingEnumeration<?> answer = ((DirContext) controls).search("ou=users,o=itradeit", "(&(objectclass=inetOrgPerson)(sAMAccountName=userName))", controls);*/
				//System.out.println(initialProperties.isMember("ou=roles,ou=groups,o=sars","pmdsadmins", "uid=S1017086,ou=people,o=sars")); 
				
				return true;
			}
			
		}
		return false;
	}
		public static void main(String[] args) throws NamingException  
	{
		UserList sample = new UserList();
		System.out.println(sample.loginfromLDAP());
		
	}

}
