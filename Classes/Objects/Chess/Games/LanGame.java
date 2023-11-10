package Classes.Objects.Chess.Games;

import Classes.Objects.Chess.Game;

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
}
