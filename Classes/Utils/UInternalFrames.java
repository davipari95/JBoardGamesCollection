package Classes.Utils;

import java.awt.*;
import javax.swing.*;

public class UInternalFrames 
{
    public static Point retrivePointForCentering(JInternalFrame frame, Component component)
    {
        int x = Math.max((component.getWidth() - frame.getWidth()) / 2, 0);
        int y = Math.max((component.getHeight() - frame.getHeight()) / 2, 0);

        return new Point(x, y);
    }
}
