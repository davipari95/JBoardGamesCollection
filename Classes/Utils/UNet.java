package Classes.Utils;

import java.net.*;

public class UNet 
{
    
    public static String getLanAddresssString()
    {
        try
        {
            return InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException ex)
        {
            return null;
        }
    }

}
