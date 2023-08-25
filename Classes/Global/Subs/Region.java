package Classes.Global.Subs;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import Classes.Global.*;
import Classes.Global.Subs.SQLiteWrapper.*;
import Interfaces.*;

public class Region 
{
    HashMap<Long, HashMap<String, String>> 
        translations;
    HashMap<String, String> 
        availableRegions;
    String 
        activeLanguage = "eng";
    ArrayList<RegionListener>
        regionListeners = new ArrayList<>(0);

    public Region()
    {
        loadAvailableLanguages();
        loadTranslations();
        loadActiveLanguageFromDB();
    }

    public String getTranslatedText(long id)
    {
        return translations.get(id).get(activeLanguage);
    }

    public String getTranslatedText(long id, Object... args)
    {
        return String.format(getTranslatedText(id), args);
    }

    public void transltateComponentsInContainer(Container frame)
    {
        Component[] components = frame.getComponents();

        for (Component c : components)
        {
            if (c instanceof ITranslatable)
            {
                translateITranslatableElement((ITranslatable)c);
            }

            if (c instanceof JMenu)
            {
                for (MenuElement e : ((JMenu)c).getSubElements())
                {
                    translateMenuElement(e);
                }
            }
            else if (c instanceof Container)
            {
                transltateComponentsInContainer((Container)c);
            }
        }
    }

    public String getActiveLanguage()
    {
        return activeLanguage;
    }

    public HashMap<String, String> getAvailableRegions()
    {
        return availableRegions;
    }

    public void setActiveLanguage(String value)
    {
        if (value != activeLanguage)
        {
            activeLanguage = value;
            saveActiveLanguageOnDB(value);
            invokeActiveLanguageChangedEvent(value);
        }
    }

    public void addRegionListener(RegionListener l)
    {
        regionListeners.add(l);
    }

    public void removeRegionListener(RegionListener l)
    {
        regionListeners.remove(l);
    }

    @Override
    public String toString()
    {
        return String.format("LocalLanguage=\"%s\"; NrOfTranslations=%d", activeLanguage, translations.size());
    }

    private void translateMenuElement(MenuElement e)
    {
        if (e instanceof ITranslatable)
        {
            translateITranslatableElement((ITranslatable)e);
        }

        for (MenuElement element : e.getSubElements())
        {
            translateMenuElement(element);
        }
    }

    public void translateITranslatableElement(ITranslatable e)
    {
        long languageId = e.getLanguageReference();
        String text = getTranslatedText(languageId, e.getArgs());

        if (e instanceof JLabel)
        {
            ((JLabel)e).setText(text);
        }
        else if (e instanceof AbstractButton)
        {
            ((AbstractButton)e).setText(text);
        }
    }

    private void loadAvailableLanguages()
    {
        availableRegions = new HashMap<>(0);

        String sqlQuery = "SELECT * FROM AvailableRegions";
        ArrayList<HashMap<String, DbResultSet>> data = GlobalMain.sSQLiteWrapper.executeReaderQuery(sqlQuery);

        for (HashMap<String, DbResultSet> map : data)
        {
            String id = (String)map.get("ID").getResult();
            String description = (String)map.get("Description").getResult();

            availableRegions.put(id, description);
        }
    }

    private void loadTranslations()
    {
        translations = new HashMap<>(0);

        String sqlQuery = "SELECT * FROM Translations";
        ArrayList<HashMap<String, DbResultSet>> data = GlobalMain.sSQLiteWrapper.executeReaderQuery(sqlQuery);

        for (HashMap<String, DbResultSet> map : data)
        {
            long id = (long)(int) map.get("ID").getResult();

            HashMap<String, String> stranslates = new HashMap<>(0);

            for (String r : availableRegions.keySet())
            {
                stranslates.put(r, (String)map.get(r).getResult());
            }

            translations.put(id, stranslates);
        }
    }

    private void loadActiveLanguageFromDB()
    {
        String sqlQuery = "SELECT ActiveRegion FROM Settings";
        ArrayList<HashMap<String, DbResultSet>> data = GlobalMain.sSQLiteWrapper.executeReaderQuery(sqlQuery);

        activeLanguage = (String) data.get(0).get("ActiveRegion").getResult();
    }

    private void saveActiveLanguageOnDB(String langauge)
    {
        String sqlQuery = String.format("UPDATE Settings SET ActiveRegion='%s'", langauge);
        GlobalMain.sSQLiteWrapper.executeQuery(sqlQuery);
    }

    private void invokeActiveLanguageChangedEvent(String activeLanguage)
    {
        for (RegionListener l : regionListeners) l.activeLangaugeChangedEvent(activeLanguage);
    }

    public interface RegionListener
    {
        public void activeLangaugeChangedEvent(String activeLanguage);
    }

}
