package Structs.Chess;

import java.io.*;
import java.net.*;
import java.util.*;

import Interfaces.Chess.*;

public class LanChessPlayerProperties 
{
    int 
        playerNr = 0;
    IChessPiece.ColorEnum 
        playerColor = IChessPiece.ColorEnum.WHITE;
    String 
        playerName = "";
    Socket
        playerSocket;
    PrintWriter
        socketWriter;
    BufferedReader
        socketReader;
    ArrayList<LanChessPlayerPropertiesListener>
        lanChessPlayerPropertiesListeners = new ArrayList<>(0);

    public LanChessPlayerProperties(int playerNr, IChessPiece.ColorEnum playerColor, String playerName, Socket playerSocket) throws IOException
    {
        this.playerNr = playerNr;
        this.playerColor = playerColor;
        this.playerName = playerName;
        this.playerSocket = playerSocket;
        socketWriter = new PrintWriter(playerSocket.getOutputStream());
        socketReader = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
    }

    public int getNr()
    {
        return playerNr;
    }

    public void setNr(int value)
    {
        if (value != playerNr)
        {
            playerNr = value;
        }
    }

    public IChessPiece.ColorEnum getColor()
    {
        return playerColor;
    }

    public String getName()
    {
        return playerName;
    }

    public void setName(String value)
    {
        if (value != playerName)
        {
            playerName = value;
            onPlayerNameChanged();
        }
    }

    public Socket getSocket()
    {
        return playerSocket;
    }

    public PrintWriter getSocketWriter()
    {
        return socketWriter;
    }

    public BufferedReader getSocketReader()
    {
        return socketReader;
    }

    public void addLanChessPlayerPropertiesListener(LanChessPlayerPropertiesListener listener)
    {
        lanChessPlayerPropertiesListeners.add(listener);
    }

    public void removeLanChessPlayerPropertiesListener(LanChessPlayerPropertiesListener listener)
    {
        lanChessPlayerPropertiesListeners.remove(listener);
    }

    private void onPlayerNameChanged()
    {
        for (LanChessPlayerPropertiesListener l : lanChessPlayerPropertiesListeners)
        {
            l.playerNameChanged(this);
        }
    }

    public interface LanChessPlayerPropertiesListener 
    {
        public void playerNameChanged(LanChessPlayerProperties sender);
    }
}
