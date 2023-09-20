package Classes.Frames.InternalFrames.Chess;

import java.io.*;
import java.net.*;
import javax.swing.*;
import Classes.Utils.*;
import Interfaces.Chess.*;

public class ChessLanClientFrame extends JInternalFrame
{
    Socket
        client;
    PrintWriter
        socketWriter;
    Thread
        serverListener;
    boolean
        listenServer = false;

    public ChessLanClientFrame(String ipAddress, int port, String playerName, IChessPiece.ColorEnum playerColor)
    {
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
    }

    private void initializeComponents()
    {

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
