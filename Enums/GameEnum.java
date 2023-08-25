package Enums;

import Classes.Global.GlobalMain;

public enum GameEnum 
{
    CHESS("Data/Chess/Rules/%s.html");

    String rulePath;

    GameEnum(String rulePath)
    {
        this.rulePath = rulePath;
    }

    public String getRulePath(boolean showFileName)
    {
        String _rules = rulePath;

        if (showFileName)
        {
            _rules = String.format(_rules, GlobalMain.sRegion.getActiveLanguage());
        }

        return _rules;
    }

}
