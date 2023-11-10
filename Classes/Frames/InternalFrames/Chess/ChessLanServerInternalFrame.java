package Classes.Frames.InternalFrames.Chess;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import Classes.Global.*;
import Classes.Objects.CustomComponents.*;
import Classes.Utils.*;
import Interfaces.Chess.*;
import Structs.Chess.*;

public class ChessLanServerInternalFrame extends JInternalFrame
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
    Thread
        acceptFirstPlayerThread;
    HashMap<LanChessPlayerProperties, Thread>
        readersThread = new HashMap<>(0);

    int
        FRAME_WIDTH = 800,
        FRAME_HEIGHT = 600,
        INFOPANEL_WIDTH = 500,
        INFOLABEL_WIDTH = 270;

    public ChessLanServerInternalFrame(String firstPlayerName, IChessPiece.ColorEnum firstPlayerColor, String ipAddress, int port)
    {
        super("", false, true, false, true);

        this.firstPlayerName = firstPlayerName;
        this.firstPlayerColor = firstPlayerColor;
        this.ipAddress = ipAddress;
        this.port = port;

        initializeComponents();

        tranlsate();

        acceptFirstPlayerThread = new Thread(new AcceptFirstPlayerClientRunnable());
        acceptFirstPlayerThread.start();
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

    private void printMessage(String message)
    {
        String info = String.format("[%s] >> %s\n", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()), message);
        infoTextArea.append(info);
    }

    private class AcceptFirstPlayerClientRunnable implements Runnable
    {

        @Override
        public void run() 
        {
            try
            {
                ServerSocket server = new ServerSocket(port);
                
                ChessLanClientInternalFrame frame = new ChessLanClientInternalFrame(ipAddress, port, firstPlayerName, firstPlayerColor);
                GlobalMain.mdiPane.add(frame);
                frame.setVisible(true);

                Socket client = server.accept();

                LanChessPlayerProperties player = new LanChessPlayerProperties(1, firstPlayerColor, firstPlayerName, client);
                Thread listenerThread = new Thread(new ListenClientRunnable(player));
                readersThread.put(player, listenerThread);
                listenerThread.start();

                printMessage(String.format("A client is connected [%s]", client.getInetAddress().getHostAddress()));
            }
            catch (IOException ex)
            {
                //TODO: IOException - if an I/O error occurs when opening the socket. -> opening chess server
            }
        }

    }

    private class ListenClientRunnable implements Runnable
    {

        LanChessPlayerProperties
            player;

        ListenClientRunnable(LanChessPlayerProperties player)
        {
            this.player = player;
        }

        @Override
        public void run() 
        {
            while (true)
            {
                try
                {
                    String message = player.getSocketReader().readLine();
                    String returnMessage = "";

                    if (!UStrings.isNullOrEmpty(message))
                    {

                        if (message.matches("^ping$"))
                        {
                            returnMessage = ping();
                        }
                        else if (message.matches("^retrive-players-names$"))
                        {
                            returnMessage = retrivePlayersNames();
                        }
                        else if (message.matches("^set-player-name '.{1,}'$"))
                        {
                            returnMessage = setPlayerName(message);
                        }
                        else
                        {
                            returnMessage = invalid();
                        }
                            
                        player.getSocketWriter().write(returnMessage);
                        player.getSocketWriter().flush();
                    }
                }
                catch (IOException e)
                {
                    //TODO: IOException -> unable to retrive message from client
                }
            }
        }

        private String ping()
        {
            return "ACK\npong\r\n";
        }

        private String invalid()
        {
            return "INV\r\n";
        }

        private String retrivePlayersNames()
        {
            String message = "ACK";

            for (LanChessPlayerProperties playerProperties : readersThread.keySet())
            {
                message += String.format("\n%d;%s;%s", playerProperties.getNr(), playerProperties.getName(), playerProperties.getColor() == IChessPiece.ColorEnum.WHITE ? "white" : "black");
            }

            message += "\r\n";

            return message;
        }

        private String setPlayerName(String message)
        {
            String nameWithoutQuotes = UStrings.getStringBetweenQuotes(message)

            player.setName(nameWithoutQuotes);

            return String.format("ACK\n%s\r\n", nameWithoutQuotes);
        }
        
    }
}
