package ppr;


import org.hibernate.*;

import java.util.LinkedList;
import java.util.List;

/**
 * User: flake
 * Date: May 30, 2010
 * Time: 8:06:52 PM
 */
public class MysqlHibernate
{


    SessionFactory sf = new HibernateUtil().getSessionFactory();
    Session session = null; //sf.openSession();
    Transaction transaction = null;


    /**
     * simple close routine for the session factory itself.
     */
    public void close()
    {
        sf.close();
    }


    /**
     * Contructor for the MySQL class
     */
    public MysqlHibernate()
    {

    }   // constructor for the MySql class


    /**
     * write a new Node entry
     * @param name
     * @param firstSeen
     * @param lastSeen
     * @param status
     * @param puppetVersion
     * @param productionVersion
     * @param ip
     * @return
     */
    public boolean writeNode(String name, String firstSeen, String lastSeen, String status, String puppetVersion, String productionVersion, String ip)
    {

        boolean existed = false;

        // if the URI exists, just increase the counter
        if (existsNode(name))
        {

            firstSeen = getFirstSeen(name);

            deleteNode(name);
            existed = true;

        }
        else if (firstSeen == null)
        {
            firstSeen = lastSeen;
        }

        session = sf.openSession();

        HibernateNodes newNode = new HibernateNodes();
        newNode.setname(name);
        newNode.setfirstSeen(firstSeen);
        newNode.setlastSeen(lastSeen);
        newNode.setstatus(status);
        newNode.setpuppetVersion(puppetVersion);
        newNode.setconfigurationVersion(productionVersion);
        newNode.setlastIP(ip);

        transaction = session.beginTransaction();
        session.save(newNode);
        transaction.commit();

        session.close();

        return existed;

    }   // writeNode



    /**
     * deletes the line with the given URI
     *
     * @param name
     */
    public void deleteNode(String name)
    {

        session = sf.openSession();

        String hql = "delete from org.metams.ppr.HibernateNodes Nodes WHERE Nodes.name= :URI";
        Query query = session.createQuery(hql);
        query.setString("URI", name);

        int row = query.executeUpdate();
        session.flush();
        session.close();
    }


    /**
     *
     * @param nameNode
     * @return
     */
    public String getFirstSeen(String nameNode)
    {
        session = sf.openSession();
        String hql = "select us.firstSeen from org.metams.ppr.HibernateNodes us where us.name = :uid";

        Query query = session.createQuery(hql);
        query.setString("uid",nameNode);
        List results = query.list();
        session.close();


        for (int runner = 0; runner <= results.size() -1; runner++)
        {
            return (String)results.get(runner);

        }

        return null;
    }   // getFirstSeen



    /**
     * queries the existance of a node
     * @param nameNode
     * @return
     */
    public boolean existsNode(String nameNode)
    {



        session = sf.openSession();
        String hql = "SELECT E.name FROM org.metams.ppr.HibernateNodes E";
        Query query = session.createQuery(hql);
        List results = query.list();
        session.close();


        for (int runner = 0; runner <= results.size() -1; runner++)
        {
            String listName = (String)results.get(runner);
            if (listName != null && listName.equals(nameNode))
            {
                return true;
            }
        }

        return false;

    }


    /**
     * retrieves all nodes from the database
     * @return
     */
    public List getNodes()
    {
        List returnList = new LinkedList();

        session = sf.openSession();
        String SQL_QUERY = "Select nodes.name as name, nodes.firstSeen as firstSeen, nodes.lastSeen as lastSeen, nodes.lastIP as lastIP, nodes.status as status, nodes.puppetVersion as puppetVersion, nodes.configurationVersion as configurationVersion from Nodes nodes;";
        SQLQuery query = session.createSQLQuery(SQL_QUERY);

        List<Object[]> result = query.list();
        for (Object[] objects : result)
        {

            String nodeElement = "";

            for (int runner = 0; runner <= 6; runner++)
            {
                String debugValue = (String)objects[runner];
                nodeElement = nodeElement.concat((String)objects[runner]).concat("::");
            }

            returnList.add(nodeElement);
        }

        session.close();

        return returnList;

    }   // getNodes

}


