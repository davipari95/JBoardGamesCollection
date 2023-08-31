package Classes.Frames.InternalFrames.Chess;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import javax.swing.*;
import javax.swing.border.*;
import Classes.Global.*;
import Classes.Global.Subs.Region.*;
import Classes.Objects.CustomComponents.*;
import Classes.Utils.*;
import Classes.Utils.UDialogs.*;

public class ChessLanServerSettingsInternalFrame extends JInternalFrame
{

    JTranslatableLabel
        ipAddressTranslatableLabel,
        portTranslatableLabel,
        playerNameTranslatableLabel,
        infoTranslatableLabel;
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
        listenTranslatableButton;
    MyActionListener
        myActionListener = new MyActionListener();
    MyRegionListener
        myRegionListener = new MyRegionListener();
    ServerSocket
        lanChessServer;
    ListenServerRunnable
        serverListenerThread = null;

    public ChessLanServerSettingsInternalFrame()
    {
        super("", false, true, false, true);

        initializeComponents();

        translate();

        GlobalMain.sRegion.addRegionListener(myRegionListener);
    }

    private void initializeComponents()
    {
        //#region this
        getContentPane().setPreferredSize(new Dimension(230, 240));
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
        ipAddressValueLabel.setText(UNet.getLanAddresssString());
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
        listenTranslatableButton = new JTranslatableButton(48); //"Start server"
        listenTranslatableButton.addActionListener(myActionListener);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        centralPanel.add(listenTranslatableButton, gbc);
        //#endregion

        //#region infoTranslatableLabel
        infoTranslatableLabel = new JTranslatableLabel(44); //"-"
        gbc.gridy = 5;
        centralPanel.add(infoTranslatableLabel, gbc);
        //#endregion

        //#region Bottom space
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
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

    private void updateGUIServerListening(boolean isListening)
    {
        infoTranslatableLabel.setLanguageReference(isListening ? 43 : 44);
        GlobalMain.sRegion.translateITranslatableElement(infoTranslatableLabel);

        listenTranslatableButton.setLanguageReference(isListening ? 47 : 42);
        GlobalMain.sRegion.translateITranslatableElement(listenTranslatableButton);

        portSpinner.setEnabled(!isListening);

        playerNameTextField.setEnabled(!isListening);

        playAsWhitesTranslatableRadioButton.setEnabled(!isListening);
        playAsBlacksTranslatableRadioButton.setEnabled(!isListening);
    }

    private void clientConnectedInvoked(ServerSocket server, Socket client)
    {

    }

    private class MyActionListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if (e.getSource() == listenTranslatableButton)
            {
                if (serverListenerThread == null)
                {
                    try 
                    {
                        int port = (int) portSpinner.getValue();
                        lanChessServer = new ServerSocket(port);
                        serverListenerThread = new ListenServerRunnable(lanChessServer);
                        serverListenerThread.start();
                    }
                    catch (IOException ex)
                    {
                        UDialogs.showMessageDialogTranslated(45, 46, IconTypeEnum.Error);
                    }
                }
                else 
                {
                    serverListenerThread.stopRunning();
                    serverListenerThread = null;
                }

                updateGUIServerListening(serverListenerThread != null);
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

    private class ListenServerRunnable extends Thread
    {
        ServerSocket
            server;

        public ListenServerRunnable(ServerSocket server)
        {
            this.server = server;
        }

        @Override
        public void run() 
        {
            try 
            {
                Socket client = server.accept();
                clientConnectedInvoked(lanChessServer, client);
            }
            catch (SocketException e)
            {
                System.out.println("Stop listening");
            }
            catch (IOException e) 
            {
                UDialogs.showMessageDialogTranslated(45, 46, IconTypeEnum.Error);
            }
        }
        
        public void stopRunning()
        {
            try 
            {
                server.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }
}
