package Classes.Utils;

import java.net.*;

public class UNet 
{
    /**
     * Retrive the IP4 address of this machine.
     * @return the IP4 address in <code>String</code> format. Return <code>null</code> if the host is unknowed.
     */
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
