package Classes.Frames.InternalFrames.Chess;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.*;
import Classes.Global.*;
import Classes.Global.Subs.Region.*;
import Classes.Objects.CustomComponents.*;
import Classes.Utils.*;
import Classes.Utils.UDialogs.IconTypeEnum;
import Interfaces.Chess.*;
import Interfaces.Chess.IChessPiece.*;

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
    TitledBorder
        playAsTitledBorder;
    ButtonGroup
        playAsButtonGroup;
    JTranslatableButton
        openServerTranslatableButton;
    MyActionListener
        myActionListener = new MyActionListener();
    MyRegionListener
        myRegionListener = new MyRegionListener();
    ServerSocket
        lanChessServer;
    String
        ipAddress;

    public ChessLanServerSettingsInternalFrame()
    {
        super("", false, true, false, true);

        ipAddress = UNet.getLanAddresssString();

        initializeComponents();

        translate();

        GlobalMain.sRegion.addRegionListener(myRegionListener);
    }

    private void initializeComponents()
    {
        //#region this
        getContentPane().setPreferredSize(new Dimension(215, 200));
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
        ipAddressTranslatableLabel = new JTranslatableLabel(27); //"IP address:"
        gbc.gridx = 0;
        gbc.gridy = 0;
        centralPanel.add(ipAddressTranslatableLabel, gbc);
        //#endregion

        //#region ipAddressValueLabel
        ipAddressValueLabel = new JLabel();
        ipAddressValueLabel.setText(ipAddress);
        gbc.gridx = 1;
        gbc.gridy = 0;
        centralPanel.add(ipAddressValueLabel);
        //#endregion
        
        //#region portTranslatableLabel
        portTranslatableLabel = new JTranslatableLabel(28); //"Port:"
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        centralPanel.add(portTranslatableLabel, gbc);
        //#endregion

        //#region portSpinner
        portSpinner = new JSpinner(spinnerNumberModel);
        USpinners.removeThousandsSeparator(portSpinner);
        gbc.gridx = 1;
        gbc.gridy = 1;
        centralPanel.add(portSpinner, gbc);
        //#endregion

        //#region playerNameTranslatableLabel
        playerNameTranslatableLabel = new JTranslatableLabel(38); //"Player name:"
        gbc.gridx = 0;
        gbc.gridy = 2;
        centralPanel.add(playerNameTranslatableLabel, gbc);
        //#endregion

        //#region playerNameTextField
        playerNameTextField = new JTextField(6);
        playerNameTextField.setText(GlobalMain.isDebug() ? "Alberto Bianchi" : "");
        gbc.gridx = 1;
        centralPanel.add(playerNameTextField, gbc);
        //#endregion

        //#region playAsPanel
        playAsPanel = new JPanel();
        playAsPanel.setLayout(new GridLayout(2, 1));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        playAsTitledBorder = BorderFactory.createTitledBorder(GlobalMain.sRegion.getTranslatedText(41));
        playAsPanel.setBorder(playAsTitledBorder);
        centralPanel.add(playAsPanel, gbc);
        //#endregion

        //#region playAsWhitesTranslatableRadioButton
        playAsWhitesTranslatableRadioButton = new JTranslatableRadioButton(39); //"Whites"
        playAsWhitesTranslatableRadioButton.setSelected(true);
        playAsPanel.add(playAsWhitesTranslatableRadioButton);
        //#endregion

        //#region playAsBlacksTranslatableRadioButton
        playAsBlacksTranslatableRadioButton = new JTranslatableRadioButton(40); //"Blacks"
        playAsPanel.add(playAsBlacksTranslatableRadioButton);
        //#endregion

        //#region playAsButtonGroup
        playAsButtonGroup = new ButtonGroup();
        playAsButtonGroup.add(playAsWhitesTranslatableRadioButton);
        playAsButtonGroup.add(playAsBlacksTranslatableRadioButton);
        //#endregion

        //#region listenTranslatableButton
        openServerTranslatableButton = new JTranslatableButton(48); //"Start server"
        openServerTranslatableButton.addActionListener(myActionListener);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        centralPanel.add(openServerTranslatableButton, gbc);
        //#endregion

        //#region Bottom space
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
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

        pack();
        setLocation(UInternalFrames.retrivePointForCentering(this, GlobalMain.mdiPane));
    }

    private void translate()
    {
        setTitle(GlobalMain.sRegion.getTranslatedText(37)); //"[LAN - SERVER] Chess - Settings"
        playAsTitledBorder.setTitle(GlobalMain.sRegion.getTranslatedText(41)); //"Play as"

        GlobalMain.sRegion.transltateComponentsInContainer(this);
    }

    private class MyActionListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if (e.getSource() == openServerTranslatableButton)
            {
                String playerName = playerNameTextField.getText();

                if (UStrings.isNullOrEmpty(playerName))
                {
                    UDialogs.showMessageDialogTranslated(10, 51, IconTypeEnum.Warning);
                    return;
                }

                IChessPiece.ColorEnum playerColor = playAsWhitesTranslatableRadioButton.isSelected() ? ColorEnum.WHITE : ColorEnum.BLACK;
                int port = (int) portSpinner.getValue();

                ChessLanServerInternalFrame frame = new ChessLanServerInternalFrame(playerName, playerColor, ipAddress, port);
                GlobalMain.mdiPane.add(frame);
                frame.setVisible(true);
            }
        }
        
    }

    private class MyRegionListener implements RegionListener
    {

        @Override
        public void activeLangaugeChangedEvent(String activeLanguage) 
        {
            translate();
        }
        
    }
}
