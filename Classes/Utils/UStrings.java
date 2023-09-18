package Classes.Utils;

public class UStrings 
{
    
    public static boolean isNullOrEmpty(String s)
    {
        if (s == null)
        {
            return true;
        }

        return s.replace(" ", "").length() == 0;
    }

    public static String convertTextInHtmlString(String text)
    {
        String s = "<html>";

        s += text.replace("\n", "<br>").replace("\r", "");
        s += "</html>";

        return s;
    }

}
