package Enums;

import javax.swing.ImageIcon;
import Classes.Global.GlobalMain;
import Classes.Utils.UImages;

public enum GameEnum 
{
    //Free ports: 49200 → 65400 | Jump 25

    CHESS("Data/Chess/Rules/%s.html", "Data/FavIcons/Chess.png", 49200);

    String rulePath;
    String faviconPath;
    int startingPort;

    GameEnum(String rulePath, String faviconPath, int startingPort)
    {
        this.rulePath = rulePath;
        this.faviconPath = faviconPath;
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

    public ImageIcon getFavIcon()
    {
        return UImages.getImageIconResized(faviconPath, 16, 16);
    }

    public int getStartingPort()
    {
        return startingPort;
    }

    public int getEndingPort()
    {
        return startingPort + 24;
    }

}
