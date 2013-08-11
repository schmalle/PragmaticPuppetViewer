package controllers;

import com.maxmind.geoip.LookupService;
import models.Node;
import org.metams.ppr.MysqlHibernate;
import play.mvc.*;
import views.html.about;
import views.html.empty;
import views.html.overview;

import java.util.List;

public class ApplicationAbout extends Controller
{

    public static Result index()
    {

        return ok(about.render());
    }   // index

}
