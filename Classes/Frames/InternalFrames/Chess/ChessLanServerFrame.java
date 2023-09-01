package Classes.Frames.InternalFrames.Chess;

import java.awt.*;
import javax.swing.*;
import Classes.Global.*;
import Interfaces.Chess.*;

public class ChessLanServerFrame extends JInternalFrame
{
    JTextArea
        infoTextArea;
    Panel
        centralPanel;

    public ChessLanServerFrame(String firstPlayerName, IChessPiece.ColorEnum firstPlayerColor)
    {
        super("", !false, true, false, true);

        initializeComponents();
    }

    private void initializeComponents()
    {
        //#region this
        setSize(800, 600);
        setTitle(GlobalMain.sRegion.getTranslatedText(49));
        //#endregion

        //#region centralPanel
        centralPanel = new Panel();
        centralPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.VERTICAL;
        
        //#endregion

        //#region infoTextArea
        infoTextArea = new JTextArea();
        infoTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
        //#endregion
    }
}
