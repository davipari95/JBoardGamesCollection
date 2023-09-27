package Classes.Frames.InternalFrames.Chess;

import java.awt.Dimension;
import java.io.*;
import java.net.*;
import javax.swing.*;
import Classes.Global.*;
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

        pack();
        setLocation(UInternalFrames.retrivePointForCentering(this, GlobalMain.mdiPane));
    }

    private void translate()
    {
        setTitle(GlobalMain.sRegion.getTranslatedText(54));

        GlobalMain.sRegion.transltateComponentsInContainer(this);
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

                    if (!UStrings.isNullOrEmpty(msg))
                    {
                        switch (msg)
                        {
                            case "ping":
                                response = "ACK\npong\r\n";
                                socketWriter.println(response);
                                break;
                            default:
                                socketWriter.println("INV\r\n");
                                break;
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
    }
}
