package tn.esprit.sltsclient.main;

import java.security.MessageDigest;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import org.apache.log4j.Logger;
import sun.misc.BASE64Encoder;
 
/**
 *  com.edw.ldap.main.LDAPMain
 *
 *  @author edw
 */
public class LDAPMain {
 
    private Logger logger = Logger.getLogger(LDAPMain.class);
    private Hashtable<String, String> env = new Hashtable<String, String>();
 
    public LDAPMain() {
        try {
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://localhost:10389");
            env.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
            env.put(Context.SECURITY_CREDENTIALS, "secret");
        } catch (Exception e) {
            logger.error(e, e);
        }
 
    }
 
    private boolean insert(Person person) {
        try {
 
            DirContext dctx = new InitialDirContext(env);
            Attributes matchAttrs = new BasicAttributes(true);
            matchAttrs.put(new BasicAttribute("uid", person.getName()));
            matchAttrs.put(new BasicAttribute("cn", person.getName()));
            matchAttrs.put(new BasicAttribute("street", person.getAddress()));
            matchAttrs.put(new BasicAttribute("sn", person.getName()));
            matchAttrs.put(new BasicAttribute("userpassword", encryptLdapPassword("SHA", person.getPassword())));
            matchAttrs.put(new BasicAttribute("objectclass", "top"));
            matchAttrs.put(new BasicAttribute("objectclass", "person"));
            matchAttrs.put(new BasicAttribute("objectclass", "organizationalPerson"));
            matchAttrs.put(new BasicAttribute("objectclass", "inetorgperson"));
            String name = "uid=" + person.getName() + ",ou=users,ou=system";
            InitialDirContext iniDirContext = (InitialDirContext) dctx;
            iniDirContext.bind(name, dctx, matchAttrs);
 
            logger.debug("success inserting "+person.getName());
            return true;
        } catch (Exception e) {
            logger.error(e, e);
            return false;
        }
    }
 
    private boolean edit(Person person) {
        try {
 
            DirContext ctx = new InitialDirContext(env);
            ModificationItem[] mods = new ModificationItem[2];
            Attribute mod0 = new BasicAttribute("street", person.getAddress());
            Attribute mod1 = new BasicAttribute("userpassword", encryptLdapPassword("SHA", person.getPassword()));
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
            mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod1);
 
            ctx.modifyAttributes("uid=" + person.getName() + ",ou=users,ou=system", mods);
 
            logger.debug("success editing "+person.getName());
            return true;
        } catch (Exception e) {
            logger.error(e, e);
            return false;
        }
    }
 
    private boolean delete(Person person) {
        try {
 
            DirContext ctx = new InitialDirContext(env);
            ctx.destroySubcontext("uid=" + person.getName() + ",ou=users,ou=system");
 
            logger.debug("success deleting "+person.getName());
            return true;
        } catch (Exception e) {
            logger.error(e, e);
            return false;
        }
    }
     
    private boolean search(Person person) {
        try {
 
            DirContext ctx = new InitialDirContext(env);
            String base = "ou=users,ou=system";
 
            SearchControls sc = new SearchControls();
            sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
 
            String filter = "(&(objectclass=person)(userpassword="+"oj"+"))";
 
            NamingEnumeration results = ctx.search(base, filter, sc);
 
 
            while (results.hasMore()) {
                SearchResult sr = (SearchResult) results.next();
                Attributes attrs = sr.getAttributes();
 
                Attribute attr = attrs.get("userpassword");
                if(attr != null)
                    System.out.println("record found "+attr.get());
            }
            ctx.close();
                         
            return true;
        } catch (Exception e) {
            System.out.println( e);
            return false;
        }
    }
 
    private String encryptLdapPassword(String algorithm, String _password) {
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
                    logger.error(e, e);
                }
            }
        }
        return sEncrypted;
    }
 
    public static void main(String[] args) {
        LDAPMain main = new LDAPMain();
 
        Person person = new Person();
        person.setAddress("kebayoran");
        person.setName("kamplenk");
        person.setPassword("pepe");
 
        // insert
      //  main.insert(person);
         
        // edit
     //   main.edit(person);
         
        // select
        System.out.println(main.search(person));
         
        // delete
      //  main.delete(person);
    }
}