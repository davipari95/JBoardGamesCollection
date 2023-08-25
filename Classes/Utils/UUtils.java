package Classes.Utils;

public class UUtils 
{
    /**
     * Retrive all values beetween <b>value1</b> and <b>value2</b> in ascending order.
     * Input values are included.<p/>
     * For example:
     * <ul>
     * <li> <code>getRangeValuesOrdered(3, 7) = [3, 4, 5, 6, 7]</code> </li>
     * <li> <code>getRangeValuesOrdered(7, 3) = [3, 4, 5, 6, 7]</code> </li>
     * </ul>
     * <pre>&nbsp;</pre>
     * <p/>
     * 
     * @param value1    first value.
     * @param value2    second value.
     * @return array of integer containing all numbers beetween the two parameters.
     */
    public static int[] getRangeValuesOrdered(int value1, int value2)
    {
        int min = Math.min(value1, value2);
        int size = Math.abs(value1 - value2 + 1);

        int values[] = new int[size];

        for (int i=0; i<size; i++)
        {
            values[i] = min + i;
        }

        return values;
    }

    /**
     * Retrive all values beetween <b>start</b> and <b>stop</b>.
     * Values are ordered from <b>start</b> to <b>stop</b>.
     * Input values are included.<p/>
     * For example:
     * <ul>
     * <li> <code>getRangeValuesOrdered(3, 7) = [3, 4, 5, 6, 7]</code> </li>
     * <li> <code>getRangeValuesOrdered(7, 3) = [7, 6, 5, 4, 3]</code> </li>
     * </ul>
     * <pre>&nbsp;</pre>
     * <p/>
     * @param start starting value (included)
     * @param stop  ending value (included)
     * @return  array of integer all numbers beetween the two parameters.
     */
    public static int[] getRangeValues(int start, int stop)
    {
        int inc = stop > start ? +1 : -1;
        int size = Math.abs(start - stop) + 1;

        int[] values = new int[size];

        for (int i=0, j=start; i < size; i++, j += inc)
        {
            values[i] = j;
        }

        return values;
    }
}
