package controllers;

import com.maxmind.geoip.LookupService;
import models.Node;
import org.metams.ppr.MysqlHibernate;
import play.mvc.*;
import views.html.overview;

import java.util.List;

public class Application extends Controller
{



    public static Result index()
    {


        String dbfile = "/data/GeoIP.dat";
        LookupService cl = null;
        String countryName = "unknown";



        List nodesList = new MysqlHibernate().getNodes();

        Node[] nodes = new Node[nodesList.size()];

        for (int runner = 0; runner <= nodes.length - 1; runner++)
        {
            String node = (String)nodesList.get(runner);
            String[] splits = node.split("::");

            // You should only call LookupService once, especially if you use
            // GEOIP_MEMORY_CACHE mode, since the LookupService constructor takes up
            // resources to load the GeoIP.dat file into memory
            //LookupService cl = new LookupService(dbfile,LookupService.GEOIP_STANDARD);
            try
            {
                cl = new LookupService(dbfile,LookupService.GEOIP_MEMORY_CACHE);
                countryName = cl.getCountry(splits[3]).getName();
            }
            catch (Exception e)
            {

            }


            nodes[runner] = new Node(splits[0], splits[1],splits[2],splits[3],splits[4],splits[5],splits[6],countryName);

        }


     //   return ok(index.render("Your new application is ready.", nodes));
        return ok(overview.render(nodes, new Integer(nodesList.size()).toString()));
    }
  
}
