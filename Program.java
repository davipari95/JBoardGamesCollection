import Classes.Frames.MainFrame;
import Classes.Global.GlobalMain;

public class Program
{
    public static void main(String[] args)
    {
        boolean debug = false;

        if (args.length > 0)
        {
            debug = args[0].equals("debug");
        }

        GlobalMain.initialize(debug);

        GlobalMain.fMainFrame = new MainFrame();
        GlobalMain.fMainFrame.setVisible(true);
    }
}