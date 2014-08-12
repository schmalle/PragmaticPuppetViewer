package ppr;

import play.db.ebean.Model;

import javax.persistence.Entity;

/**
 * Created by flake on 6/23/13.
 */
@Entity
public class Node extends Model
{


    public String name;
    public String firstSeen;
    public String lastSeen;
    public String lastIP;
    public String status;
    public String puppetVersion;
    public String configurationVersion;
    public String country;


    public String getName() {return name; };
    public String getFirstSeen() {return firstSeen; };
    public String getLastSeen() {return lastSeen; };
    public String getLastIP() { return lastIP;}
    public String getStatus() {return status;}
    public String getPuppetVersion() { return puppetVersion;}
    public String getConfigurationVersion() {return configurationVersion;}
    public String getCountry() { return country;}



    public Node(String nameExt, String firstSeenExt, String lastSeenExt, String ipE, String statusE, String puppetVersionE, String configurationVersionE, String countryE)
    {
        name = nameExt;
        firstSeen = firstSeenExt;
        lastSeen = lastSeenExt;
        lastIP = ipE;
        status = statusE;
        puppetVersion = puppetVersionE;
        configurationVersion = configurationVersionE;
        country = countryE;
    }

}
