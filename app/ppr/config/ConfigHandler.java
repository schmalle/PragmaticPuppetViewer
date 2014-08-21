package ppr.config;

        import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileReader;
        import java.io.IOException;

/**
 * User: flake
 * Date: Jun 30, 2010
 * Time: 9:19:30 PM
 */
public class ConfigHandler
{

    private File m_file = null;
    private BufferedReader m_reader = null;
    private String  m_useTwitter = "no";
    private String  m_consumerkey = null;
    private String  m_consumerkeysecret = null;
    private String  m_accessToken = null;
    private String  m_accessTokenSecret = null;
    private String  m_ewsDirectoryLocation = null;
    private String  m_ewsPythonCreateHoneypotLocation = null;
    private String  m_access_token_ppv = null;


    public String getEwsDirectoryLocation() { return m_ewsDirectoryLocation; }
    public String getEwsTemplateLocation() { return m_ewsPythonCreateHoneypotLocation; }
    public String getAccessTokenPPV() {return m_access_token_ppv;}

    public String getAccessToken() {return m_accessToken; }
    public String getAccessTokenSecret() {return m_accessTokenSecret; }
    public String getConsumerKey() {return m_consumerkey; }
    public String getConsumerKeySecret() {return m_consumerkeysecret; }

    /**
     * returns, if the init was ok
     * @return
     */
    public boolean isOk()
    {

        if (m_reader != null)
            System.out.println("Reader set");
        else
            System.out.println("Reader not set");

        return !(m_reader == null);
    }

    /**
     *
     * @return
     */
    public boolean getUseTwitter()
    {
        if (m_useTwitter == null)
            return false;

        if (m_useTwitter.toLowerCase().equals("yes"))
            return true;

        return false;
    }


    /*
        constructor for the ConfigHandler class
     */
    public ConfigHandler(String fileName)
    {
        try
        {

            if (fileName != null)
            {
                m_file = new File(fileName);
                m_reader = new BufferedReader(new FileReader(m_file));
                read();

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            m_reader = null;
        }

    }   // ConfigHandler


    private String catchData(String line, String in, String inData)
    {

        if (inData != null)
            return inData;

        if (line.startsWith(in))
        {
            return line.substring(in.length() + 1);
        }

        return inData;
    }   // catchData


    private boolean catchDataBoolean(String line, String in, boolean inData)
    {

        if (inData)
            return inData;

        if (line.startsWith(in))
        {
            return line.substring(in.length()).toLowerCase().contains("yes");
        }

        return false;
    }

    /**
     * core read loop
     */
    public void read()
    {

        try
        {

            String line = null;

            while ((line = m_reader.readLine()) != null)
            {

                m_accessToken = catchData(line, "accesstoken", m_accessToken);
                m_accessTokenSecret = catchData(line, "accesstokensecret", m_accessTokenSecret);

                m_consumerkey = catchData(line, "consumerkey", m_consumerkey);
                m_consumerkeysecret = catchData(line, "consumerkeysecret", m_consumerkeysecret);
                m_useTwitter = catchData(line, "usetwitter", m_useTwitter);

                m_ewsDirectoryLocation = catchData(line, "ews_basedirectoryforconfigs", m_ewsDirectoryLocation);
                m_ewsPythonCreateHoneypotLocation = catchData(line, "ews_pathconfiggenerate", m_ewsPythonCreateHoneypotLocation);

                m_access_token_ppv = catchData(line, "access_token_ppv", m_access_token_ppv);


            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }    // read


}
