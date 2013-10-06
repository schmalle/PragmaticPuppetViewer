package org.metams.ppr.twitter;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterMessage
{

    /**
     *
     * @param consumerKey
     * @param consumerSecret
     * @param accessTokenString
     * @param accessTokenSecret
     * @param message
     */
    public void message(String consumerKey, String consumerSecret, String accessTokenString, String accessTokenSecret, String message)
    {
        TwitterFactory factory = new TwitterFactory();
        AccessToken accessToken = new AccessToken(accessTokenString, accessTokenSecret);
        Twitter twitter = factory.getInstance();
        twitter.setOAuthConsumer(consumerKey, consumerSecret);
        twitter.setOAuthAccessToken(accessToken);
        try
        {
            Status status = twitter.updateStatus(message);
        }
        catch (TwitterException x)
        {
            System.out.println("Error: Unable to update status...");
        }
    }

}
