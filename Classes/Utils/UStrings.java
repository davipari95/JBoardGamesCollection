package Classes.Utils;

import java.util.regex.Pattern;

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

    public static boolean regexMatch(String pattern, String word)
    {
        return Pattern.compile(pattern).matcher(word).matches();
    }

}
