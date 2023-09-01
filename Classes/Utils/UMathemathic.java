package Classes.Utils;

import Classes.CustomExceptions.*;

public class UMathemathic 
{
    /**
     * Check if a number is beetween two numbers.<p/>
     * <code>min</code> and <code>max</code> are inclusive.
     * <ul>
     *  <li> <code>isBetween(3, 1, 5) = true</code> </li>
     *  <li> <code>isBetween(1, 1, 5) = true</code> </li>
     *  <li> <code>isBetween(6, 1, 5) = false</code> </li>
     *  <li> <code>isBetween(3, 5, 1) = ArgumentsException</code> </li>
     * </ul>
     * 
     * <pre>&nbsp;</pre><p/>
     * 
     * @param n     number to check.
     * @param min   checking from.
     * @param max   checking to.
     * @return      <code>true</code> if <code>n >= min</code> and <code>n <= max</code>, otherwise <code>false</code>.
     * @exception   ArgumentsException if <code>min > max</code>
     */
    public static boolean isBetween(long n, long min, long max)
    {
        if (min > max)
        {
            throw new ArgumentsException("Min value should be less than max value");
        }

        return ((n >= min) && (n <= max));
    }

    /**
     * Check if a number is beetween two numbers.<p/>
     * <code>min</code> and <code>max</code> are inclusive.
     * <ul>
     *  <li> <code>isBetween(3, 1, 5) = true</code> </li>
     *  <li> <code>isBetween(1, 1, 5) = true</code> </li>
     *  <li> <code>isBetween(6, 1, 5) = false</code> </li>
     *  <li> <code>isBetween(3, 5, 1) = ArgumentsException</code> </li>
     * </ul>
     * 
     * <pre>&nbsp;</pre><p/>
     * 
     * @param n     number to check.
     * @param min   checking from.
     * @param max   checking to.
     * @return      <code>true</code> if <code>n >= min</code> and <code>n <= max</code>, otherwise <code>false</code>.
     * @exception   ArgumentsException if <code>min > max</code>
     */
    public static boolean isBetween(double n, double min, double max)
    {
        if (min > max)
        {
            throw new ArgumentsException("Min value should be less than max value");
        }

        return ((n >= min) && (n <= max));
    }
}
