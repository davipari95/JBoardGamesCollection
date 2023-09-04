package Classes.Frames.InternalFrames.Chess;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import Classes.Global.*;
import Interfaces.Chess.*;

public class ChessLanServerFrame extends JInternalFrame
{
    JTextArea
        infoTextArea;
    JPanel
        infoPanel;
    TitledBorder
        infoTitledBorder;
    int
        FRAME_WIDTH = 800,
        FRAME_HEIGHT = 600,
        INFOPANEL_WIDTH = 500;

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
        getContentPane().setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setLayout(null);
        //#endregion

        //#region infoPanel
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(1, 1));
        infoTitledBorder = BorderFactory.createTitledBorder(GlobalMain.sRegion.getTranslatedText(50));
        infoPanel.setBorder(infoTitledBorder);
        infoPanel.setLocation(10, 10);
        infoPanel.setSize(INFOPANEL_WIDTH, FRAME_HEIGHT - (2 * 10));
        add(infoPanel);
        //#endregion

        pack();
    }
}
