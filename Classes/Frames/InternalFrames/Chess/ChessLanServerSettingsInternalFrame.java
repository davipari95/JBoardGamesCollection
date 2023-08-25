package Classes.Frames.InternalFrames.Chess;

import java.awt.BorderLayout;

import javax.swing.*;

import Classes.Global.GlobalMain;
import Classes.Objects.CustomComponents.*;
import Classes.Utils.*;

public class ChessLanServerSettingsInternalFrame extends JInternalFrame
{

    JTranslatableLabel
        ipAddressTranslatableLabel,
        portTranslatableLabel;
    JPanel
        centralPanel,
        portPanel;

    public ChessLanServerSettingsInternalFrame()
    {
        super("", !false, true, false, true);

        initializeComponents();

        translate();
    }

    private void initializeComponents()
    {
        //#region this
        setSize(800, 600);
        setLayout(new BorderLayout());
        //#endregion

        //#region centralPanel
        centralPanel = new JPanel();
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
        add(centralPanel, BorderLayout.CENTER);
        //#endregion

        //#region ipAddressTranslatableLabel
        ipAddressTranslatableLabel = new JTranslatableLabel(27);
        ipAddressTranslatableLabel.setArgs(UNet.getLanAddresssString());
        centralPanel.add(ipAddressTranslatableLabel);
        //#endregion

        //#region portPanel
        portPanel = new JPanel();
        portPanel.setLayout(new BoxLayout(portPanel, BoxLayout.X_AXIS));
        centralPanel.add(portPanel);
        //#endregion

        //#region portTranslatableLabel
        portTranslatableLabel = new JTranslatableLabel(28);
        portPanel.add(portTranslatableLabel);
        //#endregion
    }

    private void translate()
    {
        setTitle(GlobalMain.sRegion.getTranslatedText(37));

        GlobalMain.sRegion.transltateComponentsInContainer(this);
    }
}
