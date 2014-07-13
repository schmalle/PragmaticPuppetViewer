package controllers;

import ppr.MysqlHibernate;
import ppr.config.ConfigHandler;
import ppr.twitter.TwitterMessage;
import play.mvc.*;
import views.html.empty;
import views.html.empty2;

import java.util.List;

/**
 * Created by flake on 10/6/13.
 */
public class Heartbeat extends Controller
{

    /**
     * handles GET request
     * @return
     */
    public static Result index()
    {

        ConfigHandler config = new ConfigHandler("/etc/ppv.conf");

        if (config.isOk())
        {

            String accessToken = config.getAccessToken();
            String accessTokenSecret = config.getAccessTokenSecret();
            String consumerKey = config.getConsumerKey();
            String consumerSecret = config.getConsumerKeySecret();

            TwitterMessage messageInstance = new TwitterMessage();

            MysqlHibernate mysql = new MysqlHibernate();
            List nodes = mysql.getNodes();
            mysql.close();
            int size = 0;
            String date = new java.util.Date().toString();

            if (nodes != null)
            {
                size = nodes.size();
            }


            String status = date + ": " + size + " managed boxes online";

            messageInstance.message(consumerKey, consumerSecret, accessToken, accessTokenSecret, status);


        }
        else
        {
            System.out.println("Error");
        }


        return ok(empty2.render());
    }


}
