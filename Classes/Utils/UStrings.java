package Classes.Utils;

import java.util.regex.Pattern;
import Classes.CustomExceptions.*;

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

    public static String getStringBetweenQuotes(String stringWithQuotes)
    {
        if (!stringWithQuotes.contains("'"))
        {
            throw new ArgumentsException("String must have quotes.");
        }

        int start = stringWithQuotes.indexOf("'");
        int end = stringWithQuotes.lastIndexOf("'");

        if (start == end)
        {
            throw new ArgumentsException("String must have at least two quotes.");
        }

        return stringWithQuotes.substring(start + 1, end);
    }

}
