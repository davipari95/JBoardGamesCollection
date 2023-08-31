package Structs.Chess;

import Interfaces.Chess.*;

public class LanChessPlayerProperties 
{
    int 
        playerNr = 0;
    IChessPiece.ColorEnum 
        playerColor = IChessPiece.ColorEnum.WHITE;
    String 
        playerName = "";

    public LanChessPlayerProperties(int playerNr, IChessPiece.ColorEnum playerColor, String playerName)
    {
        this.playerNr = playerNr;
        this.playerColor = playerColor;
        this.playerName = playerName;
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

    
}
