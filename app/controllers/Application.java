package controllers;

import com.maxmind.geoip.LookupService;
import models.Node;
import ppr.MysqlHibernate;
import play.mvc.*;
import views.html.empty;
import views.html.overview;

import java.util.List;

public class Application extends Controller
{

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

        // sanitize checks
        if (nodesList == null)
            return ok(empty.render());

        if (nodesList.size() == 0)
            return ok(empty.render());

        int correctFactor = 0;
        Node[] nodes = new Node[nodesList.size()];

        System.out.println("Info: Starting with " + nodesList.size() + " nodes...");

        for (int runner = 0; runner <= nodes.length - 1; runner++)
        {
            String node = (String)nodesList.get(runner);
            if (node == null)
            {
                System.out.println("Error: At position " + runner + " a node itself is null");
                return ok(empty.render());
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
                return ok(empty.render());
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