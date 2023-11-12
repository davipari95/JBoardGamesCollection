package Classes.Objects.Chess.Games;

import Classes.Objects.Chess.Game;
import Interfaces.Chess.IChessPiece;

public class LanGame extends Game
{
    public LanGame()
    {
        super("", "");
    }

    public void setWhitesPlayerName(String whitesPlayerName)
    {
        this.whitesPlayerName = whitesPlayerName;
    }

    public void setBlacksPlayerName(String blacksPlayerName)
    {
        this.blacksPlayerName = blacksPlayerName;
    }

    public void setPlayerName(IChessPiece.ColorEnum color, String playerName)
    {
        switch (color) 
        {
            case WHITE:
                setWhitesPlayerName(playerName);
                break;
            case BLACK:
                setBlacksPlayerName(playerName);
                break;
        }
    }
}
