package Classes.Objects.Chess;

import java.util.*;
import Classes.Objects.*;
import Classes.Objects.Chess.ChessPiece.*;
import Classes.Objects.Chess.Pieces.*;
import Classes.Utils.*;
import Interfaces.*;
import Interfaces.Chess.*;
import Interfaces.Chess.IChessPiece.KindEnum;
import Structs.Chess.*;

public class ChessBoard implements ICopyable<ChessBoard>
{
    ArrayList<ChessPiece> 
        chessPieces;
    Game 
        game;
    ArrayList<ChessBoardActionListener>
        chessBoardActionListeners = new ArrayList<>(0);
    MyChessPieceActionListener
        myChessPieceActionListener = new MyChessPieceActionListener();

    public ChessBoard(Game game)
    {
        this.game = game;

        initialize();
    }

    public Game getGame() 
    { 
        return game;
    }

    public ChessPiece[] getChessPieces()
    {
        return chessPieces.toArray(new ChessPiece[0]);
    }

    public ChessPiece getPieceInPosition(GridPosition position)
    {
        for (ChessPiece p : chessPieces)
        {
            if (p.actualPosition.equals(position))
            {
                return p;
            }
        }

        return null;
    }

    public void eatPieceInPosition(ChessPiece eater, GridPosition eatPosition)
    {
        ChessPiece toEat = getPieceInPosition(eatPosition);

        if (toEat != null && toEat.getColor() != eater.getColor())
        {
            chessPieces.remove(toEat);
        }
        else
        {
            throw new RuntimeException("Something goes wrong. Piece to eat is null or a piece is trying to eat a " +
                "piece of the same color.");
        }
    }

    public void eatPiece(ChessPiece eater, ChessPiece toEat)
    {
        if (toEat != null && toEat.getColor() != eater.getColor())
        {
            chessPieces.remove(toEat);
        }
        else
        {
            throw new RuntimeException("Something goes wrong. Piece to eat is null or a piece is trying to eat a " +
                    "piece of the same color.");
        }
    }

    public ChessPiece[] getPieces(IChessPiece.ColorEnum color)
    {
        return chessPieces
            .stream()
            .filter(x -> x.getColor() == color)
            .toArray(ChessPiece[]::new);
    }

    public King getKing(IChessPiece.ColorEnum color)
    {
        return (King) chessPieces
            .stream()
            .filter(p -> (p.getColor() == color && p.getKind() == IChessPiece.KindEnum.KING))
            .findFirst()
            .get();
    }

    public boolean isCheck(IChessPiece.ColorEnum color)
    {
        IChessPiece.ColorEnum oppositeColor = IChessPiece.getOppositeColor(color);

        King king = (King)getKing(color);
        GridPosition kingPosition = king.getGridPosition();
        ChessPiece[] oppositePieces = getPieces(oppositeColor);

        for (ChessPiece p : oppositePieces)
        {
            if (p.haveAsAvailableMove(kingPosition, false))
            {
                return true;
            }
        }

        return false;
    }

    public boolean isThatMoveCauseAOwnCheck(ChessPiece piece, AvailableMove move)
    {
        ChessBoard boardCopy = copy();
        ChessPiece pieceToMove = Arrays
                                    .stream(boardCopy.getChessPieces())
                                    .filter(p -> piece.equals(p))
                                    .findFirst()
                                    .get();

        boardCopy.removeChessPiece(move.getEatedChessPiece());
        pieceToMove.getGridPosition().setPosition(move.getGridPosition());

        return boardCopy.isCheck(pieceToMove.getColor());
    }

    public ChessPiece[] getPieces(IChessPiece.KindEnum kind, IChessPiece.ColorEnum color)
    {
        return
            chessPieces
                .stream()
                .filter(p -> p.getColor() == color && p.getKind() == kind)
                .toArray(ChessPiece[]::new);
    }

