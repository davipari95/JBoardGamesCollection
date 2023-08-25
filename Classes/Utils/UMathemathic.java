package Classes.Utils;

import Classes.CustomExceptions.*;

public class UMathemathic 
{
    public static boolean isBetween(long n, long min, long max)
    {
        if (min > max)
        {
            throw new ArgumentsException("Min value should be less than max value");
        }

        return ((n >= min) && (n <= max));
    }

    public static boolean isBetween(double n, double min, double max)
    {
        if (min > max)
        {
            throw new ArgumentsException("Min value should be less than max value");
        }

        return ((n >= min) && (n <= max));
    }
}
