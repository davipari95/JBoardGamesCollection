package Classes.Objects.CustomComponents;

import java.util.*;
import javax.swing.*;

import Classes.CustomExceptions.KeyNotFoundException;

public class JHashMapComboBox<T> extends JComboBox<String>
{
    HashMap<T, String>
        map;

    public JHashMapComboBox(HashMap<T, String> map, boolean sort)
    {
        this.map = new HashMap<>(map);

        initializeList(sort);
    }

    public String getSelectedValue()
    {
        return (String) getSelectedItem();
    }

    public void setSelectedValue(String value)
    {
        setSelectedItem(value);
    }

    public T getSelectedKey()
    {
        String selectedValue = getSelectedValue();

        for (T key : map.keySet())
        {
            if (map.get(key) == selectedValue)
            {
                return key;
            }
        }

        throw new KeyNotFoundException("Unable to retrive the key in function getSelectedKey()");
    }

    public void setSelectedKey(T key)
    {
        if (map.containsKey(key))
        {
            String value = map.get(key);
            setSelectedValue(value);
        }
        else 
        {
            throw new KeyNotFoundException("Key doesn't exists in this map.");
        }
    }

    private void initializeList(boolean sort)
    {
        ArrayList<String> values = new ArrayList<String>(map.values()); 

        if (sort)
        {
            Collections.sort(values);
        }

        for (String value : values)
        {
            addItem(value);
        }
    }
}
