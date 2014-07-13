package controllers;

import ppr.ReportHandler;
import play.mvc.*;
import views.html.empty;
import views.html.empty2;

import java.io.File;

public class ApplicationContent extends Controller
{

    /**
     * handles POST request from the reporting
     * @param nodeName
     * @return
     */
    public static Result index(String nodeName)
    {

        Http.RequestBody body = request().body();
        Http.RawBuffer textBodyRaw = body.asRaw();
        File newFile = textBodyRaw.asFile();
        ppr.ReportHandler x = new ReportHandler(null, null);
        x.getData(null, newFile.getAbsolutePath(), request().remoteAddress());

        // delete (temporary file)
        newFile.delete();
        return ok(empty2.render());
    }

}
