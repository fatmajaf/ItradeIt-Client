/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.sltsclient.Utils;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import tn.esprit.SLTS_server.persistence.Comment;
import tn.esprit.SLTS_server.persistence.Trader;
import tn.esprit.SLTS_server.services.CommentServiceRemote;
import tn.esprit.SLTS_server.services.UserServiceRemote;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author milind
 */
public class SentimentAnalysisWithCount {

    DoccatModel model;
    static int positive = 0;
    static int negative = 0;
    static int positive2 = 0;
    static int negative2 = 0;
    public static HashMap commentsanalysis(List<Comment>cc) throws TwitterException, IOException, NamingException{
    	 
    	   positive2 = 0;
    	    negative2 = 0;
    	HashMap map=new HashMap();  
    	String line = "";
          SentimentAnalysisWithCount twitterCategorizer = new SentimentAnalysisWithCount();
          twitterCategorizer.trainModel();

          ConfigurationBuilder cb = new ConfigurationBuilder();
          cb.setDebugEnabled(true)
                  .setOAuthConsumerKey("3jmA1BqasLHfItBXj3KnAIGFB")
                  .setOAuthConsumerSecret("imyEeVTctFZuK62QHmL1I0AUAMudg5HKJDfkx0oR7oFbFinbvA")
                  .setOAuthAccessToken("265857263-pF1DRxgIcxUbxEEFtLwLODPzD3aMl6d4zOKlMnme")
                  .setOAuthAccessTokenSecret("uUFoOOGeNJfOYD3atlcmPtaxxniXxQzAU4ESJLopA1lbC");
          TwitterFactory tf = new TwitterFactory(cb.build());
          Twitter twitter = tf.getInstance();
          Query query = new Query("the ring");
          QueryResult result = twitter.search(query);
         
        
          int result1 = 0;
          for (Status status : result.getTweets()) {
          	 
              result1 = twitterCategorizer.classifyNewTweet(status.getText());
              if (result1 == 1) {
                  positive++;
                  map.put(status.getText(), 1);
              } else {
                  negative++;
                  map.put(status.getText(), 1);
              }
          }
          map.put("positivetwitter", positive);
          map.put("negativetwitter", negative);
          
  /***comments*****/
         
  		  int result2;
  		
  		for (Comment comm : cc) {
  			System.out.println(comm.getBody());	
  			 result2 = twitterCategorizer.classifyNewTweet(comm.getBody());
  	            if (result2 == 1) {
  	                positive2++;
  	                map.put(comm.getBody(), 1);
  	            } else {
  	                negative2++;
  	              map.put(comm.getBody(), 0);
  	            }
  			
  		}
        map.put("positivecomments", positive2);
        map.put("negativecomments", negative2);
  		
  		
  		/****end comments ***/
  		/*** write in csv****/
          BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/UserAnalysisFatma/res.csv"));
          bw.write("Positive Tweets," + positive);
          bw.newLine();
          bw.write("Negative Tweets," + negative);
          bw.newLine();
          bw.write("Positive comments2," + positive2);
          bw.newLine();
          bw.write("Negative comments2," + negative2);
          bw.close();
  		/*** end write in csv***/
          return map;
  		
    }
    public static void main(String[] args) throws IOException, TwitterException, NamingException {
    //  SentimentAnalysisWithCount.commentsanalysis();
    }

    public void trainModel() {
        InputStream dataIn = null;
        try {
            dataIn = new FileInputStream("src/main/resources/UserAnalysisFatma/input.txt");
            ObjectStream lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
            ObjectStream sampleStream = new DocumentSampleStream(lineStream);
            // Specifies the minimum number of times a feature must be seen
            int cutoff = 2;
            int trainingIterations = 30;
            model = DocumentCategorizerME.train("en", sampleStream, cutoff,
                    trainingIterations);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dataIn != null) {
                try {
                    dataIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public  int classifyNewTweet(String tweet) throws IOException {
        DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);
        double[] outcomes = myCategorizer.categorize(tweet);
        String category = myCategorizer.getBestCategory(outcomes);

        System.out.print("-----------------------------------------------------\nTWEET :" + tweet + " ===> ");
        if (category.equalsIgnoreCase("1")) {
            System.out.println(" POSITIVE ");
            return 1;
        } else {
            System.out.println(" NEGATIVE ");
            return 0;
        }

    }
    public static HashMap newssanalysis(LinkedList<NewsItem> news) throws TwitterException, IOException, NamingException{
   	 
 	   positive2 = 0;
 	    negative2 = 0;
 	HashMap map=new HashMap();  
 	String line = "";
       SentimentAnalysisWithCount twitterCategorizer = new SentimentAnalysisWithCount();
       twitterCategorizer.trainModel();

       ConfigurationBuilder cb = new ConfigurationBuilder();
       cb.setDebugEnabled(true)
               .setOAuthConsumerKey("3jmA1BqasLHfItBXj3KnAIGFB")
               .setOAuthConsumerSecret("imyEeVTctFZuK62QHmL1I0AUAMudg5HKJDfkx0oR7oFbFinbvA")
               .setOAuthAccessToken("265857263-pF1DRxgIcxUbxEEFtLwLODPzD3aMl6d4zOKlMnme")
               .setOAuthAccessTokenSecret("uUFoOOGeNJfOYD3atlcmPtaxxniXxQzAU4ESJLopA1lbC");
      // TwitterFactory tf = new TwitterFactory(cb.build());
      // Twitter twitter = tf.getInstance();
      // Query query = new Query("the ring");
      // QueryResult result = twitter.search(query);
      
     
      
       
/***news*****/
      
		  int result2;
		
		
		for(int i = 0;i<news.size();i++)
        {
                             
                    System.out.println("Title : " +news.get(i).Title);
                     System.out.println("Description : "+news.get(i).Description);
                      System.out.println("Url : "+news.get(i).URL);
                       System.out.println("Date : "+news.get(i).DatePublished);
                       System.out.println("--------------------------------");
                       
                       result2= twitterCategorizer.classifyNewTweet(news.get(i).Title);
                       
                       if (result2 == 1) {
       	                positive2++;
       	                map.put(news.get(i).Title, 1);
       	            } else {
       	                negative2++;
       	              map.put(news.get(i).Title, 0);
       	            }
                }
		
     map.put("positivenews", positive2);
     map.put("negativenews", negative2);
		
		
		/****end comments ***/
		/*** write in csv****/
       BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/UserAnalysisFatma/resnews.csv"));
       bw.write("Positive News," + positive2);
       bw.newLine();
       bw.write("Negative News," + negative2);
     
       bw.close();
		/*** end write in csv***/
       return map;
		
 }
}
