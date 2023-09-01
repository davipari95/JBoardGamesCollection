package Classes.Utils;

import javax.swing.*;
import java.awt.*;

public class UImages
{
    
    /**
     * Resize an image.<p/>
     * The image cannot mantains the same size ratio, so, be sure to calculate correctly <code>width</code> and <code>height</code>.
     * @param image     input image.
     * @param width     width of the resized image.
     * @param height    height of the resized image.
     * @return          a new image with width and height inserted in <code>width</code> and <code>height</code>.
     */
    public static ImageIcon resizeImageIcon(ImageIcon image, int width, int height)
    {
        return new ImageIcon(image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    /**
     * Resize an image.<p/>
     * The image cannot mantains the same size ratio, so, be sure to calculate correctly <code>width</code> and <code>height</code>.
     * @param path      path of the image.
     * @param width     width of the resized image.
     * @param height    height of the resized image.
     * @return          a new image with width and height inserted in <code>width</code> and <code>height</code>.
     */
    public static ImageIcon getImageIconResized(String path, int width, int height)
    {
        return resizeImageIcon(new ImageIcon(path), width, height);
    }

}
