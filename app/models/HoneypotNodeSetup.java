package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
public class HoneypotNodeSetup extends Model
{

    @Id
    public String   name;
    public String   date;
    public String   type;
    public String   token;


    /**
     *
     * @param date
     * @param name
     * @param type
     * @param token
     */
    public HoneypotNodeSetup(String date, String name, String type, String token)
    {
        try
        {

            this.date = date;
            this.name = name;
            this.type = type;
            this.token = token;
            updateDate();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     *
     */
    public void updateDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        this.date = (cal.getTime()).toString();

    }

    public static Finder<String,HoneypotNodeSetup> find = new Finder<String,HoneypotNodeSetup>(
            String.class, HoneypotNodeSetup.class
    );
}