package Classes.Utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class UFiles 
{
    
    /**
     * Open a html file with the default browser.
     * @param htmlPath  path of the html file.
     * @return          <code>true</code> if the file exists and the file is opening successufully, otherwise <code>false</code>.
     */
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
