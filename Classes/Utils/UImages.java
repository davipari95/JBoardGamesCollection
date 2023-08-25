package Classes.Utils;

import javax.swing.*;
import java.awt.*;

public class UImages
{
    
    public static ImageIcon resizeImageIcon(ImageIcon image, int width, int height)
    {
        return new ImageIcon(image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    public static ImageIcon getImageIconResized(String path, int width, int height)
    {
        return resizeImageIcon(new ImageIcon(path), width, height);
    }

}