    public void promotePawn(Pawn pawn, IChessPiece.KindEnum promoteTo, AvailableMove move)
    {
        pawn.removeChessPieceActionListener(myChessPieceActionListener);
        chessPieces.remove(pawn);
        ChessPiece toAdd;

        switch (promoteTo)
        {
            case BISHOP:
                toAdd = new Bishop(pawn.getColor(), move.getGridPosition().copy(), this);
                break;
            case KNIGHT:
                toAdd = new Knight(pawn.getColor(), move.getGridPosition().copy(), this);
                break;
            case QUEEN:
                toAdd = new Queen(pawn.getColor(), move.getGridPosition().copy(), this);
                break;
            case ROOK:
                toAdd = new Rook(pawn.getColor(), move.getGridPosition().copy(), this);
                break;
            case KING:
            case PAWN:
            default:
                throw new RuntimeException("Selected promotion is not valid.");
        }

        toAdd.addChessPieceActionListener(myChessPieceActionListener);
        chessPieces.add(toAdd);
        invokePiecedMovedActionEvent(this, toAdd, toAdd.getGridPosition());
    }

    public boolean isCheckMate(IChessPiece.ColorEnum color)
    {
        if (isCheck(getChessPieces(), color))
        {
            ChessPiece[] oppositePieces = getPieces(color);

            for (ChessPiece p : oppositePieces)
            {
                AvailableMove[] pMoves = p.getAvailableMoves(false);

                for (AvailableMove m : pMoves)
                {
                    if (!p.getChessBoard().isThatMoveCauseAOwnCheck(p, m))
                    {
                        return false;
                    }
                }
            }

            return true;
        }

        return false;
    }

    public static boolean isInGrid(GridPosition grid)
    {
        return UMathemathic.isBetween(grid.getRow(), 0, 7) && UMathemathic.isBetween(grid.getColumn(), 0, 7);
    }

    protected void removeChessPiece(ChessPiece pieceToRemove)
    {
        if (pieceToRemove != null)
        {
            chessPieces.remove(pieceToRemove);
        }
    }

    @Override
    public ChessBoard copy()
    {
        return new ChessBoard(null, this.chessPieces);
    }

    public static boolean isCheck(ChessPiece[] pieces, IChessPiece.ColorEnum color)
    {
        IChessPiece.ColorEnum oppositeColor = IChessPiece.getOppositeColor(color);

        ChessPiece king = 
            Arrays
                .stream(pieces)
                .filter(p -> p.getKind() == KindEnum.KING && p.getColor() == color)
                .findFirst()
                .get();

        GridPosition kingPosition = king.getGridPosition();

        ChessPiece[] oppositePieces =
            Arrays
                .stream(pieces)
                .filter(p -> p.getColor() == oppositeColor)
                .toArray(ChessPiece[]::new);

        for (ChessPiece p : oppositePieces)
        {
            if (p.haveAsAvailableMove(kingPosition, false))
            {
                return true;
            }
        }

        return false;
    }

    private ChessBoard(Game game, ArrayList<ChessPiece> pieces)
    {
        this.game = game;
        initialize(pieces, false);
    }

    private void initialize()
    {
        initializePieces();
        initializeActionListeners();
    }

    private void initialize(ArrayList<ChessPiece> pieces, boolean initializeListeners)
    {
        initializePieces(pieces);

        if (initializeListeners)
        {
            initializeActionListeners();
        }
    }

