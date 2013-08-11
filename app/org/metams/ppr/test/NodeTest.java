package org.metams.ppr.test;

import org.metams.ppr.MysqlHibernate;

import java.util.List;

/**
 * Test class for the Hibernate class for Puppet Nodes
 */
public class NodeTest
{

    MysqlHibernate m_mysql = new MysqlHibernate();


    private boolean testNodeExistsTrue()
    {
        return m_mysql.existsNode("testnode");
    }

    private boolean testNodeExistsFalse()
    {
        return m_mysql.existsNode("testnode_NON");
    }

    private boolean testgetNodes()
    {
        List x =  m_mysql.getNodes();
        return (x.size() != 0);
    }


    /**
     *
     * @return
     */
    private boolean testNode()
    {
        try
        {

            boolean falseValue = m_mysql.writeNode("testnode401", "gestern", "heute", "ok", "NEU", "NEUER", "127.0.0.1");
            boolean trueValue = m_mysql.writeNode("testnode401", "gestern", "heute", "ok", "NEU", "NEUER++", "127.0.0.1");

            String firstSeen = m_mysql.getFirstSeen("testnode401");
            int i = 1;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }   // testNode



    public static void main(String[] args)
    {
        NodeTest x = new NodeTest();
        boolean trueBoolean = x.testNodeExistsTrue();
        boolean falseBoolean = x.testNodeExistsFalse();
        boolean trueTest = x.testNode();
        boolean nodeTest = x.testgetNodes();



        int i = 1;

    }

}
