package controllers;

import play.mvc.*;
import views.html.*;


public class ApplicationAbout extends Controller
{

    public static Result index()
    {

        return ok(about.render());
    }   // index

}
