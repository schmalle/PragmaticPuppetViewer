package org.metams.ppr;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@SequenceGenerator(name = "id", initialValue = 1, allocationSize = 1)
@Table(name = "Nodes")
public class HibernateNodes implements Serializable
{


    private static final long serialVersionUID = 2675108132819289148L;


    String uri;
    int id;
    String name;
    String firstSeen;
    String lastSeen;
    String lastIP;
    String status;
    String puppetVersion;
    String configurationVersion;


    // Default-Konstruktor:
    public HibernateNodes()
    {

    }




    /* Getter */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public int getid()
    {
        return id;
    }

    @Column(name = "name", nullable = false)
    public String getname()
    {
        return name;
    }

    @Column(name = "firstSeen", nullable = false)
    public String getfirstSeen()
    {
        return firstSeen;
    }

    @Column(name = "lastSeen", nullable = false)
    public String getlastSeen()
    {
        return lastSeen;
    }

    @Column(name = "lastIP", nullable = false)
    public String getlastIP()
    {
        return lastIP;
    }

    @Column(name = "status", nullable = false)
    public String getstatus()
    {
        return status;
    }

    @Column(name = "puppetVersion", nullable = false)
    public String getpuppetVersion()
    {
        return puppetVersion;
    }


    @Column(name = "configurationVersion", nullable = false)
    public String getconfigurationVersion()
    {
        return configurationVersion;
    }

/*

    name   TEXT,
    firstSeen TEXT,
    lastSeen TEXT,
    lastIP TEXT,
    status TEXT,
    puppetVersion TEXT,
    configurationVersion  TEXT,
    );

 */


    /* Setter */
    public void setstatus(String intUserID)
    {
        this.status = intUserID;
    }

    /* Setter */
    public void setconfigurationVersion(String intUserID)
    {
        this.configurationVersion = intUserID;
    }

    /* Setter */
    public void setpuppetVersion(String intUserID)
    {
        this.puppetVersion = intUserID;
    }

    /* Setter */
    public void setfirstSeen(String intUserID)
    {
        this.firstSeen = intUserID;
    }

    /* Setter */
    public void setlastSeen(String intUserID)
    {
        this.lastSeen = intUserID;
    }

    /* Setter */
    public void setlastIP(String intUserID)
    {
        this.lastIP = intUserID;
    }

    /* Setter */
    public void setid(int intUserID)
    {
        this.id = intUserID;
    }


    /* Setter */
    public void setname(String intUserID)
    {
        this.name = intUserID;
    }

}  	// Nsode class for OR mapping with Hibernate