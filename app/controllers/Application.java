package controllers;

import com.maxmind.geoip.LookupService;
import models.Node;
import ppr.MysqlHibernate;
import play.mvc.*;
import views.html.*;

import java.io.*;
import java.util.List;

public class Application extends Controller
{

    private static String m_dump = "/tmp/dump.txt";
    private static String m_listSh = "/tmp/createlist.ppv.sh";

    /**
     *
     * @param fileName
     */
    private static void deleteFile(String fileName)
    {
        try
        {
            File dump = new File(fileName);
            dump.delete();
        }
        catch (Exception e)
        {

        }
    }


    /**
     *
     * @return
     */
    public static String[] createList()
    {

        deleteFile(m_dump);
        deleteFile("/tmp/createdump.ppv.sh");
        deleteFile(m_listSh);

        int counter = 0;
        String[] buffer = new String[100];
        String[] b = new String[counter];


        String createPuppetDumpString = "puppet cert --list >> /tmp/dump.txt";

        try {
            PrintWriter y = new PrintWriter(m_listSh);
            y.println(createPuppetDumpString);
            y.println("rm " + m_listSh);
            y.close();
            Thread.sleep(5000);


            BufferedReader br = new BufferedReader(new FileReader(m_dump));
            String line;
            while ((line = br.readLine()) != null)
            {
                buffer[counter++] = line;
                // process the line.
            }
            br.close();

            b = new String[counter];
            for (int a = 0; a <= counter - 1; a++)
            {
                b[a] = buffer[a];
            }

        }
        catch (Exception e)
        {
            System.out.println("Info: PragmaticPuppetViewer found to waiting clients");
        }


        return b;
    }


    /**
     *
     * @param client
     */
    public static void signClientFS(String client)
    {

        deleteFile(m_dump);
        deleteFile("/tmp/createdump.ppv.sh");
        deleteFile(m_listSh);

        String x = "puppet cert sign " + client;

        System.out.println("Executing puppet command: " + x);

        try
        {
            PrintWriter y = new PrintWriter("/tmp/createdump.ppv.sh");
            y.println(x);
            y.println("rm " + "/tmp/createdump.ppv.sh");
            y.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    /**
     *
     * @param name
     * @return
     */
    public static Result signClient(String name)
    {

        signClientFS(name);
        return ok(empty2.render());
    }


    /**
     *
     * @param client
     * @return
     */
    public static Result getClientInfo(String client)
    {

        String clientData = "name::firstSeen::lastSeen::lastIP::status::puppetversion:configversion";

        MysqlHibernate mysql = new MysqlHibernate();

        List nodesList = mysql.getNodes();

        for (int runner = 0; runner <= nodesList.size() -1; runner++)
        {
            String data = (String)nodesList.get(runner);
            if (data != null && data.toLowerCase().startsWith(client.toLowerCase()+ "::"))
            {
                clientData = data;
            }

        }

        return ok(data.render(clientData));
    }   // getClientInfo

    /**
     * main function to return user data
     * @return
     */
    public static Result index()
    {

        String dbfile = "/data/GeoIP.dat";
        LookupService cl = null;
        String countryName = "unknown";

        MysqlHibernate mysql = new MysqlHibernate();

        List nodesList = mysql.getNodes();

        //
        // get list and create UI depending on waiting clients
        //
        String[] a = createList();
        if (a != null)
        {
            Node[] waiting = new Node[a.length];

            for (int runner = 0; runner <= a.length - 1; runner++) {

                String work = a[runner];
                int firstQuota = work.indexOf("\"") + 1;
                int nextQuota = work.indexOf("\"", firstQuota);

                waiting[runner] = new Node(work.substring(firstQuota, nextQuota), null, null, null,
                        work.substring(nextQuota + 1),
                        null, null, "(unknown)");
            }

            // sanitize checks
            if (nodesList == null && a.length != 0)
                return ok(empty.render(waiting, new Integer(a.length).toString()));

            if (nodesList.size() == 0 && a.length != 0)
                return ok(empty.render(waiting, new Integer(a.length).toString()));

            if (a.length != 0)
                return ok(empty.render(waiting, new Integer(a.length).toString()));
        }

        int correctFactor = 0;
        Node[] nodes = new Node[nodesList.size()];

        System.out.println("Info: Starting with " + nodesList.size() + " nodes...");

        for (int runner = 0; runner <= nodes.length - 1; runner++)
        {
            String node = (String)nodesList.get(runner);
            if (node == null)
            {
                System.out.println("Error: At position " + runner + " a node itself is null");
            }

            String[] splits = node.split("::");

            // You should only call LookupService once, especially if you use
            // GEOIP_MEMORY_CACHE mode, since the LookupService constructor takes up
            // resources to load the GeoIP.dat file into memory
            //LookupService cl = new LookupService(dbfile,LookupService.GEOIP_STANDARD);
            try
            {
                cl = new LookupService(dbfile,LookupService.GEOIP_MEMORY_CACHE);
                if (splits[3] == null)
                {
                    System.out.println("Error: At position " + runner + " a country could not be found due to null pointer reference");
                    countryName = "<unknown>";
                }
                else
                    countryName = cl.getCountry(splits[3]).getName();
            }
            catch (Exception e)
            {
                System.out.println("Error: At position " + runner + " an exception occured");
                mysql.close();
            }

            // check
            if (splits.length > 6)
            {
                nodes[runner-correctFactor] = new Node(splits[0], splits[1],splits[2],splits[3],splits[4],splits[5],splits[6],countryName);
            }
            else if (splits.length == 6 && splits[4].equals("failed"))
            {
                System.out.println("Info: Production status for honeypot " + splits[0] + "return failed. fixing node table...");
                nodes[runner-correctFactor] = new Node(splits[0], splits[1],splits[2],splits[3],splits[4],splits[5],"<>",countryName);
            }
            else
            {
                correctFactor++;
                System.out.println("Info: Added correctorFactor for nodes table at position " + runner + ", this means something went wrong when splitting the input...");
                System.out.println("Info: Splitted size is " + splits.length);
                for (int runner2 = 0; runner2 <= splits.length -1; runner2++)
                {
                    System.out.print("Element: " + runner2 + ": " + splits[runner2]);
                }
                System.out.println(" ");

            }

        }


        // check if something was corrected, if not copy directly the data
        if (correctFactor != 0)
        {

            System.out.println("Info: Correct factor found, copying nodes tables....");

            Node[] nodes2 = new Node[nodesList.size() - correctFactor];
            for (int runner = 0; runner <= nodes2.length -1 ; runner++)
            {
                nodes2[runner] = nodes[runner];
            }

            mysql.close();
            return ok(overview.render(nodes2, new Integer(nodes2.length).toString()));
        }
        else
        {
            mysql.close();
            return ok(overview.render(nodes, new Integer(nodesList.size()-correctFactor).toString()));
        }
    }   // index

}