package Classes.Frames.InternalFrames.Chess;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import Classes.Global.*;
import Classes.Objects.CustomComponents.*;
import Classes.Utils.*;

public class ChessLanServerSettingsInternalFrame extends JInternalFrame
{

    JTranslatableLabel
        ipAddressTranslatableLabel,
        portTranslatableLabel,
        playerNameTranslatableLabel;
    JPanel
        centralPanel,
        playAsPanel;
    SpinnerNumberModel
        spinnerNumberModel = new SpinnerNumberModel
            (
                Enums.GameEnum.CHESS.getStartingPort(), 
                Enums.GameEnum.CHESS.getStartingPort(), 
                Enums.GameEnum.CHESS.getEndingPort(), 
                1
            );
    JSpinner
        portSpinner;
    JTextField
        playerNameTextField;
    JLabel
        ipAddressValueLabel;
    JTranslatableRadioButton
        playAsWhitesTranslatableRadioButton,
        playAsBlacksTranslatableRadioButton;

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
        //#endregion

        //#region centralPanel
        centralPanel = new JPanel();
        centralPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(centralPanel, BorderLayout.CENTER);
        //#endregion

        //#region ipAddressTranslatableLabel
        ipAddressTranslatableLabel = new JTranslatableLabel(27);
        gbc.gridx = 0;
        gbc.gridy = 0;
        centralPanel.add(ipAddressTranslatableLabel, gbc);
        //#endregion

        //#region ipAddressValueLabel
        ipAddressValueLabel = new JLabel();
        ipAddressValueLabel.setText(UNet.getLanAddresssString());
        gbc.gridx = 1;
        gbc.gridy = 0;
        centralPanel.add(ipAddressValueLabel);
        //#endregion
        
        //#region portTranslatableLabel
        portTranslatableLabel = new JTranslatableLabel(28);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        centralPanel.add(portTranslatableLabel, gbc);
        //#endregion

        //#region portSpinner
        portSpinner = new JSpinner(spinnerNumberModel);
        gbc.gridx = 1;
        gbc.gridy = 1;
        centralPanel.add(portSpinner, gbc);
        //#endregion

        //#region playerNameTranslatableLabel
        playerNameTranslatableLabel = new JTranslatableLabel(38);
        gbc.gridx = 0;
        gbc.gridy = 2;
        centralPanel.add(playerNameTranslatableLabel, gbc);
        //#endregion

        //#region playerNameTextField
        playerNameTextField = new JTextField(6);
        gbc.gridx = 1;
        centralPanel.add(playerNameTextField, gbc);
        //#endregion

        //#region playAsPanel
        playAsPanel = new JPanel();
        playAsPanel.setLayout(new GridLayout(2, 1));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        //playAsPanel.setBorder(BorderFactory.createTitledBorder(GlobalMain.sRegion.getTranslatedText(41));
        //#endregion

        //#region Bottom space
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 1;
        centralPanel.add(new JPanel(), gbc);
        //#endregion

        //#region Right space
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 1;
        centralPanel.add(new JPanel(), gbc);
        //#endregion

        setLocation(UInternalFrames.retrivePointForCentering(this, GlobalMain.mdiPane));
    }

    private void translate()
    {
        setTitle(GlobalMain.sRegion.getTranslatedText(37));

        GlobalMain.sRegion.transltateComponentsInContainer(this);
    }
}
