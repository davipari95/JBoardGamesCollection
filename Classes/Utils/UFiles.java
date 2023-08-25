package Classes.Utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class UFiles 
{
    
    public static boolean openHtmlFileWithDefaultBrowser(String htmlPath)
    {
        File htmlFile = new File(htmlPath);
        
        try 
        {
            Desktop.getDesktop().browse(htmlFile.toURI());
            return true;
        } 
        catch (IOException e) 
        {
            return false;
        }
    }

}
