package Classes.Global;

import javax.swing.JDesktopPane;
import Classes.Frames.*;
import Classes.Global.Subs.*;

public class GlobalMain 
{
    public static MainFrame fMainFrame;
    public static JDesktopPane mdiPane;

    public static SQLiteWrapper sSQLiteWrapper;
    public static Region sRegion;

    private static boolean debug;

    public static void initialize(boolean _debug)
    {
        debug = _debug;

        sSQLiteWrapper = new SQLiteWrapper("Data/Data.db");
        sRegion = new Region();
    }

    public static boolean isDebug()
    {
        return debug;
    }
}