    private void initializePieces()
    {
        chessPieces = new ArrayList<>(0);

        for (int i=0; i<8; i++)
        {
            chessPieces.add(new Pawn(IChessPiece.ColorEnum.WHITE, new GridPosition(6, i), this));
            chessPieces.add(new Pawn(IChessPiece.ColorEnum.BLACK, new GridPosition(1, i), this));
        }

        chessPieces.add(new King(IChessPiece.ColorEnum.WHITE, new GridPosition(7, 4), this));
        chessPieces.add(new King(IChessPiece.ColorEnum.BLACK, new GridPosition(0, 4), this));

        chessPieces.add(new Queen(IChessPiece.ColorEnum.WHITE, new GridPosition(7, 3), this));
        chessPieces.add(new Queen(IChessPiece.ColorEnum.BLACK, new GridPosition(0, 3), this));

        chessPieces.add(new Bishop(IChessPiece.ColorEnum.WHITE, new GridPosition(7, 2), this));
        chessPieces.add(new Bishop(IChessPiece.ColorEnum.WHITE, new GridPosition(7, 5), this));
        chessPieces.add(new Bishop(IChessPiece.ColorEnum.BLACK, new GridPosition(0, 2), this));
        chessPieces.add(new Bishop(IChessPiece.ColorEnum.BLACK, new GridPosition(0, 5), this));

        chessPieces.add(new Knight(IChessPiece.ColorEnum.WHITE, new GridPosition(7, 1), this));
        chessPieces.add(new Knight(IChessPiece.ColorEnum.WHITE, new GridPosition(7, 6), this));
        chessPieces.add(new Knight(IChessPiece.ColorEnum.BLACK, new GridPosition(0, 1), this));
        chessPieces.add(new Knight(IChessPiece.ColorEnum.BLACK, new GridPosition(0, 6), this));

        chessPieces.add(new Rook(IChessPiece.ColorEnum.WHITE, new GridPosition(7, 0), this));
        chessPieces.add(new Rook(IChessPiece.ColorEnum.WHITE, new GridPosition(7, 7), this));
        chessPieces.add(new Rook(IChessPiece.ColorEnum.BLACK, new GridPosition(0, 0), this));
        chessPieces.add(new Rook(IChessPiece.ColorEnum.BLACK, new GridPosition(0, 7), this));
    }

    private void initializePieces(ArrayList<ChessPiece> pieces)
    {
        chessPieces = new ArrayList<>(0);

        for (ChessPiece p : pieces)
        {
            switch (p.getKind())
            {
                case BISHOP:
                    chessPieces.add(new Bishop(p.getColor(), p.getGridPosition().copy(), this));
                    break;
                case KING:
                    chessPieces.add(new King(p.getColor(), p.getGridPosition().copy(), this));
                    break;
                case KNIGHT:
                    chessPieces.add(new Knight(p.getColor(), p.getGridPosition().copy(), this));
                    break;
                case PAWN:
                    chessPieces.add(new Pawn(p.getColor(), p.getGridPosition().copy(), this));
                    break;
                case QUEEN:
                    chessPieces.add(new Queen(p.getColor(), p.getGridPosition().copy(), this));
                    break;
                case ROOK:
                    chessPieces.add(new Rook(p.getColor(), p.getGridPosition().copy(), this));
                    break;
                default:
                    throw new RuntimeException("Piece with no kind.");
            }
        }
    }

    private void initializeActionListeners()
    {
        for (ChessPiece p : chessPieces)
        {
            p.addChessPieceActionListener(myChessPieceActionListener);
        }
    }

    private class MyChessPieceActionListener implements ChessPieceActionListener
    {
        @Override
        public void chessPieceMovedActionEvent(ChessPiece sender, GridPosition newGridPosition) 
        {
            invokePiecedMovedActionEvent(ChessBoard.this, sender, newGridPosition);
        }
    }

    //#region ChessBoardActionListener

    public void addChessBoardActionListener(ChessBoardActionListener listener)
    {
        chessBoardActionListeners.add(listener);
    }

    public void removeChessBoardActionListener(ChessBoardActionListener listener)
    {
        chessBoardActionListeners.remove(listener);
    }

    public void invokePiecedMovedActionEvent(ChessBoard sender, ChessPiece piece, GridPosition newGridPosition)
    {
        for (ChessBoardActionListener l : chessBoardActionListeners)
        {
            l.pieceMovedActionEvent(sender, piece, newGridPosition);
        }
    }

    public interface ChessBoardActionListener
    {
        public void pieceMovedActionEvent(ChessBoard sender, ChessPiece piece, GridPosition newGridPosition);
    }

    //#endregion
}
