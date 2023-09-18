package Classes.Frames.InternalFrames.Chess;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import Classes.Global.*;
import Classes.Objects.CustomComponents.*;
import Classes.Utils.*;
import Interfaces.Chess.*;

public class ChessLanServerFrame extends JInternalFrame
{
    JTextArea
        infoTextArea;
    JScrollPane
        infoPanel;
    TitledBorder
        infoTitledBorder;
    JTranslatableLabel
        infoTranslatableLabel;
    String
        firstPlayerName,
        ipAddress;
    int
        port;
    IChessPiece.ColorEnum
        firstPlayerColor;

    int
        FRAME_WIDTH = 800,
        FRAME_HEIGHT = 600,
        INFOPANEL_WIDTH = 500,
        INFOLABEL_WIDTH = 270;

    public ChessLanServerFrame(String firstPlayerName, IChessPiece.ColorEnum firstPlayerColor, String ipAddress, int port)
    {
        super("", false, true, false, true);

        this.firstPlayerName = firstPlayerName;
        this.firstPlayerColor = firstPlayerColor;
        this.ipAddress = ipAddress;
        this.port = port;

        initializeComponents();

        tranlsate();
    }

    private void initializeComponents()
    {
        //#region this
        setSize(800, 600);
        setTitle(GlobalMain.sRegion.getTranslatedText(49));
        getContentPane().setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setLayout(null);
        //#endregion

        //#region infoTextArea
        infoTextArea = new JTextArea();
        infoTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        infoTextArea.setEditable(false);
        //#endregion

        //#region infoPanel
        infoPanel = new JScrollPane(infoTextArea);
        infoTitledBorder = BorderFactory.createTitledBorder(GlobalMain.sRegion.getTranslatedText(50));
        infoPanel.setBorder(infoTitledBorder);
        infoPanel.setLocation(10, 10);
        infoPanel.setSize(INFOPANEL_WIDTH, FRAME_HEIGHT - (2 * 10));
        add(infoPanel);
        //#endregion

        //#region infoTranslatableLabel
        infoTranslatableLabel = new JTranslatableLabel(52, ipAddress, port, firstPlayerName, firstPlayerColor.getName());
        infoTranslatableLabel.setLocation(10 + INFOPANEL_WIDTH + 10, 10);
        infoTranslatableLabel.setSize(INFOLABEL_WIDTH, FRAME_HEIGHT - (2 * 10));
        infoTranslatableLabel.setVerticalAlignment(JTranslatableLabel.TOP);
        add(infoTranslatableLabel);
        //#endregion

        pack();
        setLocation(UInternalFrames.retrivePointForCentering(this, GlobalMain.mdiPane));
    }

    private void tranlsate()
    {
        GlobalMain.sRegion.transltateComponentsInContainer(this);

        infoTranslatableLabel.setText(UStrings.convertTextInHtmlString(infoTranslatableLabel.getText()));
    }
}
