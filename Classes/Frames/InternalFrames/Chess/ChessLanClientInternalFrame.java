package Classes.Frames.InternalFrames.Chess;

import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import Classes.Global.*;
import Classes.Objects.CustomComponents.*;
import Classes.Utils.*;
import Interfaces.Chess.*;

public class ChessLanClientInternalFrame extends JInternalFrame
{
    Socket
        client;
    PrintWriter
        socketWriter;
    Thread
        serverListener;
    boolean
        listenServer = false;
    JLabel
        chessboardBackGroud;
    JGridLabel[][]
        chessboardGridLabel;
    String[]
        tcpResponse = null;
    final int
        CHESSBOARD_SIZE = 800,
        GRID_SIZE = CHESSBOARD_SIZE / 8,
        GRID_PADDING = 5,
        INFO_LABEL_HEIGHT = 30;

    public ChessLanClientInternalFrame(String ipAddress, int port, String playerName, IChessPiece.ColorEnum playerColor)
    {
        super("", false, true, false, true);

        try
        {
            client = new Socket(ipAddress, port);

            socketWriter = new PrintWriter(client.getOutputStream());

            listenServer = true;
            serverListener = new Thread(new ListenServerRunnable(new BufferedReader(new InputStreamReader(client.getInputStream()))));
            serverListener.start();
        }
        catch (IOException e)
        {
            UDialogs.showMessageDialogTranslated(45, 53, UDialogs.IconTypeEnum.Error);
            dispose();
        }

        initializeComponents();

        translate();
    }

    private void initializeComponents()
    {
        //#region this
        getContentPane().setPreferredSize(new Dimension(CHESSBOARD_SIZE, CHESSBOARD_SIZE + INFO_LABEL_HEIGHT));
        //setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(null);
        //#endregion

        //#region chessBoardBackGroud
        chessboardBackGroud = new JLabel();
        chessboardBackGroud.setBounds(0, 0, CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        chessboardBackGroud.setIcon(new ImageIcon("Data/Chess/Assets/Pictures/ChessBoard.png"));
        chessboardBackGroud.setLayout(null);
        add(chessboardBackGroud);
        //#endregion

        //#region chessboardGridLabel
        chessboardGridLabel = new JGridLabel[8][8];
        for (int i=0; i<chessboardGridLabel.length; i++)
        {
            for (int j=0; j<chessboardGridLabel[i].length; j++)
            {
                chessboardGridLabel[i][j] = new JGridLabel(i, j);
                chessboardGridLabel[i][j].setSize(GRID_SIZE - (2 * GRID_PADDING), GRID_SIZE - (2 * GRID_PADDING));
                chessboardGridLabel[i][j].setLocation((j * GRID_SIZE) + 10, (i * GRID_SIZE) + 10);
                chessboardBackGroud.add(chessboardGridLabel[i][j]);
            }
        }
        //#endregion

        pack();
        setLocation(UInternalFrames.retrivePointForCentering(this, GlobalMain.mdiPane));
    }

    private void translate()
    {
        setTitle(GlobalMain.sRegion.getTranslatedText(54));

        GlobalMain.sRegion.transltateComponentsInContainer(this);
    }

    private void sendTCPMessage(String message)
    {
        socketWriter.write(message);
        socketWriter.flush();
    }

    private boolean setTCPUsername(String username)
    {
        String message = String.format("set-player-name '%s'", username);
        sendTCPMessage(message);

        while (tcpResponse == null);

        boolean ok = username == tcpResponse[1];
        tcpResponse = null;

        return ok;
    }

    private class ListenServerRunnable implements Runnable
    {

        BufferedReader
            socketReader;
        
        ListenServerRunnable(BufferedReader socketReader)
        {
            this.socketReader = socketReader;
        }

        @Override
        public void run() 
        {
            while (listenServer)
            {
                try
                {
                    String msg = socketReader.readLine();
                    String response = "";
                    boolean sendResponse = true;

                    if (!UStrings.isNullOrEmpty(msg))
                    {
                        if (msg.matches("$ping^"))
                        {
                            response = ping();
                        }
                        else if (msg.contains("ACK"))
                        {
                            tcpResponse = UStrings.removeTCPSeparatoStrings(response);
                            sendResponse = false;
                        }
                        else
                        {
                            response = invalid();
                        }

                        if (sendResponse)
                        {
                            sendTCPMessage(response);
                        }
                    }
                }
                catch (IOException e)
                {
                    System.out.println(e.getMessage());
                    //TODO: IOException -> problem reading line from server
                }
            }
        }

        public String ping()
        {
            return "ACK\npong\r\n";
        }

        public String invalid()
        {
            return "INV\r\n";
        }
    }
}
