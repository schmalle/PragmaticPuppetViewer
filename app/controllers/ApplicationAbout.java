package controllers;

import play.mvc.*;
import views.html.about;

public class ApplicationAbout extends Controller
{

    public static Result index()
    {

        return ok(about.render());
    }   // index

}
