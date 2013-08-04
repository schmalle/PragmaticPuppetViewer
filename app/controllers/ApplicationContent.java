package controllers;

import org.metams.ppr.ReportHandler;
import play.mvc.*;
import views.html.empty;

import java.io.File;

public class ApplicationContent extends Controller
{

    public static Result index(String nodeName)
    {

        Http.RequestBody body = request().body();
        Http.RawBuffer textBodyRaw = body.asRaw();
        File newFile = textBodyRaw.asFile();

        System.out.println("Found request from IP " + request().remoteAddress());
        System.out.println("Found request with node " + nodeName);
        System.out.println("Filename " + newFile.getAbsolutePath());


        org.metams.ppr.ReportHandler x = new ReportHandler(null, null);
        x.getData(null, newFile.getAbsolutePath(), request().remoteAddress());

        // newFile.delete();

        //   return ok(index.render("Your new application is ready.", nodes));
        return ok(empty.render());
    }

}
