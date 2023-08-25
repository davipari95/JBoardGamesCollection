package Classes.Objects.Chess;

import java.util.*;
import Classes.Objects.*;
import Classes.Objects.Chess.ChessBoard.*;
import Interfaces.Chess.*;

public class Game 
{
    ChessBoard
        chessBoard;
    String
        whitesPlayerName,
        blacksPlayerName;
    IChessPiece.ColorEnum
        actualTurn = IChessPiece.ColorEnum.WHITE;
    int
        actualTurnNumber;
    ChessPiece
        selectedChessPiece = null;
    ArrayList<GameActionListener> 
        gameActionListeners = new ArrayList<>(0);
    MyChessBoardActionListener
        myChessBoardActionListener = new MyChessBoardActionListener();
    boolean
        gameOver = false;

    public Game(String whitesPlayerName, String blacksPlayerName)
    {
        this.whitesPlayerName = whitesPlayerName;
        this.blacksPlayerName = blacksPlayerName;
        chessBoard = new ChessBoard(this);
        chessBoard.addChessBoardActionListener(myChessBoardActionListener);
    }

    public ChessBoard getChessBoard() 
    { 
        return chessBoard;
    }

    public String getWhitesPlayerName()
    {
        return whitesPlayerName;
    }

    public String getBlacksPlayerName()
    {
        return blacksPlayerName;
    }

    public String getPlayerName(IChessPiece.ColorEnum color)
    {
        switch (color)
        {
            case WHITE:
                return getWhitesPlayerName();
            case BLACK:
                return getBlacksPlayerName();
            default:
                return null;
        }
    }

    public IChessPiece.ColorEnum getActualTurn()
    {
        return actualTurn;
    }

    public void changeTurn()
    {
        actualTurn = 
        (
            getActualTurn() == IChessPiece.ColorEnum.BLACK ? 
            IChessPiece.ColorEnum.WHITE : IChessPiece.ColorEnum.BLACK
        );
        actualTurnNumber++;

        invokeTurnChangedActionEvent(this, actualTurn, actualTurnNumber);
    }

    public int getActualTurnNumber()
    {
        return actualTurnNumber;
    }

    public void setSelectedChessPiece(ChessPiece value)
    {
        if ((selectedChessPiece == null ^ value == null) || !selectedChessPiece.equals(value))
        {
            selectedChessPiece = value;
            invokeSelectedChessPieceChangedActionEvent(this, value);
        }
    }

    public ChessPiece getSelectedChessPiece()
    {
        return selectedChessPiece;
    }

    public boolean getGameOver()
    {
        return gameOver;
    }

    public void setGameOver(boolean value)
    {
        if (gameOver != value)
        {
            gameOver = value;
            invokeGameOverChangedActionEvent(this, value);
        }
    }

    //#region Listener interface

    public void addGameActionListener(GameActionListener l)
    {
        gameActionListeners.add(l);
    }

    public void removeGameActionListener(GameActionListener l)
    {
        gameActionListeners.remove(l);
    }

    private void invokeTurnChangedActionEvent(Game sender, IChessPiece.ColorEnum actualTurn, int actualTurnNr)
    {
        for (GameActionListener l : gameActionListeners)
        {
            l.turnChangedActionEvent(sender, actualTurn, actualTurnNr);
        }
    }

    private void invokeSelectedChessPieceChangedActionEvent(Game sender, ChessPiece selectedPiece)
    {
        for (GameActionListener l : gameActionListeners)
        {
            l.selectedChessPieceChangedActionEvent(sender, selectedPiece);
        }
    }

    private void invokeGameOverChangedActionEvent(Game sender, boolean value)
    {
        for (GameActionListener l : gameActionListeners)
        {
            l.gameOverChangedActionEvent(sender, value);
        }
    }

    public interface GameActionListener
    {
        public void turnChangedActionEvent(Game sender, IChessPiece.ColorEnum actualTurn, int actualTurnNr);
        public void selectedChessPieceChangedActionEvent(Game sender, ChessPiece selectedPiece);
        public void gameOverChangedActionEvent(Game sender, boolean value);
    }

    //#endregion

    private class MyChessBoardActionListener implements ChessBoardActionListener
    {

        @Override
        public void pieceMovedActionEvent(ChessBoard sender, ChessPiece piece, GridPosition newGridPosition) 
        {
            changeTurn();
        }
        
    }
}
