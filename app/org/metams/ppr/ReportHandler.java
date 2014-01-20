package org.metams.ppr;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;

public class ReportHandler
{
    private int        m_index = 0;
    private int        m_length = 0;
    private String     m_data = null;


    /**
     *
     * @param input
     * @param fileName
     */
    public ReportHandler(String input, String fileName)
    {
/*        try {
            m_reader = new YamlReader(new FileReader("/Users/flake/Box Documents/IdeaProjects/PragmaticPuppetReporter/test.yaml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
    }


    public void read()
    {
/*
        try
        {

        while (true && m_reader != null)
        {
            Map contact = (Map)m_reader.read();
            if (contact == null) break;
            System.out.println(contact.get("age"));
        }
        }
        catch (Exception e )
        {
            System.out.println("Error: Unable to parse ReportHandler file");
            e.printStackTrace();
        }
  */
    }

    public static void main(String[] args)
    {
        ReportHandler x = new ReportHandler(null, null);
        x.getData(null, "/Users/flake/Box Documents/IdeaProjects/PragmaticPuppetReporter/test.yaml", "127.0.0.1");
    }


    /**
     * read data from file if needed
     * @param data
     * @param fileName
     * @param ip
     * @return
     */
    public boolean getData(String data, String fileName, String ip)
    {
        if (fileName != null)
        {
            try
            {
                BufferedReader br = new BufferedReader(new FileReader(fileName));
                try {
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();

                    while (line != null) {
                        sb.append(line);
                        sb.append("\n");
                        line = br.readLine();
                    }
                    data = sb.toString();
                } finally {
                    br.close();
                }
            }
            catch (Exception e)
            {
                return false;
            }
        }
        else if (data == null)
        {
            return false;
        }

        m_data = data;
        m_length = data.length() - 1;
        m_index = 0;


        String host = getDataValue("  host: ");
        String time = "20".concat(getDataValue("  time: 20"));
        String reportFromat = getDataValue("report_format: ");
        String puppetVersion = getDataValue("puppet_version: ");
        String configVersion = getDataValue("configuration_version: ");
        String environment = getDataValue("environment: ");
        String status = getDataValue("status: ");

        MysqlHibernate mysql = new MysqlHibernate();

        // use the date from filename when importing from files, otherwise use the time from the server
        if (fileName != null)
            mysql.writeNode(host, null , time, status, puppetVersion, configVersion, ip);
        else
            mysql.writeNode(host, null , new Date().toString(), status, puppetVersion, configVersion, ip);


        // clean up factory
        mysql.close();

        return true;
    }


    /**
     * return a line based value based on a given key
     * @param searchValue
     * @return
     */
    private String getDataValue(String searchValue)
    {

        if (searchValue == null)
            return null;


        int index = m_data.indexOf(searchValue, 0); //m_index);
        if (index == -1)
            return null;

        // calculate new start index
        index += searchValue.length();

        int endIndex = m_data.indexOf("\n", index);
        if (endIndex == -1)
        {
            endIndex = m_data.indexOf("\r", index);
            if (endIndex == -1)
                return null;
        }

        m_index = endIndex;

        return m_data.substring(index, endIndex);

    }   // getDataValue



}

