package Classes.Utils;

import java.awt.*;
import javax.swing.*;

public class UInternalFrames 
{
    /**
     * Retrive the point to set into the <code>JInternalFrame</code> frame for centering it into a container.<p/>
     * 
     * An example to use this:
     * <pre>{@code
     * JInternalFrame frame = new JInternalFrame();
     * JDesktopPane pane = new JDesktopPane();
     * frame.setPosition(UInternalFrames.retrivePointForCentering(frame, pane));
     * }</pre>
     * 
     * @param frame         frame to center.
     * @param component     componente where centering the frame.
     * @return              point to set into <code>JInternalFrame</code> frame for centering into <code>component</code>.
     */
    public static Point retrivePointForCentering(JInternalFrame frame, Component component)
    {
        int x = Math.max((component.getWidth() - frame.getWidth()) / 2, 0);
        int y = Math.max((component.getHeight() - frame.getHeight()) / 2, 0);

        return new Point(x, y);
    }
}
